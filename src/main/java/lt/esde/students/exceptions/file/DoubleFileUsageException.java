package lt.esde.students.exceptions.file;

import lt.esde.students.exceptions.MediaTaggerException;

import java.io.File;

public class DoubleFileUsageException extends MediaTaggerException {
    private final File firstFile;
    private final File secondFile;

    public DoubleFileUsageException(String message, File firstFile, File secondFile) {
        super(message);

        this.firstFile = firstFile;
        this.secondFile = secondFile;
    }

    public File getFirstFile() {
        return this.firstFile;
    }

    public File getSecondFile() {
        return this.secondFile;
    }
}
