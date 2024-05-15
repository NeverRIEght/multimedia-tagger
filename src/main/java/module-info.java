module multimedia.tagger {
    requires htmlunit;
    requires java.desktop;
    requires javafx.graphics;
    requires metadata.extractor;
    requires org.apache.commons.imaging;
    requires org.apache.commons.io;
    requires org.json;

    opens lt.esde.students to javafx.fxml;
    exports lt.esde.students.ui;
}