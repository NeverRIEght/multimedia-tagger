package dev.mkomarov.multimediatagger.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import dev.mkomarov.multimediatagger.metadata.entity.ExifTag;
import dev.mkomarov.multimediatagger.utils.FileUtil;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExifReader {

    private ExifReader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Parses the file's EXIF tags using Apache Commons Imaging and Metadata Extractor libraries.
     * <p>Can produce duplicates.
     *
     * @param fromFile file to parse
     * @return <code>Hashmap</code> of the non-null fields set. Might be null if the set is empty
     */
    public static List<ExifTag> readExifTags(File fromFile) {
        FileUtil.checkFile(fromFile);

        List<ExifTag> output = new ArrayList<>();

        output.addAll(readExifTagsApache(fromFile));
        output.addAll(readExifTagsMetadataExtractor(fromFile));

        return output.stream().distinct().toList();
    }

    private static List<ExifTag> readExifTagsApache(File fromFile) {
        List<ExifTag> apacheTags = new ArrayList<>();

        try {
            final ImageMetadata apacheMetadata = Imaging.getMetadata(fromFile);

            if (apacheMetadata instanceof JpegImageMetadata jpegMetadata) {
                final List<ImageMetadata.ImageMetadataItem> items = jpegMetadata.getItems();

                for (final ImageMetadata.ImageMetadataItem item : items) {
                    String tagString = item.toString();
                    String tagName = tagString.substring(0, tagString.indexOf(":"));
                    String tagValue = tagString.substring(tagString.indexOf(":") + 2);
                    apacheTags.add(new ExifTag(tagName, tagValue));
                }
            }
        } catch (ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }

        return apacheTags;
    }

    private static List<ExifTag> readExifTagsMetadataExtractor(File fromFile) {
        List<ExifTag> extractorTags = new ArrayList<>();

        try {
            Metadata extractorMetadata = ImageMetadataReader.readMetadata(fromFile);
            Iterable<Directory> directories = extractorMetadata.getDirectories();

            for (Directory directory : directories) {
                for (Tag tag : directory.getTags()) {
                    extractorTags.add(new ExifTag(tag.getTagName(), tag.getDescription()));
                }
            }

        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }

        return extractorTags;
    }
}
