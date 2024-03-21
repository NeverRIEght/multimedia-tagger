package lt.esde.students;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static lt.esde.students.FileUtil.getCreationDateTime;

public class ExifUtil {

    public static boolean writeExifTagDateTimeOriginal(final File inputImage, final String outputImage) throws IOException {
        LocalDateTime dateTimeToWrite = getCreationDateTime(inputImage.getAbsolutePath());
        return writeExifTagDateTimeOriginal(inputImage, outputImage, dateTimeToWrite);
    }

    public static boolean writeExifTagDateTimeOriginal(final File inputImage, final String outputImage, LocalDateTime dateTimeToWrite) {
        boolean returnValue = false;

        if (Objects.isNull(inputImage)) {
            throw new NullPointerException("inputImage is null");
        } else if (outputImage.isEmpty()) {
            throw new NullPointerException("outputImage is not defined");
        } else if (Objects.isNull(dateTimeToWrite)) {
            throw new NullPointerException("dateTimeToWrite is null");
        }

        try (FileOutputStream fos = new FileOutputStream(outputImage);
             OutputStream os = new BufferedOutputStream(fos)) {

            TiffOutputSet outputSet = null;

            final ImageMetadata metadata = Imaging.getMetadata(inputImage);
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            if (null != jpegMetadata) {
                // note that exif might be null if no Exif metadata is found.
                final TiffImageMetadata exif = jpegMetadata.getExif();

                if (null != exif) {
                    // TiffImageMetadata class is immutable (read-only).
                    // TiffOutputSet class represents the Exif data to write.
                    //
                    // Usually, we want to update existing Exif metadata by
                    // changing
                    // the values of a few fields, or adding a field.
                    // In these cases, it is easiest to use getOutputSet() to
                    // start with a "copy" of the fields read from the image.
                    outputSet = exif.getOutputSet();
                    returnValue = true;
                }
            }

            // if file does not contain any exif metadata, we create an empty
            // set of exif metadata. Otherwise, we keep all the other
            // existing tags.
            if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }

            // check if field already EXISTS - if so remove
            TiffOutputField imageHistoryPre = outputSet.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            if (imageHistoryPre != null) {
                System.out.println("REMOVE");
                outputSet.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            }

            // add field
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeString = dateTimeToWrite.format(formatter);

            TiffOutputField imageDateTimeOriginal = new TiffOutputField(
                    ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL,
                    FieldType.ASCII,
                    dateTimeString.length(),
                    dateTimeString.getBytes());

            TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
            exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            exifDirectory.add(imageDateTimeOriginal);
            new ExifRewriter().updateExifMetadataLossless(inputImage, os, outputSet);
        } catch (ImageWriteException | ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }

        return returnValue;
    }


}
