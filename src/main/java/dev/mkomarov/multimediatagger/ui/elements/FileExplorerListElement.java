package dev.mkomarov.multimediatagger.ui.elements;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import dev.mkomarov.multimediatagger.utils.FileUtil;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class FileExplorerListElement extends GridPane {
    private ImageView imagePreview;
    private Label titleLabel;
    private Label detailsLabel1;
    private Label detailsLabel2;
    private Label detailsLabel3;
    private File file;

    public FileExplorerListElement(File file) {
        if (file == null || !file.exists()) return;
        this.file = file;

        if (file.isFile()) {
            Image fileImage = new Image(file.toURI().toString());
            imagePreview = new ImageView(fileImage);

            titleLabel = new Label(file.getName());

            detailsLabel1 = new Label(fileImage.getWidth() + "x" + fileImage.getHeight());
            detailsLabel2 = new Label(FileUtil.getFileExtension(file).toUpperCase());
            detailsLabel3 = new Label(file.length() + " bytes");
        } else if (file.isDirectory()) {

            titleLabel = new Label(file.getName());

            detailsLabel1 = new Label(String.valueOf(file.listFiles().length));
            detailsLabel2 = new Label(file.listFiles().length + " without tags");
            detailsLabel3 = new Label(file.length() + " bytes");
        }

        this.maxWidth(400);
        this.prefWidth(400);
        this.minWidth(400);

        this.maxHeight(80);
        this.prefHeight(80);
        this.minHeight(80);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(82);
        column1.setPrefWidth(82);
        column1.setMaxWidth(82);

        this.getColumnConstraints().add(column1);

        imagePreview.minWidth(66);
        imagePreview.minHeight(66);
        imagePreview.prefWidth(66);
        imagePreview.prefHeight(66);
        imagePreview.maxWidth(66);
        imagePreview.maxHeight(66);

//        Rectangle clip = new Rectangle(imagePreview.getFitWidth(), imagePreview.getFitHeight());
//        clip.setArcWidth(4);
//        clip.setArcHeight(4);
        imagePreview.setStyle("-fx-border-radius: 5; -fx-background-radius: 5;");

        this.add(imagePreview, 0, 0);
//        this.getColumnConstraints().getFirst().setMaxWidth(82);

        titleLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 18px; -fx-line-height: 21px; -fx-text-alignment: left; -fx-text-fill: #1F1F1F;");
        VBox detailsBox = new VBox(titleLabel, detailsLabel1, detailsLabel2, detailsLabel3);
        detailsBox.setSpacing(5);

        this.add(detailsBox, 1, 0);
        this.setStyle("-fx-padding: 10; -fx-background-color: #d3d3d3; -fx-border-radius: 5; -fx-background-radius: 5;");
    }

    public File getFile() {
        return file;
    }
}