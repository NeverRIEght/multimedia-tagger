package dev.mkomarov.multimediatagger.metadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileAttributesReader {
    public static LocalDateTime getFileCreationTime(String pathToFile) {
        LocalDateTime output;

        Path file = Paths.get(pathToFile);
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(file, BasicFileAttributes.class);

            output = LocalDateTime.ofInstant(fileAttributes.creationTime().toInstant(),
                    ZoneId.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    public static LocalDateTime getFileLastAccessTime(String pathToFile) {
        LocalDateTime output;

        Path file = Paths.get(pathToFile);
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(file, BasicFileAttributes.class);

            output = LocalDateTime.ofInstant(fileAttributes.lastAccessTime().toInstant(),
                    ZoneId.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    public static LocalDateTime getFileLastModifiedTime(String pathToFile) {
        LocalDateTime output;

        Path file = Paths.get(pathToFile);
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(file, BasicFileAttributes.class);

            output = LocalDateTime.ofInstant(fileAttributes.lastModifiedTime().toInstant(),
                    ZoneId.systemDefault());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }
}
