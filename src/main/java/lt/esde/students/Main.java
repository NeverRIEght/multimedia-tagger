package lt.esde.students;

import org.apache.commons.imaging.ImagingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

import static lt.esde.students.ExifUtil.writeExifTag;
import static lt.esde.students.MetadataExample.metadataExample;

public class Main {
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg";
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "eifel.jpg";
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "maricat.jpg";

    public static void main(String[] args) throws ImagingException, IOException, ParseException {

//        File inputImage = new File("D:\\Test.tiff"); // Good
//        File inputImage = new File("D:\\Test.jpg"); // Good
//        File inputImage = new File("D:\\Test.png"); // Exception: png has no exif support :(
        //WriteExifMetadataExample example = new WriteExifMetadataExample();
        //example.setExifGPSTag(new File(TEST_IMG_WITHOUT_METADATA_PATH), new File(TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg"));

        writeExifTag(new File(TEST_IMG_WITHOUT_METADATA_PATH), TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg", 20);
        metadataExample(new File(TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg"));
    }
}