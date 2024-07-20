package lt.sdc.students.multimediatagger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    /**
     * This method returns the extension of the provided file
     *
     * @param ofFile file to get extension from
     * @return Extension of the provided file in lowercase ("txt", "exe", etc.)
     * @throws NullPointerException     if provided file is null or not a file
     * @throws IllegalArgumentException if provided file has no extension
     */
    public static String getFileExtension(File ofFile) {
        if (!ofFile.isFile()) {
            throw new NullPointerException("Provided file is not a file");
        }

        return getFileExtension(ofFile.getAbsolutePath());
    }

    /**
     * This method returns the extension based on the provided path to file. Can be used with non-existing files.
     *
     * @param path path to the file to get extension from
     * @return Extension of the provided file in lowercase ("txt", "exe", etc.)
     * @throws IllegalArgumentException if provided file has no extension
     */
    public static String getFileExtension(String path) {
        int startIndex = path.contains(File.separator) ? path.lastIndexOf(File.separator) + 1 : 0;
        String fileName = path.substring(startIndex);

        Pattern extensionPattern = Pattern.compile("\\.(\\w{1,4})$");
        Matcher extensionmatcher = extensionPattern.matcher(fileName);

        if (extensionmatcher.find()) {
            return extensionmatcher.group(1).toLowerCase();
        } else {
            throw new IllegalArgumentException("File \"" + fileName + "\" has no extension");
        }
    }
}