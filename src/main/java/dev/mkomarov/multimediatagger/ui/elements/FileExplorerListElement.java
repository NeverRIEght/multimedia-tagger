package dev.mkomarov.multimediatagger.ui.elements;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import dev.mkomarov.multimediatagger.utils.FileUtil;

import java.io.File;

public class FileExplorerListElement extends HBox {
    private ImageView iconView;
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
            iconView = new ImageView(fileImage);
            iconView.setFitWidth(60);

            titleLabel = new Label(file.getName());

            detailsLabel1 = new Label(fileImage.getWidth() + "x" + fileImage.getHeight());
            detailsLabel2 = new Label(FileUtil.getFileExtension(file).toUpperCase());
            detailsLabel3 = new Label(file.length() + " bytes");
        } else if (file.isDirectory()) {
            iconView.setFitWidth(60);

            titleLabel = new Label(file.getName());

            detailsLabel1 = new Label(String.valueOf(file.listFiles().length));
            detailsLabel2 = new Label(file.listFiles().length + " without tags");
            detailsLabel3 = new Label(file.length() + " bytes");
        }

        iconView.setFitHeight(60);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox detailsBox = new VBox(titleLabel, detailsLabel1, detailsLabel2, detailsLabel3);
        detailsBox.setSpacing(5);

        this.getChildren().addAll(iconView, detailsBox);
        this.setSpacing(10);
        this.setStyle("-fx-padding: 10; -fx-background-color: #d3d3d3; -fx-border-radius: 5; -fx-background-radius: 5;");
    }

    public File getFile() {
        return file;
    }
}