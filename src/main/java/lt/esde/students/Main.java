package lt.esde.students;

import lt.esde.students.metadata.exif.entities.ExifTag;
import lt.esde.students.metadata.exif.scraper.ExifTagsScraper;
import lt.esde.students.serializer.JsonSerializer;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    /**
     * Name of the folder with test images
     */
    public static final String TEST_FOLDER_NAME = "testimg";
    /**
     * Path to the folder with test images
     */
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + TEST_FOLDER_NAME;

    /**
     * Path to the test jpg file, which has exif field with creation date
     */
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + TEST_FOLDER_NAME + File.separator + "eifel.jpg";

    /**
     * Path to the test jpg file, which has no exif field with creation date
     */
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + TEST_FOLDER_NAME + File.separator + "maricat.jpg";

    public static void main(String[] args) {
        List<ExifTag> exifTags = ExifTagsScraper.scrapAllExistingExifTags();
        JSONObject jsonTags = JsonSerializer.serializeExifTagsToJson(exifTags);

        JsonSerializer.saveJsonAsFile(jsonTags, TEST_IMG_FOLDER_PATH + File.separator + "test.json");

        //System.out.println(exifTags);

        //ExifReader.readExifTags(TEST_IMG_PHOTOS_LIST.getFirst());

//        for(File f : TEST_IMG_PHOTOS_LIST) {
//            Map<String, String> tagsMap = ExifReader.readExifTags(f);
//            System.out.println("--------------------------------------");
//            System.out.println("--------------------------------------");
//            System.out.println(f.getAbsolutePath());
//            System.out.println("--------------------------------------");
//            System.out.println("--------------------------------------");
//
//            int maximumLength = -1;
//
//            for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
//                int currentLength = entry.getKey().length();
//                if (currentLength > maximumLength) {
//                    maximumLength = currentLength;
//                }
//            }
//
//            for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
//                int currentLength = entry.getKey().length();
//                StringBuilder sb = new StringBuilder();
//                sb.append(entry.getKey());
//                sb.append((" ").repeat(maximumLength - currentLength));
//                sb.append(" -> ");
//                sb.append(entry.getValue());
//                System.out.println(sb);
//            }
//        }
    }
}