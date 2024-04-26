package lt.esde.students;

import java.io.File;
import java.nio.file.Paths;

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

    }
}