package dev.mkomarov.multimediatagger.mediaobject;

import dev.mkomarov.multimediatagger.tag.Tag;
import dev.mkomarov.multimediatagger.tag.TagParser;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class MediaObject {
    private final String title;
    private final Path path;
    Set<Tag> tags;

    public MediaObject(String path) {
        this.path = Path.of(path);
        this.title = this.path.getFileName().toString();
        this.tags = new HashSet<>(TagParser.getTagsFromFile(path));
    }

    public String getTitle() {
        return title;
    }

    public Path getPath() {
        return path;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
