package lt.sdc.students.multimediatagger.metadata.exif;

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

import static lt.sdc.students.multimediatagger.metadata.exif.ExifReader.readExifTag;


public class ExifWriter {
    /**
     * Writes DateTimeOriginal (0x9003) EXIF tag with the date provided
     * <p>
     *
     * @param inputImage      <code>File</code> of the image to write into
     * @param outputImage     <code>String</code> of the export path
     * @param dateTimeToWrite <code>LocalDateTime</code> of the date to write
     * @return true if the field written successfully, false otherwise
     * @throws NullPointerException if inputImage does not exist
     * @throws NullPointerException if case outputImage is null
     * @see LocalDateTime
     * @see ExifTagConstants
     */
    public static boolean writeExifTagDateTimeOriginal(final File inputImage,
                                                       final File outputImage,
                                                       final LocalDateTime dateTimeToWrite) {
        if (!inputImage.isFile()) {
            throw new NullPointerException("inputImage is null or not a File");
        }
        if (Objects.isNull(outputImage)) {
            throw new NullPointerException("outputImage is null");
        }
        if (outputImage.isFile()) {
            throw new RuntimeException("outputImage must not exist");
        }
        if (inputImage.getAbsolutePath().equals(outputImage.getAbsolutePath())) {
            throw new RuntimeException("outputImage can not be inputImage");
        }

        try (FileOutputStream fos = new FileOutputStream(outputImage);
             OutputStream os = new BufferedOutputStream(fos)) {

            TiffOutputSet outputSet = null;

            final ImageMetadata metadata = Imaging.getMetadata(inputImage);
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

            if (jpegMetadata != null) {
                final TiffImageMetadata exif = jpegMetadata.getExif();
                if (null != exif) {
                    outputSet = exif.getOutputSet();
                }
            }

            if (outputSet == null) {
                outputSet = new TiffOutputSet();
            }

            TiffOutputField dateTimeOriginalField = outputSet.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            if (dateTimeOriginalField != null) {
                outputSet.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeString = dateTimeToWrite.format(formatter);

            TiffOutputField outputField = new TiffOutputField(
                    ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL,
                    FieldType.ASCII,
                    dateTimeString.length(),
                    dateTimeString.getBytes());

            TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
            exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
            exifDirectory.add(outputField);
            new ExifRewriter().updateExifMetadataLossless(inputImage, os, outputSet);

            return (null != readExifTag(outputImage, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL));
        } catch (ImageWriteException | ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean writeExifTagDateTimeOriginal(final String inputImagePath,
                                                       final String outputImagePath,
                                                       final LocalDateTime dateTimeToWrite) {

        File inputImage = new File(inputImagePath);
        File outputImage = new File(outputImagePath);

        if (!inputImage.isFile()) {
            throw new NullPointerException("inputImage does not exist");
        }
        if (outputImage.isFile()) {
            throw new RuntimeException("outputImage must not exist");
        }

        return writeExifTagDateTimeOriginal(inputImage, outputImage, dateTimeToWrite);
    }
}
