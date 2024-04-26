package lt.esde.students;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    /**
     * This method returns the extension of the provided file
     *
     * @param ofFile file to get extension from
     * @throws NullPointerException if provided file is null or not a file
     * @throws IllegalArgumentException if provided file has no extension
     * @return Extension of the provided file in lowercase ("txt", "exe", etc.)
     */
    public static String getFileExtension(File ofFile) {
        if (!ofFile.isFile()) {
            throw new NullPointerException("Provided file is not a file");
        }

        Pattern extensionPattern = Pattern.compile("\\.(\\w{1,4})$");
        Matcher extensionmatcher = extensionPattern.matcher(ofFile.getName());

        if (extensionmatcher.find()) {
            return extensionmatcher.group(1).toLowerCase();
        } else {
            throw new IllegalArgumentException("File \"" + ofFile.getName() + "\" has no extension");
        }
    }
}