package lt.esde.students.metadata.exif;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lt.esde.students.Main.TEST_IMG_WITH_METADATA_PATH;

public class ExifReader {
    /**
     * Parses the specific EXIF tag to <code>String</code>
     * <p>
     *
     * @param fromFile file to parse
     * @param tagInfo  <code>TagInfo</code> of the EXIF tag to read
     * @return <code>String</code> with the data found. Might be null in case the field is empty
     * @see ExifTagConstants
     */
    public static String readExifTag(final File fromFile, final TagInfo tagInfo) {
        try {
            final ImageMetadata metadata = Imaging.getMetadata(fromFile);
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

                final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
                if (field != null) {
                    return field.getValueDescription();
                }
            }
        } catch (ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Parses the whole set of EXIF tags for specific file and returns it as a <code>Hashmap</code>
     * <p>Uses two libraries for the same file. For the most of EXIF fields, duplicates will be deleted.
     *
     * @param fromFile file to parse
     * @return <code>Hashmap</code> of the non-null fields set. Might be null if the set is empty
     */
    public static HashMap<String, String> readExifTags(final File fromFile) {
        HashMap<String, String> tagsMap = new HashMap<>();

        try {
            final ImageMetadata apacheMetadata = Imaging.getMetadata(fromFile);
            if (apacheMetadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) apacheMetadata;
                final List<ImageMetadata.ImageMetadataItem> items = jpegMetadata.getItems();

                for (final ImageMetadata.ImageMetadataItem item : items) {
                    String tagString = item.toString();
                    tagsMap.put(tagString.substring(0, tagString.indexOf(":")), tagString.substring(tagString.indexOf(":") + 2));
                }
            }

            Metadata extractorMetadata = ImageMetadataReader.readMetadata(new File(TEST_IMG_WITH_METADATA_PATH));
            Iterable<Directory> directories = extractorMetadata.getDirectories();
            for (Directory dir : directories) {
                Collection<Tag> tags = dir.getTags();
                for (Tag tag : tags) {
                    boolean isInMap = false;
                    for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
                        if(entry.getValue().equals(tag.getDescription())) {
                            isInMap = true;
                        }
                    }

                    if(!isInMap) {
                        tagsMap.put(tag.getTagName(), tag.getDescription());
                    }
                }
            }

        } catch (ImageReadException | IOException | ImageProcessingException e) {
            throw new RuntimeException(e);
        }

        return tagsMap;
    }
}
