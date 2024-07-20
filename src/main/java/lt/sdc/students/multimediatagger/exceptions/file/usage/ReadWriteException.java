package lt.sdc.students.multimediatagger.exceptions.file.usage;

import lt.sdc.students.multimediatagger.exceptions.file.FileUsageException;

import java.io.File;
import java.util.stream.Stream;

/**
 * Exception to reading or writing file
 */
public class ReadWriteException extends FileUsageException{
    /**
     * Used stream of file with problems
     */
    private final Stream stream;

    /**
     * Constructor for read/write exception
     * @param message Message for exception
     * @param file File which has problems
     * @param stream Used stream of file with problems
     */
    public ReadWriteException(String message, File file, Stream stream) {
        super(message, file);
        this.stream = stream;
    }

    /**
     * Get used stream
     * @return Used stream of file with problems
     */
    public Stream getStream() {
        return stream;
    }
}
