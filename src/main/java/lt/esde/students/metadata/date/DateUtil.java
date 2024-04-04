package lt.esde.students.metadata.date;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static lt.esde.students.metadata.exif.ExifReader.readExifTags;

public class DateUtil {
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
