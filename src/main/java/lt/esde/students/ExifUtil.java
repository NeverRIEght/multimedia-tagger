package lt.esde.students;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffContents;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static lt.esde.students.FileUtil.getFileExtension;
import static lt.esde.students.Main.*;

public class ExifUtil {

    public static final int DATE_TIME_ORIGINAL = 0x9003;
    public static ImageMetadata getMetadata(File inputImage) throws IOException, ImageReadException {
        return Imaging.getMetadata(inputImage);
    }



    public static boolean writeExifTag(File inputImage, String outputImage, int exifTag) throws IOException, ImageReadException, ParseException {
        if(Objects.isNull(inputImage)) {
            throw new NullPointerException("inputImage is null");
        }

        if(outputImage.isEmpty()) {
            throw new NullPointerException("outputImage is not defined");
        }

        // TODO: Add every single exifTag
        // TODO: Find out file extension and proceed accordingly

//        if(getFileExtension(inputImage).equals(".jpg")) {
//
//        }

        if(exifTag == DATE_TIME_ORIGINAL) {
            final ImageMetadata metadata = getMetadata(inputImage);

            if(Objects.isNull(metadata)) {
                // TODO: Create new empty set of metadata
            } else {
                // TODO: Parse the system field "creation date", than copy it to metadata

            }
        }

        return true;
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
