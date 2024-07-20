package lt.sdc.students.multimediatagger;

import lt.sdc.students.multimediatagger.metadata.exif.ExifWriter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lt.sdc.students.multimediatagger.metadata.exif.ExifWriter.writeExifTagDateTimeOriginal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ExifUtilTest {

    private static LocalDateTime exampleDateTime;

    @BeforeAll
    static void assignExampleDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        exampleDateTime = LocalDateTime.parse("2010-01-01 10:01:10", formatter);
    }

    @AfterAll
    static void removeImages() {
        File imageWithExif = new File("image_with_exif.jpg");
        if (imageWithExif.isFile()) {
            assertTrue(imageWithExif.delete());
        }
    }

    @Test
    void writeExifTagDateTimeOriginalFileNotFound() {
        String inputImage = "non_existent_image.jpg";
        String outputImage = "output/image_with_exif.jpg";
        assertThrows(NullPointerException.class, () -> {
            ExifWriter.writeExifTagDateTimeOriginal(inputImage, outputImage, exampleDateTime);
        });
    }

    @Test
    void writeExifTagDateTimeOriginalFile() {
        String inputImage = "testimg/eifel.jpg";
        String outputImage = "image_with_exif.jpg";
        boolean result = ExifWriter.writeExifTagDateTimeOriginal(inputImage, outputImage, exampleDateTime);
        assertTrue(true);
    }

    @Test
    void writeExifTagDateTimeOriginalFileIncorrectData() {
        String inputImage = "exif.tsss";
        String outputImage = "image_with_exif.jpg";
        assertThrows(NullPointerException.class, () -> {
            ExifWriter.writeExifTagDateTimeOriginal(inputImage, outputImage, exampleDateTime);
        });
    }

    @Test
    void writeExifTagDateTimeOriginalSameFileTest() {
        boolean resultOtherPath = ExifWriter.writeExifTagDateTimeOriginal(Main.TEST_IMG_WITH_METADATA_PATH,
                new File(Main.TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg"),
                exampleDateTime);
        assertTrue(resultOtherPath);

        assertThrows(RuntimeException.class, () -> {
            ExifWriter.writeExifTagDateTimeOriginal(
                    Main.TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg",
                    Main.TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg",
                    exampleDateTime);
        });

        File imageFile = new File(Main.TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg");
        if (imageFile.isFile()) {
            assertTrue(imageFile.delete());
        }
    }

    @Test
    void testWriteExifTagDateTimeOriginal() {

    }

    @Test
    void writeExifTag() {

    }

    @Test
    void readExifTag() {

    }

    @Test
    void readExifTags() {

    }
}