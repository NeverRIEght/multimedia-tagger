package lt.esde.students.ui.elements;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lt.esde.students.utils.FileUtil;

import java.io.File;

public class FileExplorerListElement extends HBox {
    private ImageView iconView;
    private Label titleLabel;
    private Label detailsLabel1;
    private Label detailsLabel2;
    private Label detailsLabel3;

    public FileExplorerListElement(File file) {
        if (file == null || !file.exists()) return;

        if (file.isFile()) {
            Image fileImage = new Image(file.getAbsolutePath());
            iconView = new ImageView(fileImage);
            titleLabel = new Label(file.getName());

            detailsLabel1 = new Label(fileImage.getWidth() + "x" + fileImage.getHeight());
            detailsLabel2 = new Label(FileUtil.getFileExtension(file).toUpperCase());
            detailsLabel3 = new Label(file.length() + " bytes");
        } else if (file.isDirectory()) {
            iconView = null;
            titleLabel = new Label(file.getName());

            detailsLabel1 = new Label(String.valueOf(file.listFiles().length));
            detailsLabel2 = new Label(file.listFiles().length + " without tags");
            detailsLabel3 = new Label(file.length() + " bytes");
        }


        iconView.setFitWidth(60);
        iconView.setFitHeight(60);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");



        VBox detailsBox = new VBox(titleLabel, detailsLabel1, detailsLabel2, detailsLabel3);
        detailsBox.setSpacing(5);

        this.getChildren().addAll(iconView, detailsBox);
        this.setSpacing(10);
        this.setStyle("-fx-padding: 10; -fx-background-color: #d3d3d3; -fx-border-radius: 5; -fx-background-radius: 5;");
    }
}
