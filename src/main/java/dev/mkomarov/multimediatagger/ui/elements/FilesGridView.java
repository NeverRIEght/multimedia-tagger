package dev.mkomarov.multimediatagger.ui.elements;

import dev.mkomarov.multimediatagger.mediaobject.Image;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.Collection;

public class FilesGridView extends FlowPane {
    private final ObservableList<Image> elements = FXCollections.observableArrayList();
    private double imageSize = 100; // Ширина каждого изображения

    public FilesGridView() {
        elements.addListener((ListChangeListener<Image>) change -> updateGrid());

        setHgap(10);
        setVgap(10);
        setPrefWidth(500);
        setPrefHeight(550);

        // Инициализация
        updateGrid();
    }

    private void updateGrid() {
        getChildren().clear();
        for (Image image : elements) {
            ImageView imageView = new ImageView(new javafx.scene.image.Image(image.getPath().toUri().toString()));
            imageView.setFitWidth(imageSize);
            imageView.setPreserveRatio(true);

            VBox imageBox = new VBox(imageView);
            getChildren().add(imageBox);
        }
    }

    public void setElements(Collection<Image> images) {
        elements.setAll(images);
    }

    public ObservableList<Image> getElements() {
        return elements;
    }
}