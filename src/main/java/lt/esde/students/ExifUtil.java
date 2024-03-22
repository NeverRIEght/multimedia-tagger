package lt.esde.students;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static lt.esde.students.FileUtil.getCreationDateTime;

public class ExifUtil {
    /**
     * Writes DateTimeOriginal (0x9003) EXIF tag with the oldest date from file attributes
     * <p>
     * @param inputImage <code>File</code> of the image to write into
     * @param outputImage <code>String</code> of the export path
     * @return true if the field written successfully, false otherwise
     * @throws Exception in case inputImage does not exist
     * @see lt.esde.students.FileUtil#getCreationDateTime(String)
     */
    public static boolean writeExifTagDateTimeOriginal(final File inputImage,
                                                       final String outputImage) throws Exception {
        LocalDateTime dateTimeToWrite = getCreationDateTime(inputImage.getAbsolutePath());
        return writeExifTagDateTimeOriginal(inputImage, outputImage, dateTimeToWrite);
    }

    /**
     * Writes DateTimeOriginal (0x9003) EXIF tag with the date provided
     * <p>
     * @param inputImage <code>File</code> of the image to write into
     * @param outputImage <code>String</code> of the export path
     * @param dateTimeToWrite <code>LocalDateTime</code> of the date to write
     * @return true if the field written successfully, false otherwise
     * @throws Exception in case inputImage does not exist
     */
    public static boolean writeExifTagDateTimeOriginal(final File inputImage,
                                                       final String outputImage,
                                                       final LocalDateTime dateTimeToWrite) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = dateTimeToWrite.format(formatter);

        return writeExifTag(inputImage, outputImage, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, dateTimeString);
    }

    /**
     * Writes the <code>String</code> provided to the EXIF tag field of the provided file
     * <p><code>false</code> return can indicate the problem with the formatting of <code>contents</code>
     * <p>
     * @param inputImage <code>File</code> of the image to write into
     * @param outputImage <code>String</code> of the export path
     * @param tagInfo <code>TagInfo</code> of the EXIF tag to write
     * @param contents <code>String</code> with the value to write to EXIF
     * @return true if the field written successfully, false otherwise
     * @throws Exception if something went wrong
     */
    public static boolean writeExifTag(final File inputImage,
                                       final String outputImage,
                                       final TagInfo tagInfo,
                                       final String contents) throws Exception {
        // TODO: contents parsing???

        if (Objects.isNull(inputImage)) {
            throw new NullPointerException("inputImage is null");
        } else if (Objects.isNull(outputImage)) {
            throw new NullPointerException("outputImage is null");
        } else if (Objects.isNull(tagInfo)) {
            throw new NullPointerException("tagInfo is null");
        } else if (contents.isEmpty()) {
            throw new NullPointerException("contents is empty");
        }

        try (FileOutputStream fos = new FileOutputStream(outputImage);
             OutputStream os = new BufferedOutputStream(fos)) {

            TiffOutputSet outputSet = null;

            final ImageMetadata metadata = Imaging.getMetadata(inputImage);
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

            if (null != jpegMetadata) {
                final TiffImageMetadata exif = jpegMetadata.getExif();
                if (null != exif) {
                    outputSet = exif.getOutputSet();
                }
            }

            if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }


            TiffOutputField imageHistoryPre = outputSet.findField(tagInfo);
            if (imageHistoryPre != null) {
                System.out.println("REMOVE");
                outputSet.removeField(tagInfo);
            }

            FieldType fieldType;
            if(tagInfo.dataTypes.size() == 1) {
                fieldType = tagInfo.dataTypes.getFirst();
            } else {
                throw new Exception("This type of field is not supported");
            }


            TiffOutputField outputField = new TiffOutputField(
                    tagInfo,
                    fieldType,
                    contents.length(),
                    contents.getBytes());

            TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
            exifDirectory.removeField(tagInfo);
            exifDirectory.add(outputField);
            new ExifRewriter().updateExifMetadataLossless(inputImage, os, outputSet);

            return (null != readExifTag(new File(outputImage), tagInfo));
        } catch (ImageWriteException | ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static HashMap<String, String> readExifTags(final File fromFile) {

        HashMap<String, String> tagsMap = new HashMap<>();

        try {
            final ImageMetadata metadata = Imaging.getMetadata(fromFile);
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                final List<ImageMetadata.ImageMetadataItem> items = jpegMetadata.getItems();

                for (final ImageMetadata.ImageMetadataItem item : items) {
                    String tagString = item.toString();
                    tagsMap.put(tagString.substring(0, tagString.indexOf(":")), tagString.substring(tagString.indexOf(":") + 2));
                }
            }
        } catch (ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }

        return tagsMap;
    }
}
