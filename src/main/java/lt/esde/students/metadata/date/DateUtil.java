package lt.esde.students.metadata.date;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static lt.esde.students.metadata.exif.ExifReader.readExifTags;

public class DateUtil {
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

        Map<String, String> map = readExifTags(fromFile);
        List<String> dateStrings = new ArrayList<>();

        Pattern timePattern = Pattern.compile("[0-9]{2}\\u003A[0-9]{2}\\u003A[0-9]{2}");
        Pattern dateKeyPattern = Pattern.compile("date|Date");

        String datePatternString1 = "([0-9]{4}\\u003A[0-9]{2}\\u003A[0-9]{2})";
        String datePatternString2 = "([0-9]{4}\\u002D[0-9]{2}\\u002D[0-9]{2})";
        Pattern datePattern = Pattern.compile(datePatternString1 + "|" + datePatternString2);

        for (Map.Entry<String, String> item : map.entrySet()) {
            String itemValueStr = item.getValue();
            String itemKeyStr = item.getKey();

            Matcher dateValueMatcher = timePattern.matcher(itemValueStr);
            Matcher dateKeyMatcher = dateKeyPattern.matcher(itemKeyStr);

            if (dateValueMatcher.find() || dateKeyMatcher.find()) {
                dateStrings.add(itemValueStr);
            }
        }

        List<LocalDateTime> outputDates = new ArrayList<>();

        for (String currentDateString : dateStrings) {
            Matcher dateMatcher = datePattern.matcher(currentDateString);
            String dateString = "";
            if (dateMatcher.find()) {
                int startIndex = dateMatcher.start();
                int endIndex = dateMatcher.end();
                dateString = currentDateString.substring(startIndex, endIndex);
                dateString = dateString.replaceAll(":", "-");
                currentDateString = currentDateString.substring(endIndex);
            }

            if (dateString.isEmpty()) {
                Pattern monthDayPattern = Pattern.compile(
                        "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s[0-9]{2}"
                );
                Pattern yearPattern = Pattern.compile("\\u003A[0-9]{2}\\s[0-9]{4}");

                Matcher monthDayMatcher = monthDayPattern.matcher(currentDateString);
                String monthString = "";
                String dayString = "";
                if (monthDayMatcher.find()) {
                    int startIndex = monthDayMatcher.start();
                    int endIndex = monthDayMatcher.end();
                    monthString = currentDateString.substring(startIndex, endIndex - 3);
                    dayString = currentDateString.substring(startIndex + 4, endIndex);
                }

                Matcher yearMatcher = yearPattern.matcher(currentDateString);
                String yearString = "";
                if (yearMatcher.find()) {
                    int startIndex = yearMatcher.start();
                    int endIndex = yearMatcher.end();
                    yearString = currentDateString.substring(startIndex + 4, endIndex);
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

            Matcher timeMatcher = timePattern.matcher(currentDateString);
            String timeString = "";
            if (timeMatcher.find()) {
                int startIndex = timeMatcher.start();
                int endIndex = timeMatcher.end();
                timeString = currentDateString.substring(startIndex, endIndex);
            }

            if (!timeString.isEmpty() && !dateString.isEmpty()) {
                LocalDateTime dateTime = LocalDateTime.parse(dateString + "T" + timeString);
                outputDates.add(dateTime);
            }
        }

        outputDates = outputDates.stream().distinct().collect(Collectors.toList());

        return outputDates;
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
