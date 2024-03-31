package lt.esde.students;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

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
        Metadata metadata = ImageMetadataReader.readMetadata(new File(TEST_IMG_WITH_METADATA_PATH));

        Iterable<Directory> directories = metadata.getDirectories();
        Iterator<Directory> iterator = directories.iterator();
        while (iterator.hasNext()) {
            Directory dir = iterator.next();
            Collection<Tag> tags = dir.getTags();
            for (Tag tag : tags) {
                System.out.println(tag.getTagName() + " - " + tag.getDescription() + " - " + tag.getTagTypeHex());
            }
        }

    }
}