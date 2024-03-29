package lt.esde.students;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ExifUtilTest {



    @Test
    void writeExifTagDateTimeOriginalFileNotFound()  {

        File inputImage = new File("non_existent_image.jpg");
        String outputImage = "output/image_with_exif.jpg";
        assertThrows(IOException.class, () -> {
            ExifUtil.writeExifTagDateTimeOriginal(inputImage, outputImage);
        });

    }

    @Test
    void writeExifTagDateTimeOriginalFile() throws Exception  {
 File inputFile = new File("testimg/eifel.jpg");
 String outputImage = "image_with_exif.jpg";
 boolean result= ExifUtil.writeExifTagDateTimeOriginal(inputFile,outputImage);
 assertTrue(true);

    }

    @Test
    void writeExifTagDateTimeOriginalFileIncorrectData() throws Exception  {
        File inputFile = new File("exif.tsss");
        String outputImage = "image_with_exif.jpg";
        boolean result= ExifUtil.writeExifTagDateTimeOriginal(inputFile,outputImage);
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