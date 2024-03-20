package lt.esde.students;


import org.apache.commons.imaging.ImageReadException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static lt.esde.students.ExifUtil.*;

import lt.esde.students.ExifUtil.*;

import static lt.esde.students.FileUtil.*;

public class Main {
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath().toString() + File.separator + "testimg";
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath().toString() + File.separator + "testimg" + File.separator + "eifel.jpg";
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath().toString() + File.separator + "testimg" + File.separator + "maricat.jpg";

    public static void main(String[] args) throws IOException, ImageReadException, ParseException {

//        File inputImage = new File("D:\\Test.tiff"); // Good
//        File inputImage = new File("D:\\Test.jpg"); // Good
//        File inputImage = new File("D:\\Test.png"); // Exception: png has no exif support :(
        //writeExifTag(inputImage, "D:\\Test.tiff", 0x9003);


        //getCreationDateTime(TEST_IMG_WITHOUT_METADATA_PATH);
        //convertToTiff();

    }
}