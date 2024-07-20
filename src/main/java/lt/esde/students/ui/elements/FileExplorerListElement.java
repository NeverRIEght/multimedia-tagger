package lt.esde.students.ui.elements;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FileExplorerListElement extends HBox {
    private ImageView iconView;
    private Label titleLabel;
    private Label detailsLabel1;
    private Label detailsLabel2;
    private Label detailsLabel3;

    public FileExplorerListElement(String iconPath, String title, String details1, String details2, String details3) {
        // Иконка
        iconView = new ImageView(new Image(iconPath));
        iconView.setFitWidth(60);
        iconView.setFitHeight(60);

        // Название файла/папки
        titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Подробности
        detailsLabel1 = new Label(details1);
        detailsLabel2 = new Label(details2);
        detailsLabel3 = new Label(details3);

        VBox detailsBox = new VBox(titleLabel, detailsLabel1, detailsLabel2, detailsLabel3);
        detailsBox.setSpacing(5);

        this.getChildren().addAll(iconView, detailsBox);
        this.setSpacing(10);
        this.setStyle("-fx-padding: 10; -fx-background-color: #d3d3d3; -fx-border-radius: 5; -fx-background-radius: 5;");
    }
}
