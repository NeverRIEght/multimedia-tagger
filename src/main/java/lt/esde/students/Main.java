package lt.esde.students;

import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;

import java.io.File;
import java.nio.file.Paths;

import static lt.esde.students.ExifUtil.readExifTag;

public class Main {
    /**
     * Path to the folder with test images
     */
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg";

    /**
     * Path to the test jpg file, which has exif field with creation date
     */
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "eifel.jpg";

    /**
     * Path to the test jpg file, which has no exif field with creation date
     */
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "maricat.jpg";

    public static void main(String[] args) {
        File paramFile = new File(TEST_IMG_WITH_METADATA_PATH);

        //parseDateTimeOriginal()

        String dateTimeOriginal = readExifTag(paramFile, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);

        //parseOffsetTimeOriginal()

        //String offsetTimeOriginal = readExifTag(paramFile, )

        //parseModifyDate()
        //parseOffsetTime()

        // TODO: is this the right constant?
        //String offsetTime = readExifTag(paramFile, ExifTagConstants.EXIF_TAG_EXIF_OFFSET);

        //parseExifImageWidth()
        //parseExifImageHeight()
        //parseOriginalDefaultFinalSize()
        //parseOriginalBestQualitySize()
        //parseXPAuthor()
        //parseSoftware()
        //parseImageHistory()
        //parseImageDescription()

        System.out.println(0x9010);

    }
}