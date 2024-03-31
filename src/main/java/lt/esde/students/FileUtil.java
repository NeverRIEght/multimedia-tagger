package lt.esde.students;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class FileUtil {
    /**
     * Parse the file located in <code>pathToFile</code> and return the oldest of dates in file attributes
     * <p>
     *
     * @param pathToFile Path of file to parse
     * @return The oldest date from the file attributes as a <code>LocalDateTime</code>
     * @throws IOException In case if the file does not exist
     * @see LocalDateTime
     */
    public static LocalDateTime getCreationDateTime(String pathToFile) throws IOException {
        Path path = Paths.get(pathToFile);

        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime creationTime = attributes.creationTime();
        FileTime editTime = attributes.lastModifiedTime();

        LocalDateTime creationTimeLocal = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime editTimeLocal = LocalDateTime.ofInstant(editTime.toInstant(), ZoneId.systemDefault());

        // Return the older date
        if (editTimeLocal.isBefore(creationTimeLocal)) {
            return editTimeLocal;
        } else {
            return creationTimeLocal;
        }
    }

    /**
     * Returns extension of the provided file as a <code>String</code>
     * <p>If there is no extension, method will return empty <code>String</code>
     * <p>Examples of return: "exe", "txt", etc.
     * <p>
     *
     * @param file <code>File</code> to get extension of
     * @return <code>File</code> extension as a <code>String</code>.
     * @see File
     */
    public static String getFileExtension(File file) {
        if (Objects.isNull(file)) {
            throw new NullPointerException("The file is null");
        }

        int index = file.getName().lastIndexOf('.');
        if (index == -1) {
            return "";
        }

        return file.getName().substring(index + 1);
    }
}