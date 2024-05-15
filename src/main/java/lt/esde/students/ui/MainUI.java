package lt.esde.students.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static lt.esde.students.Main.SOURCE_CODE_PATH;

public class MainUI extends Application {

    public static final String PATH_TO_FIRST_SCENE = SOURCE_CODE_PATH + File.separator + "ui" + File.separator + "main-page.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(PATH_TO_FIRST_SCENE).toURI().toURL());
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
