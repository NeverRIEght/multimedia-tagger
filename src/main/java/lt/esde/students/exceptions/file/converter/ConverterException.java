package lt.esde.students.exceptions.file.converter;

import lt.esde.students.exceptions.file.DoubleFileUsageException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Exception to converting one file into another
 */
public class ConverterException extends DoubleFileUsageException {
    /**
     * Input used stream
     */
    private final InputStream inputStream;

    /**
     * Output used stream
     */
    private final OutputStream outputStream;

    /**
     * Constructor for exception to converting one file into another
     * @param message Message for exception
     * @param inputFile Input file which has problems
     * @param outputFile Output file which has problems
     * @param inputStream Input used stream
     * @param outputStream Output used stream
     */
    public ConverterException(String message, File inputFile, File outputFile, InputStream inputStream,
                              OutputStream outputStream) {
        super(message, inputFile, outputFile);

        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    /**
     * Get input file
     * @return Input file which has problems
     */
    public File getInputFile() {
        return this.getFirstFile();
    }

    /**
     * Get output file
     * @return Output file which has problems
     */
    public File getOutputFile() {
        return this.getSecondFile();
    }

    /**
     * Get input stream
     * @return Input used stream
     */
    public InputStream getInputStream() {
        return this.inputStream;
    }

    /**
     * Get output stream
     * @return Output used stream
     */
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
}
