package lt.esde.students.exceptions.file;

import lt.esde.students.exceptions.MediaTaggerException;

import java.io.File;

public class FileUsageException extends MediaTaggerException {
    public FileUsageException(String message, File file, FileUsageExceptionErrors error) {
        super(generateErrorMessage(message, file, error));
    }

    private static String generateErrorMessage(String message, File file, FileUsageExceptionErrors error) {
        StringBuilder sb = new StringBuilder();

        sb.append(error.getError());
        sb.append(": ");
        sb.append(message);
        sb.append("in the file - ");
        sb.append(file.getAbsolutePath());

        return sb.toString();
    }
}
