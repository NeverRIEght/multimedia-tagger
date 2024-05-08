package lt.esde.students.exceptions.file.converter;

import lt.esde.students.exceptions.file.DoubleFileUsageException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class ConverterException extends DoubleFileUsageException {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ConverterException(String message, File inputFile, File outputFile, InputStream inputStream,
                              OutputStream outputStream) {
        super(message, inputFile, outputFile);

        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public File getInputFile() {
        return this.getFirstFile();
    }

    public File getOutputFile() {
        return this.getSecondFile();
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }
}
