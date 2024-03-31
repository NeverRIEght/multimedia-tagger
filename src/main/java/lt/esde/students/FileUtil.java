package lt.esde.students;

import java.io.File;
import java.util.Objects;

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
    public static String getFileExtension(File file) {
        if (Objects.isNull(file)) {
            throw new NullPointerException("The file is null");
        }

        int index = file.getName().lastIndexOf('.');
        if (index == -1) {
            return "";
        }

        return file.getName().substring(index + 1);
    }
}