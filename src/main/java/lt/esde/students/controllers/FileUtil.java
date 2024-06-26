package lt.esde.students.controllers;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static File getFile(String path) {
        File file = Paths.get(path).toFile();
        return checkFile(file);
    }

    public static File getFile(FileChooser fileChooser) {
        File file = fileChooser.showOpenDialog(null);
        return checkFile(file);
    }

    public static File checkFile(File file) {
        if (file == null) throw new RuntimeException("File is null");
        if (!file.exists()) throw new RuntimeException("File not found: " + file.getAbsolutePath());
        if (!file.isFile()) throw new RuntimeException("Not a file: " + file.getAbsolutePath());
        return file;
    }

    public static File getDirectory(String Path) {
        File dir = Paths.get(Path).toFile();
        return checkDirectory(dir);
    }

    public static File getDirectory(DirectoryChooser directoryChooser) {
//        return new File("D:\\Multimedia\\Images\\other\\NeverRIEght-logo");
        File dir = directoryChooser.showDialog(null);
        return checkDirectory(dir);
    }

    public static File checkDirectory(File dir) {
        if (dir == null) throw new RuntimeException("Directory is null");
        if (!dir.exists()) throw new RuntimeException("Directory not found: " + dir.getAbsolutePath());
        if (!dir.isDirectory()) throw new RuntimeException("Not a directory: " + dir.getAbsolutePath());
        return dir;
    }

    public static List<File> getFiles(File directory) {
        directory = checkDirectory(directory);
        return getFilesFromDirectory(new ArrayList<>(), directory);
    }

    public static List<File> getImages(File directory) {
        directory = checkDirectory(directory);
        List<File> files = getFilesFromDirectory(new ArrayList<>(), directory);
        return files.stream()
                .filter(file -> {
                    String fileName = file.getName();
                    return fileName.endsWith(".jpg") || fileName.endsWith(".png");
                })
                .toList();
    }

    private static List<File> getFilesFromDirectory(List<File> files, File directory) {
        if (directory.listFiles() == null) throw new RuntimeException("No files in directory");

        for (File entity : directory.listFiles()) {
            if (entity == null) throw new RuntimeException("Entity is null");
            if (entity.isDirectory()) {
                getFilesFromDirectory(files, entity);
            } else {
                files.add(entity);
            }
        }

        return files;
    }

    public static void writeJsonFile(File jsonFile, String json) {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileUtil() {
        throw new UnsupportedOperationException("Utility class");
    }
}
