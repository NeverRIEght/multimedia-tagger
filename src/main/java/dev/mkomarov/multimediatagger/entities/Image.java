package dev.mkomarov.multimediatagger.entities;

import java.nio.file.Path;

public class Image {
    private String title;
    private Path path;

    public Image(String path) {
        this.path = Path.of(path);
        this.title = this.path.getFileName().toString();
    }

    public String getTitle() {
        return title;
    }

    public Path getPath() {
        return path;
    }
}
