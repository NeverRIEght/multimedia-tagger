package lt.esde.students;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    /**
     * Path to the main project folder
     */
    public static final String PROJECT_PATH;
    /**
     * Path to the source code folder
     */
    public static final String SOURCE_CODE_PATH;
    /**
     * Name of the folder with test images
     */
    public static final String TEST_FOLDER_NAME;
    /**
     * Path to the folder with test images
     */
    public static final String TEST_IMG_FOLDER_PATH;

    /**
     * Test jpg file, which has exif field with creation date
     */
    public static final File TEST_IMG_WITH_METADATA_PATH;

    /**
     * Test jpg file, which has no exif field with creation date
     */
    public static final File TEST_IMG_WITHOUT_METADATA_PATH;

    /**
     * Test jpg files, which are the real photos with lots of metadata
     */
    public static final List<File> TEST_IMG_PHOTOS_LIST;

    static {
        PROJECT_PATH = Paths.get("")
                .toAbsolutePath() + "";
        SOURCE_CODE_PATH = PROJECT_PATH +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "java" +
                File.separator + "lt" +
                File.separator + "esde" +
                File.separator + "students";
        TEST_FOLDER_NAME = "testimg";
        TEST_IMG_FOLDER_PATH = PROJECT_PATH + File.separator + TEST_FOLDER_NAME;
        TEST_IMG_WITH_METADATA_PATH = new File(TEST_IMG_FOLDER_PATH + File.separator + "eifel.jpg");
        TEST_IMG_WITHOUT_METADATA_PATH = new File(TEST_IMG_FOLDER_PATH + File.separator + "maricat.jpg");
        TEST_IMG_PHOTOS_LIST = List.of(
                new File(TEST_IMG_FOLDER_PATH + File.separator + "IMG_0001.JPG"),
                new File(TEST_IMG_FOLDER_PATH + File.separator + "IMG_0002.JPG"),
                new File(TEST_IMG_FOLDER_PATH + File.separator + "IMG_0003.JPG"),
                new File(TEST_IMG_FOLDER_PATH + File.separator + "IMG_0004.JPG"));
    }

    public static void main(String[] args) {

    }
}