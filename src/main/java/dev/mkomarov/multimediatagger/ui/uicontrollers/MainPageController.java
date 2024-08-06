package dev.mkomarov.multimediatagger.ui.uicontrollers;

import dev.mkomarov.multimediatagger.json.JsonTagDeserializer;
import dev.mkomarov.multimediatagger.json.JsonTagSerializer;
import dev.mkomarov.multimediatagger.mediaobject.Image;
import dev.mkomarov.multimediatagger.ui.elements.FilesGridView;
import dev.mkomarov.multimediatagger.utils.FileUtil;
import dev.mkomarov.multimediatagger.tag.TagParser;
import dev.mkomarov.multimediatagger.tag.Tag;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.util.*;

import static dev.mkomarov.multimediatagger.utils.FileUtil.checkFile;

public class MainPageController {
    public Button selectFolderButton;
    public Label currentFolderLabel;
    public FilesGridView imagesGallery;
    public TextField tagsInputField;
    public ListView<Tag> tagsListView;
    public ImageView imageView;
    public GridPane leftGrid;

    public File currentFile;

    private Set<Tag> allTags = new HashSet<>();
    private Set<Tag> currentTags = new HashSet<>();

    public void initialize() {
        setAutoCompletionNavigation();
        imagesGallery = new FilesGridView();
        leftGrid.add(imagesGallery, 0, 1);
        GridPane.setColumnSpan(imagesGallery, 2);
        imagesGallery.setOnMouseClicked(event -> {
            currentFile = imagesGallery.getSelectedFile();
            imagesGalleryClicked();
        });
    }

    private void setAutoCompletionNavigation() {
        updateAutoCompletionProperties();

        tagsInputField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                    tagsInputField.fireEvent(new javafx.scene.input.KeyEvent(
                            javafx.scene.input.KeyEvent.KEY_PRESSED,
                            javafx.scene.input.KeyEvent.CHAR_UNDEFINED,
                            "",
                            javafx.scene.input.KeyCode.DOWN,
                            false, false, false, false
                    ));
                    break;
                case UP:
                    tagsInputField.fireEvent(new javafx.scene.input.KeyEvent(
                            javafx.scene.input.KeyEvent.KEY_PRESSED,
                            javafx.scene.input.KeyEvent.CHAR_UNDEFINED,
                            "",
                            javafx.scene.input.KeyCode.UP,
                            false, false, false, false
                    ));
                    break;
                case ENTER:
                    addTag(tagsInputField.getText());
                    break;
                default:
                    break;
            }
        });
    }

    private void updateAutoCompletionProperties() {
        AutoCompletionBinding<Tag> binding = TextFields.bindAutoCompletion(tagsInputField, allTags);
        binding.setDelay(0);
        binding.setOnAutoCompleted(event -> addTag(tagsInputField.getText()));
    }

    public void selectFolderButtonClicked() {
        imageView.setImage(null);
        File initialFolder = FileUtil.getDirectory(new DirectoryChooser());
        currentFolderLabel.setText(initialFolder.getAbsolutePath());

        List<File> files = FileUtil.getImages(initialFolder);

        allTags.addAll(TagParser.getTagsFromFiles(files));

        updateImagesGallery(files);
        updateAutoCompletionProperties();
    }

    private void updateImagesGallery(List<File> files) {
        imagesGallery.getChildren().clear();
        List<Image> images = new ArrayList<>();
        for (File file : files) {
            images.add(new Image(file.getAbsolutePath()));
        }
        imagesGallery.setElements(images);
    }

    public void imagesGalleryClicked() {
        tagsListView.getItems().clear();
        checkFile(currentFile);

        imageView.setImage(new Image(currentFile.toURI().toString()));

        currentTags.clear();
        currentTags.addAll(JsonTagDeserializer.deserializeTagsForFile(currentFile));
        updateTagsList();
    }

    private void addTag(String tagText) {
        Tag currentTag = TagParser.parseTagText(tagText);
        currentTags.add(currentTag);
        allTags.add(currentTag);
        updateAutoCompletionProperties();
        tagsInputField.clear();
        updateTagsList();
    }

    private void updateTagsList() {
        tagsListView.setStyle("-fx-control-inner-background: #313338;");
        tagsListView.getItems().clear();
        tagsListView.getItems().addAll(currentTags);
    }

    public void saveData() {
        checkFile(currentFile);
        JsonTagSerializer.serializeTagsForFile(currentTags, currentFile);
    }
}