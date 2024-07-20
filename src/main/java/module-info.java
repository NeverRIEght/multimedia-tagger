module lt.esde.students {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires org.apache.commons.imaging;
    requires org.apache.commons.io;
    requires metadata.extractor;
    requires htmlunit;
    requires org.json;


    exports dev.mkomarov.multimediatagger.ui to javafx.graphics;
    exports dev.mkomarov.multimediatagger.ui.uicontrollers to javafx.fxml;

    opens dev.mkomarov.multimediatagger.ui.uicontrollers to javafx.fxml;
}