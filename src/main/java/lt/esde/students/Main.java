package lt.esde.students;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static lt.esde.students.metadata.exif.ExifReader.readExifTags;

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

    public static void main(String[] args) throws Exception {

        HashMap<String, String> map = readExifTags(new File(TEST_IMG_WITH_METADATA_PATH));


        Metadata metadata = ImageMetadataReader.readMetadata(new File(TEST_IMG_WITH_METADATA_PATH));

        Iterable<Directory> directories = metadata.getDirectories();
        for (Directory dir : directories) {
            Collection<Tag> tags = dir.getTags();
            for (Tag tag : tags) {
//                System.out.println(tag.getTagName() + ":" + tag.getDescription());
                map.put(tag.getTagName(), tag.getDescription());
            }
        }

        for (Map.Entry<String, String> item : map.entrySet()) {
            String itemValueStr = item.getValue();
            String itemKeyStr = item.getKey();

            Pattern dateValuePattern = Pattern.compile("[0-9]{2}\\u003A[0-9]{2}\\u003A[0-9]{2}");
            Matcher dateValueMatcher = dateValuePattern.matcher(itemValueStr);
            Pattern dateKeyPattern = Pattern.compile("date|Date");
            Matcher dateKeyMatcher = dateKeyPattern.matcher(itemKeyStr);

            if (dateValueMatcher.find() || dateKeyMatcher.find()) {
                System.out.print(itemKeyStr + ":");
                System.out.println(itemValueStr.replaceAll(", ", "\n"));
            }
        }

//        for (int i = 0; i < map.size(); i++) {
//            //System.out.println(map.keySet().toArray()[i] + " - " + map.get(map.keySet().toArray()[i]));
//            System.out.println(map.entrySet());
//        }

    }
}