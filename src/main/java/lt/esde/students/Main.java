package lt.esde.students;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.file.FileSystemDirectory;
import com.drew.metadata.file.FileSystemMetadataReader;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffEpTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
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

        HashMap<String, String> tagsMap1 = parseFileMetata(new File(TEST_IMG_WITH_METADATA_PATH));

        Metadata metadata = ImageMetadataReader.readMetadata(new File(TEST_IMG_WITH_METADATA_PATH));

        String content1 = tagsMap1.entrySet()
                .stream()
                .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
                .collect(Collectors.joining("\n"));

        System.out.println(content1);

        System.out.println("--------");



        // obtain the Exif directory
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(FileSystemDirectory.class);
        com.drew.metadata.file.FileSystemMetadataReader reader = new FileSystemMetadataReader();


        // query the tag's value
        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

        Iterable<Directory> directories = metadata.getDirectories();
        Iterator<Directory> iterator = directories.iterator();
        while(iterator.hasNext()) {
            Directory dir = iterator.next();
            Collection<Tag> tags = dir.getTags();
            for(Tag tag: tags) {
                System.out.println(tag.getTagName() + " - " + tag.getDescription() + " - " + tag.getTagTypeHex());
            }
        }

    }
}