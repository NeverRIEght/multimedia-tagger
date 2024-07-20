package dev.mkomarov.multimediatagger.metadata;

import dev.mkomarov.multimediatagger.metadata.entity.ExifTag;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String UNICODE_COLON = "\\u003A";
    private static final Pattern DATE_KEY_PATTERN = Pattern.compile("date|Date");
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}" +
            UNICODE_COLON +
            "\\d{2}" +
            UNICODE_COLON +
            "\\d{2})");

    private static final Pattern ADVANCED_DATE_PATTERN = Pattern.compile(
            "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)" +
                    "\\s(\\d{2}).+(\\d{4})");

    private static final Pattern TIME_PATTERN = Pattern.compile("\\D+(\\d{2}" +
            UNICODE_COLON +
            "\\d{2}" +
            UNICODE_COLON +
            "\\d{2})");
    /**
     * Method parses the metadata from the <code>File</code> and returns a <code>List</code> of dates inside of it.
     * <p> Can return an empty list
     * <p>
     *
     * @param fromFile <code>File</code> to parse metadata from
     * @return <code>List</code> of LocalDateTime contains the dates from the file
     */
    public static List<LocalDateTime> getAllMetadataDates(File fromFile) {
        if (!fromFile.isFile()) {
            throw new NullPointerException("File does not exist");
        }

        Locale.setDefault(Locale.ENGLISH);

        List<ExifTag> tagsMap = ExifReader.readExifTags(fromFile);
        Set<String> dateStrings = filterDateFields(tagsMap);

        List<LocalDateTime> outputDates = new ArrayList<>();

        for (String currentDateString : dateStrings) {
            String dateString = "";

            Matcher dateMatcher = DATE_PATTERN.matcher(currentDateString);
            if (dateMatcher.find()) {
                dateString = dateMatcher.group(1).replace(":", "-");
            }

            if (dateString.isEmpty()) {
                dateMatcher = ADVANCED_DATE_PATTERN.matcher(currentDateString);
                if (dateMatcher.find()) {
                    String dayString = dateMatcher.group(2);
                    String monthString = dateMatcher.group(1);
                    String yearString = dateMatcher.group(3);

                    monthString = Month.getNumericValue(monthString);

                    dateString = String.format("%s-%s-%s", yearString, monthString, dayString);
                }
            }

            String timeString = "";

            Matcher timeMatcher = TIME_PATTERN.matcher(currentDateString);
            if (timeMatcher.find()) {
                timeString = timeMatcher.group(1);
            }

            if (timeString.isEmpty()) {
                timeString = "00:00:00";
            }

            if (!dateString.isEmpty()) {
                LocalDateTime dateTime = LocalDateTime.parse(dateString + "T" + timeString);
                outputDates.add(dateTime);
            }
        }

        outputDates = outputDates.stream().distinct().collect(Collectors.toList());

        return outputDates;
    }

    private static Set<String> filterDateFields(List<ExifTag> tags) {
        Set<String> dateStrings = new HashSet<>();

        for (ExifTag tag : tags) {
            Matcher dateKeyMatcher = DATE_KEY_PATTERN.matcher(tag.name());

            if (dateKeyMatcher.find()) {
                dateStrings.add(tag.value().replace("'", ""));
            }
        }

        return dateStrings;
    }

    /**
     * Method finds and returns the oldest date from the <code>List</code> of dates
     * <p>
     *
     * @param dates <code>List</code> of LocalDateTime, the list of dates to search in
     * @return The oldest date from <code>dates</code>
     */
    public static LocalDateTime getOldestDate(List<LocalDateTime> dates) {
        if (Objects.isNull(dates)) {
            throw new NullPointerException("dates is null");
        }
        if (dates.isEmpty()) {
            throw new RuntimeException("dates is empty");
        }

        LocalDateTime oldestDate = dates.getFirst();

        for (int i = 1; i < dates.size() - 1; i++) {
            LocalDateTime currentDate = dates.get(i);
            if (oldestDate.isAfter(currentDate)) {
                oldestDate = currentDate;
            }
        }

        return oldestDate;
    }
}
