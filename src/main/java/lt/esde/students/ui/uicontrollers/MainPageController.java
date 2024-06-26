package lt.esde.students.ui.uicontrollers;

import lt.esde.students.utils.FileUtil;
import lt.esde.students.utils.TagUtil;
import lt.esde.students.entities.Tag;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.util.*;

import static lt.esde.students.utils.FileUtil.checkFile;

public class MainPageController {
    public Button selectFolderButton;
    public Label currentFolderLabel;
    public ListView<File> filesListView;
    public TextField tagsInputField;
    public ListView<Tag> tagsListView;
    public ImageView imageView;

    private final TagUtil tagUtil = new TagUtil();

    private File currentFile;

    private Set<Tag> allTags = new HashSet<>();
    private Set<Tag> currentTags = new HashSet<>();

    public void initialize() {
        setAutoCompletionNavigation();
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

        allTags.addAll(tagUtil.getTagsFromFiles(files));

        updateFilesListView(files);
        updateAutoCompletionProperties();
    }

    private void updateFilesListView(List<File> files) {
        filesListView.setStyle("-fx-control-inner-background: #313338;");
        filesListView.getItems().clear();
        filesListView.getItems().addAll(files);
    }

    public void filesListViewClicked() {
        tagsListView.getItems().clear();
        Object selectedItem = filesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) throw new RuntimeException("No file selected");
        if (selectedItem.getClass() != File.class) throw new RuntimeException("Selected item is not a file");
        currentFile = (File) selectedItem;
        checkFile(currentFile);

        imageView.setImage(new Image(currentFile.toURI().toString()));

        currentTags.clear();
        currentTags.addAll(tagUtil.getTagsFromFile(currentFile));
        updateTagsList();
    }

    private void addTag(String tagText) {
        Tag currentTag = tagUtil.parseTagText(tagText);
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
//        checkFile(currentFile);
//
//        String jsonString = tagController.serializeTagsToJson(currentTags.stream().toList());
//        File jsonFile = tagController.getJsonFile(currentFile);
//        if (jsonFile == null) throw new RuntimeException("Json file is null");
//
//        FileUtil.writeJsonFile(jsonFile, jsonString);
    }
}