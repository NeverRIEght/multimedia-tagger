package lt.esde.students;

import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static lt.esde.students.ExifUtil.readExifTag;
import static lt.esde.students.ExifUtil.writeExifTagDateTimeOriginal;

public class Main {
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg";
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "eifel.jpg";
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "maricat.jpg";

    public static void main(String[] args) throws IOException {

//        File inputImage = new File("D:\\Test.tiff"); // Good
//        File inputImage = new File("D:\\Test.jpg"); // Good
//        File inputImage = new File("D:\\Test.png"); // Exception: png has no exif support :(

        writeExifTagDateTimeOriginal(new File(TEST_IMG_WITHOUT_METADATA_PATH),
                TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg");

        String dateTime = readExifTag(new File(TEST_IMG_FOLDER_PATH + File.separator + "test1.jpg"),
                ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);

        System.out.println(dateTime);
    }
}