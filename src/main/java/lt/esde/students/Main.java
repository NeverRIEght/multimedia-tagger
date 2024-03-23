package lt.esde.students;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffEpTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;

import static lt.esde.students.ExifUtil.*;

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

//        HashMap<String, String> tagsMap1 = parseFileMetata(new File(TEST_IMG_WITH_METADATA_PATH));
        HashMap<String, String> tagsMap2 = readExifTags(new File(TEST_IMG_WITH_METADATA_PATH));

        Metadata metadata = ImageMetadataReader.readMetadata(new File(TEST_IMG_WITH_METADATA_PATH));




//        String content1 = tagsMap1.entrySet()
//                .stream()
//                .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
//                .collect(Collectors.joining("\n"));

        String content2 = tagsMap2.entrySet()
                .stream()
                .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
                .collect(Collectors.joining("\n"));



        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }

        System.out.println("\n\n\n");
        System.out.println(content2);

    }
}