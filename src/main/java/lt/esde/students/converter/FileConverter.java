package lt.esde.students.converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static lt.esde.students.Main.TEST_IMG_FOLDER_PATH;

public class FileConverter {
    /**
     * Converts an image file from its own format to tiff format
     */
    public static void convertToTiff() {
        File inputFile = new File(TEST_IMG_FOLDER_PATH + File.separator + "eifel.jpg");
        File outputFile = new File(TEST_IMG_FOLDER_PATH + File.separator + "Test.tiff");

        try (InputStream is = new FileInputStream(inputFile);
             OutputStream os = new FileOutputStream(outputFile)) {

            BufferedImage image = ImageIO.read(is);
            ImageIO.write(image, "tiff", os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
