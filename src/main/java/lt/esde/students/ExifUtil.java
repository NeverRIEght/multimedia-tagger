package lt.esde.students;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffContents;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static lt.esde.students.FileUtil.getCreationDateTime;
import static lt.esde.students.FileUtil.getFileExtension;
import static lt.esde.students.Main.*;

public class ExifUtil {

    public static final int DATE_TIME_ORIGINAL = 0x9003;
    public static ImageMetadata getMetadata(File inputImage) throws IOException, ImageReadException {
        return Imaging.getMetadata(inputImage);
    }


    /**
     *
     * @param inputImage File with input image
     * @param outputImage Path to which output image will be exported
     * @param exifTag int value, TagID
     * @return <Code>true</Code> if the Exif tag exists before and <Code>false</Code> otherwise
     * @throws IOException
     * @throws ImageReadException
     * @throws ParseException
     */
    public static boolean writeExifTag(final File inputImage, final String outputImage, final int exifTag) throws IOException, ImageReadException, ParseException {
        boolean returnValue = false;

        if(Objects.isNull(inputImage)) {
            throw new NullPointerException("inputImage is null");
        }

        if(outputImage.isEmpty()) {
            throw new NullPointerException("outputImage is not defined");
        }

        try (FileOutputStream fos = new FileOutputStream(new File(outputImage));
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
            // set of exif metadata. Otherwise, we keep all of the other
            // existing tags.
            if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }

            if (outputSet != null) {

                // check if field already EXISTS - if so remove
                TiffOutputField imageHistoryPre = outputSet.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                if (imageHistoryPre != null) {
                    System.out.println("REMOVE");
                    outputSet.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                }
                // add field
                try {

                    LocalDateTime dateTime = getCreationDateTime(inputImage.getAbsolutePath());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String dateTimeString = dateTime.format(formatter);

//                    String fieldData = "Hallo";
                    TiffOutputField imageDateTimeOriginal = new TiffOutputField(
                            ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL,
                            FieldType.BYTE,
                            dateTimeString.length(),
                            dateTimeString.getBytes());

                    TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
                    exifDirectory.removeField(DATE_TIME_ORIGINAL);
                    exifDirectory.add(imageDateTimeOriginal);
                    new ExifRewriter().updateExifMetadataLossless(inputImage, os, outputSet);
                } catch (ImageWriteException e) {
                    e.printStackTrace();
                }
            }

        } catch (ImageWriteException e) {
            throw new RuntimeException(e);
        }

        return returnValue;
    }



    public static void updateHourOnFile(String inputFileName, String outputFileName, int hourChange) throws ImageReadException, IOException, ParseException, ImageWriteException {
        File inputFile = new File(inputFileName);
        final ImageMetadata metadata = getMetadata(inputFile);

        if (metadata != null && metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            System.out.println("we have metadata");
            final TiffField dateTimeField = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
            SimpleDateFormat SDF = new SimpleDateFormat("yyyy:MM:dd kk:mm:ss");
            Date pictureDate = SDF.parse((String) dateTimeField.getValue());
            System.out.println("date: " + pictureDate);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(pictureDate);
            cal.add(Calendar.HOUR, hourChange);
            System.out.println("new date time: " + cal.getTime());

            TiffOutputSet outputSet = null;
            try (FileOutputStream fos = new FileOutputStream(new File(outputFileName)); OutputStream os = new BufferedOutputStream(fos)) {
                final TiffImageMetadata exif = jpegMetadata.getExif();
                outputSet = exif.getOutputSet();
                if (outputSet != null) {
                    final TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
                    FieldType originalFieldType = null;
                    TagInfo originalTagInfo = null;
                    TiffOutputField dateTimeFieldOriginal = exifDirectory.findField(DATE_TIME_ORIGINAL); // DateTimeOriginal
                    if (dateTimeFieldOriginal != null) {
                        originalFieldType = dateTimeFieldOriginal.fieldType;
                        originalTagInfo = dateTimeFieldOriginal.tagInfo;
                    }

                    exifDirectory.removeField(DATE_TIME_ORIGINAL);
                    String updatedDateString = SDF.format(cal.getTime());
                    final TiffOutputField dateTimeOutputField = new TiffOutputField(originalTagInfo, originalFieldType, updatedDateString.length(), updatedDateString.getBytes());
                    exifDirectory.add(dateTimeOutputField);
                    new ExifRewriter().updateExifMetadataLossless(inputFile, os, outputSet);
                } else {
                    System.out.println("no cigar on output set from file " + inputFileName);
                }
            }
        } else {
            System.out.println("no cigar on file " + inputFileName);
        }

    }

    public static void main(String[] args) throws ImageWriteException, ImageReadException, IOException, ParseException {
        String inputFileName = TEST_IMG_WITH_METADATA_PATH;
        String outputFileName = TEST_IMG_FOLDER_PATH + "output.tiff";
        int hoursChange = -500;
        updateHourOnFile(inputFileName, outputFileName, hoursChange);
    }
}
