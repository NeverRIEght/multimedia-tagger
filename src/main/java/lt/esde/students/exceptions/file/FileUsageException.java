package lt.esde.students.exceptions.file;

import lt.esde.students.exceptions.MediaTaggerException;

import java.io.File;

public class FileUsageException extends MediaTaggerException {
    private final File file;

    public FileUsageException(String message, File file) {
        super(message);
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }
}
