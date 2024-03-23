package lt.esde.students;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import javax.imageio.ImageIO;

import static lt.esde.students.Main.*;

public class FileUtil {

    /**
     * Converts an image file from its own format to tiff format
     */
    public static void convertToTiff() {
        // TODO: Metadata does not transfer correctly
        // TODO: Transform this method according of polymorphism: convert any-to-any (photo formats)
        File inputFile = new File(TEST_IMG_FOLDER_PATH + File.separator + "eifel.jpg");
        File outputFile = new File(TEST_IMG_FOLDER_PATH + File.separator + "Test.tiff");
        try (InputStream is = new FileInputStream(inputFile)) {
            BufferedImage image = ImageIO.read(is);
            try (OutputStream os = new FileOutputStream(outputFile)) {
                ImageIO.write(image, "tiff", os);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    /**
     * Parse the file located in <code>pathToFile</code> and return the oldest of dates in file attributes
     * <p>
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


        // TODO: are this comparison working for the dates with the same year?
        // Return the older date
        if (editTimeLocal.isBefore(creationTimeLocal)) {
            return editTimeLocal;
        } else {
            return creationTimeLocal;
        }
    }

    /**
     * Returns extension of the provided file as a <code>String</code>
     * <p>Examples of return: ".exe", ".txt", etc.
     * <p>
     * @param file <code>File</code> to get extension of
     * @return file extension as a <code>String</code>
     * @see File
     */
    public static String getFileExtension(File file) {
        // TODO: make this method private?

        if (Objects.isNull(file)) {
            throw new NullPointerException("The file is null");
        }

        int index = file.getName().indexOf('.');
        return file.getName().substring(index);
    }
}