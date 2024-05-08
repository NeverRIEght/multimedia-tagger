package lt.esde.students.exceptions.file.usage;

import lt.esde.students.exceptions.file.FileUsageException;

import java.io.File;
import java.util.stream.Stream;

public class ReadWriteException extends FileUsageException{
    private final Stream stream;

    public ReadWriteException(String message, File file, Stream stream) {
        super(message, file);
        this.stream = stream;
    }

    public Stream getStream() {
        return stream;
    }
}
