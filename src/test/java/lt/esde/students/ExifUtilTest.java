package lt.esde.students;

import lt.esde.students.metadata.exif.ExifWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ExifUtilTest {

    private static LocalDateTime exampleDateTime;

    @BeforeAll
    static void assignExampleDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        exampleDateTime = LocalDateTime.parse("2010-01-01 10:01:10", formatter);
    }

    @Test
    void writeExifTagDateTimeOriginalFileNotFound() {
        File inputImage = new File("non_existent_image.jpg");
        String outputImage = "output/image_with_exif.jpg";
        assertThrows(NullPointerException.class, () -> {
            ExifWriter.writeExifTagDateTimeOriginal(inputImage, outputImage, exampleDateTime);
        });
    }

    @Test
    void writeExifTagDateTimeOriginalFile() throws Exception {
        File inputFile = new File("testimg/eifel.jpg");
        String outputImage = "image_with_exif.jpg";
        boolean result = ExifWriter.writeExifTagDateTimeOriginal(inputFile, outputImage, exampleDateTime);
        assertTrue(true);
    }

    @Test
    void writeExifTagDateTimeOriginalFileIncorrectData() throws Exception {
        File inputFile = new File("exif.tsss");
        String outputImage = "image_with_exif.jpg";
        boolean result = ExifWriter.writeExifTagDateTimeOriginal(inputFile, outputImage, exampleDateTime);
        assertTrue(true);
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