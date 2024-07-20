package lt.sdc.students.multimediatagger.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
