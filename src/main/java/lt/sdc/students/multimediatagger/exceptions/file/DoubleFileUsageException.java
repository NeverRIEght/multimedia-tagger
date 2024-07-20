package lt.sdc.students.multimediatagger.exceptions.file;

import lt.sdc.students.multimediatagger.exceptions.MediaTaggerException;

import java.io.File;

/**
 * Exception root for exceptions which connected to usage of two files
 */
public class DoubleFileUsageException extends MediaTaggerException {
    /**
     * First file which has problems
     */
    private final File firstFile;

    /**
     * Second file which has problems
     */
    private final File secondFile;

    /**
     * Constructor for exception
     * @param message Message for exception
     * @param firstFile First file which has problems
     * @param secondFile Second file which has problems
     */
    public DoubleFileUsageException(String message, File firstFile, File secondFile) {
        super(message);

        this.firstFile = firstFile;
        this.secondFile = secondFile;
    }

    /**
     * Get first file
     * @return First file with which has problems
     */
    public File getFirstFile() {
        return this.firstFile;
    }

    /**
     * Get second file
     * @return Second file with which has problems
     */
    public File getSecondFile() {
        return this.secondFile;
    }
}
