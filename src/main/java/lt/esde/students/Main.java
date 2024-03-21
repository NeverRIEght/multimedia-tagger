package lt.esde.students;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg";
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "eifel.jpg";
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + "testimg" + File.separator + "maricat.jpg";

    public static void main(String[] args) throws Exception {

    }
}