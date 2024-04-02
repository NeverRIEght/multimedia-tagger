package lt.esde.students;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    /**
     * Path to the folder with test images
     */
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg";

    /**
     * Path to the test jpg file, which has exif field with creation date
     */
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "eifel.jpg";

    /**
     * Path to the test jpg file, which has no exif field with creation date
     */
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "maricat.jpg";

    public static void main(String[] args) {

//        HashMap<String, String> map = readExifTags(new File(TEST_IMG_WITH_METADATA_PATH));
//
//        for (int i = 0; i < map.size(); i++) {
//            System.out.println(map.keySet().toArray()[i] + " - " + map.get(map.keySet().toArray()[i]));
//        }

//        for (Map.Entry<String, String> item : map.entrySet()) {
//            String itemValueStr = item.getValue();
//            String itemKeyStr = item.getKey();
//
//            Pattern dateValuePattern = Pattern.compile("[0-9]{2}\\u003A[0-9]{2}\\u003A[0-9]{2}");
//            Matcher dateValueMatcher = dateValuePattern.matcher(itemValueStr);
//            Pattern dateKeyPattern = Pattern.compile("date|Date");
//            Matcher dateKeyMatcher = dateKeyPattern.matcher(itemKeyStr);
//
//            if (dateValueMatcher.find() || dateKeyMatcher.find()) {
//                System.out.print(itemKeyStr + ":");
//                System.out.println(itemValueStr.replaceAll(", ", "\n"));
//            }
//        }


    }
}