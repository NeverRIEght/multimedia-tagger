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
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                        if (entry.getValue().equals(tag.getDescription())) {
                            isInMap = true;
                        }
                    }

                    if (!isInMap) {
                        tagsMap.put(tag.getTagName(), tag.getDescription());
                    }
                }
            }

        } catch (ImageReadException | IOException | ImageProcessingException e) {
            throw new RuntimeException(e);
        }

        return tagsMap;
    }

    public static List<LocalDateTime> getAllMetadataDates(File fromFile) {
        Locale.setDefault(Locale.ENGLISH);

        HashMap<String, String> map = readExifTags(fromFile);
        List<String> dateStrings = new ArrayList<>();

        for (Map.Entry<String, String> item : map.entrySet()) {
            String itemValueStr = item.getValue();
            String itemKeyStr = item.getKey();

            Pattern dateValuePattern = Pattern.compile("[0-9]{2}\\u003A[0-9]{2}\\u003A[0-9]{2}");
            Matcher dateValueMatcher = dateValuePattern.matcher(itemValueStr);
            Pattern dateKeyPattern = Pattern.compile("date|Date");
            Matcher dateKeyMatcher = dateKeyPattern.matcher(itemKeyStr);

            if (dateValueMatcher.find() || dateKeyMatcher.find()) {
                dateStrings.add(itemValueStr);
            }
        }

        System.out.println(dateStrings.toString().replace(", ", "\n"));


        List<LocalDateTime> dates = new ArrayList<>();

        Pattern timePattern = Pattern.compile("[0-9]{2}\\u003A[0-9]{2}\\u003A[0-9]{2}");
        String datePatternString1 = "([0-9]{4}\\u003A[0-9]{2}\\u003A[0-9]{2})";
        String datePatternString2 = "([0-9]{4}\\u002D[0-9]{2}\\u002D[0-9]{2})";
        Pattern datePattern = Pattern.compile(datePatternString1 + "|" + datePatternString2);

        for (int i = 0; i < dateStrings.size(); i++) {
            String currentString = dateStrings.get(i);

            Matcher dateMatcher = datePattern.matcher(currentString);
            String dateString = "";
            if (dateMatcher.find()) {
                int startIndex = dateMatcher.start();
                int endIndex = dateMatcher.end();
                dateString = currentString.substring(startIndex, endIndex);
                dateString = dateString.replaceAll(":", "-");
                currentString = currentString.substring(endIndex);
            }

            Matcher timeMatcher = timePattern.matcher(currentString);
            String timeString = "";
            if (timeMatcher.find()) {
                int startIndex = timeMatcher.start();
                int endIndex = timeMatcher.end();
                timeString = currentString.substring(startIndex, endIndex);
            }

            //if() Fri Mar 29 20:35:59 +02:00 2024 parse
            if (dateString.isEmpty()) {
                Pattern monthDayPattern = Pattern.compile(
                        "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s[0-9]{2}"
                );
                Pattern yearPattern = Pattern.compile("\\u003A[0-9]{2}\\s[0-9]{4}");

                Matcher monthDayMatcher = monthDayPattern.matcher(currentString);
                String monthString = "";
                String dayString = "";
                if (monthDayMatcher.find()) {
                    int startIndex = monthDayMatcher.start();
                    int endIndex = monthDayMatcher.end();
                    monthString = currentString.substring(startIndex, endIndex - 3);
                    dayString = currentString.substring(startIndex + 4, endIndex);
                }

                Matcher yearMatcher = yearPattern.matcher(currentString);
                String yearString = "";
                if (yearMatcher.find()) {
                    int startIndex = yearMatcher.start();
                    int endIndex = yearMatcher.end();
                    yearString = currentString.substring(startIndex + 4, endIndex);
                }

                if (!monthString.isEmpty() && !dayString.isEmpty() && !yearString.isEmpty()) {
                    switch (monthString) {
                        case "Jan" -> monthString = "01";
                        case "Feb" -> monthString = "02";
                        case "Mar" -> monthString = "03";
                        case "Apr" -> monthString = "04";
                        case "May" -> monthString = "05";
                        case "Jun" -> monthString = "06";
                        case "Jul" -> monthString = "07";
                        case "Aug" -> monthString = "08";
                        case "Sep" -> monthString = "09";
                        case "Oct" -> monthString = "10";
                        case "Nov" -> monthString = "11";
                        case "Dec" -> monthString = "12";
                    }

                    dateString = yearString + "-" + monthString + "-" + dayString;
                }
            }

            if (!timeString.isEmpty() && !dateString.isEmpty()) {
                LocalDateTime dateTime = LocalDateTime.parse(dateString + "T" + timeString);
                dates.add(dateTime);
            }
        }

        dates = dates.stream().distinct().collect(Collectors.toList());

        return dates;
    }
}
