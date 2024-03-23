package lt.esde.students.Entities;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static lt.esde.students.FileUtil.getFileExtension;

public class MultimediaFile {
    private String name;

    private String path;

    private String extension;

    private LocalDateTime creationDateTime;

    private LocalDateTime modificationDateTime;

    private List<LocalDateTime> dates;

    public MultimediaFile(String name, String path, LocalDateTime creationDateTime, LocalDateTime modificationDateTime) {
        this.name = name;
        this.path = path;
        this.extension = getFileExtension(new File(path));
        this.creationDateTime = creationDateTime;
        this.modificationDateTime = modificationDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }
}
