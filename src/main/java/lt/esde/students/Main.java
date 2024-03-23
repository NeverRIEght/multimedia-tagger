package lt.esde.students;

import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffEpTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

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

        // TODO: can contain 1 or 2 values. Parse? What is the pattern?
        // 1. The time zone offset of DateTimeOriginal from GMT in hours
        // 2. If present, the time zone offset of ModifyDate
        String offsetTimeOriginal = readExifTag(paramFile, TiffEpTagConstants.EXIF_TAG_TIME_ZONE_OFFSET);

        //parseModifyDate()

        String modifyDate = readExifTag(paramFile, TiffTagConstants.TIFF_TAG_DATE_TIME);

        //parseOffsetTime()

        // TODO: can contain 1 or 2 values. Parse? What is the pattern?
        // 1. The time zone offset of DateTimeOriginal from GMT in hours
        // 2. If present, the time zone offset of ModifyDate
        String offsetTime = readExifTag(paramFile, TiffEpTagConstants.EXIF_TAG_TIME_ZONE_OFFSET);

        //parseExifImageWidth()

        String imageWidth = readExifTag(paramFile, ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);

        //parseExifImageHeight()

        String imageHeight = readExifTag(paramFile, ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);

        //parseOriginalDefaultFinalSize()
        //parseOriginalBestQualitySize()

        //parseXPAuthor()

        String author = readExifTag(paramFile, MicrosoftTagConstants.EXIF_TAG_XPAUTHOR);

        //parseSoftware()

        String software = readExifTag(paramFile, ExifTagConstants.EXIF_TAG_SOFTWARE);

        //parseImageHistory()
        //parseImageDescription()

        System.out.println(0xc792);
        System.out.println(32767);

    }
}