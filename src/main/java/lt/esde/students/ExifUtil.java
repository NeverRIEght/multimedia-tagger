package lt.esde.students;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileSystemDirectory;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffEpTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import static lt.esde.students.Main.TEST_IMG_WITH_METADATA_PATH;

public class ExifUtil {
    public static HashMap<String, String> parseFileMetadata(final File fromFile) throws ImageProcessingException, IOException {


        HashMap<String, String> tagsMap = new HashMap<>();

        //parseDateTimeOriginal()

        String dateTimeOriginal = readExifTag(fromFile, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        tagsMap.put("DateTimeOriginal", dateTimeOriginal);

        //parseOffsetTimeOriginal()

        String timeZoneOffset = readExifTag(fromFile, TiffEpTagConstants.EXIF_TAG_TIME_ZONE_OFFSET);
        tagsMap.put("TimeZoneOffset", timeZoneOffset);

        //parseModifyDate()

        String modifyDate = readExifTag(fromFile, TiffTagConstants.TIFF_TAG_DATE_TIME);
        modifyDate = modifyDate.replace("'", "").replace(" ", "T");
        modifyDate = modifyDate.replaceFirst(":", "-").replaceFirst(":", "-");

        Metadata metadata = ImageMetadataReader.readMetadata(new File(TEST_IMG_WITH_METADATA_PATH));

        if (metadata != null) {
            FileSystemDirectory directory = metadata.getFirstDirectoryOfType(FileSystemDirectory.class);
            Date modificationDate = directory.getDate(FileSystemDirectory.TAG_FILE_MODIFIED_DATE);
            String modificationDateStr = modificationDate.toInstant().toString().replace("-", ":");
            modificationDateStr = modificationDateStr.substring(0, modificationDateStr.lastIndexOf(":") + 3);
            modificationDateStr = modificationDateStr.replaceFirst(":", "-").replaceFirst(":", "-");

            LocalDateTime dateTime1 = LocalDateTime.parse(modifyDate);
            LocalDateTime dateTime2 = LocalDateTime.parse(modificationDateStr);

            if (dateTime1.isBefore(dateTime2)) {
                tagsMap.put("ModifyDate", "'" + dateTime2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "'");
            } else {
                tagsMap.put("ModifyDate", "'" + dateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "'");
            }
        } else {
            tagsMap.put("ModifyDate", "'" + modifyDate);
        }

        //parseExifImageWidth()

        String imageWidth = readExifTag(fromFile, ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);
        tagsMap.put("ExifImageWidth", imageWidth);

        //parseExifImageHeight()

        String imageHeight = readExifTag(fromFile, ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);
        tagsMap.put("ExifImageHeight", imageHeight);

        //parseXPAuthor()

        String author = readExifTag(fromFile, MicrosoftTagConstants.EXIF_TAG_XPAUTHOR);
        tagsMap.put("XPAuthor", author);

        //parseSoftware()

        String software = readExifTag(fromFile, ExifTagConstants.EXIF_TAG_SOFTWARE);
        tagsMap.put("Software", software);

        //parseImageHistory()

        String imageHistory = readExifTag(fromFile, TiffEpTagConstants.EXIF_TAG_IMAGE_HISTORY_EXIF_IFD);
        tagsMap.put("ImageHistory", imageHistory);

        //parseImageDescription()

        String imageDescription = readExifTag(fromFile, TiffTagConstants.TIFF_TAG_IMAGE_DESCRIPTION);
        tagsMap.put("ImageDescription", imageDescription);

        return tagsMap;
    }
}
