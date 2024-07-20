package lt.sdc.students.multimediatagger.exceptions.file;

import lt.sdc.students.multimediatagger.exceptions.MediaTaggerException;

import java.io.File;

/**
 * Exception root for exceptions which connected to file usage
 */
public class FileUsageException extends MediaTaggerException {
    /**
     * File with which has problems
     */
    private final File file;

    /**
     * Constructor
     * @param message Message for exception
     * @param file File with which has problems
     */
    public FileUsageException(String message, File file) {
        super(message);
        this.file = file;
    }

    /**
     * Get file
     * @return File with which has problems
     */
    public File getFile() {
        return this.file;
    }
}
