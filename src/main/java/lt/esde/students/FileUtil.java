package lt.esde.students;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    /**
     * Returns extension of the provided file as a <code>String</code>
     * <p>If there is no extension, method will return empty <code>String</code>
     * <p>Examples of return: "exe", "txt", etc.
     * <p>
     *
     * @param file <code>File</code> to get extension of
     * @return <code>File</code> extension as a <code>String</code>.
     * @see File
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