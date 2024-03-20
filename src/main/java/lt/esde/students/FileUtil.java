package lt.esde.students;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import javax.imageio.ImageIO;

import static lt.esde.students.Main.*;

public class FileUtil {

    public static void convertToTiff() {
        // TODO: Metadata does not transfer correctly
        // TODO: Transform this method in according of polymorphism: convert any-to-any (photo formats)
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

    public static LocalDateTime getCreationDateTime(String pathToFile) throws IOException {
        Path path = Paths.get(pathToFile);

        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
        FileTime creationTime = attributes.creationTime();
        FileTime editTime = attributes.lastModifiedTime();

        LocalDateTime creationTimeLocal = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime editTimeLocal = LocalDateTime.ofInstant(editTime.toInstant(), ZoneId.systemDefault());


        // TODO: ARE THIS COMPARISON IS USING F*CKING YEARS? WHY -4?
        // Return the older date
        if(editTimeLocal.isBefore(creationTimeLocal)) {
            return editTimeLocal;
        } else {
            return creationTimeLocal;
        }
    }

    static String getFileExtension(File file) {
        // TODO: make this method private?
        int index = file.getName().indexOf('.');
        return index == -1 ? null : file.getName().substring(index);
    }
}