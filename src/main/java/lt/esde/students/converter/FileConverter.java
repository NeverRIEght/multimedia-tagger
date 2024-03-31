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
}
