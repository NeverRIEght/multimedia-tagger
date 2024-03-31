package lt.esde.students;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUtilTest {
    @Test
    void convertToTiff() {

    }

    @Test
    void getCreationDateTime() {

    }

    @Test
    void getFileExtensionTxt() {
        File testTxtFile = new File("Example.txt");
        assertEquals(".txt", FileUtil.getFileExtension(testTxtFile));

    }

    @Test
    void getFileExtensionExe() {
        File testExeFile = new File("ExeFile.exe");

        File testNullFile = null;
        assertEquals(".exe", FileUtil.getFileExtension(testExeFile));
    }

    @Test
    void getFileWithoutException() {
        File testWithoutExceptionFile = new File("FileWithoutException");

        assertEquals("Incorrect File", FileUtil.getFileExtension(testWithoutExceptionFile));
    }

    @Test
    void getFileExtensionWitchSpace() {
        File testFileWitchSpace = new File("File witch space.txt");

        assertEquals(".txt", FileUtil.getFileExtension(testFileWitchSpace));
    }

    @Test
    void getFileExtensionWitchDoublePoint() {
        File testFileWitchDoublePoint = new File("FileWitchDoublePoint..txt");

        assertEquals(".txt", FileUtil.getFileExtension(testFileWitchDoublePoint));
    }

    @Test
    void getFileExtensionWitchNull() {
        File nullFile = null;

        assertThrows(NullPointerException.class, () -> {
            FileUtil.getFileExtension(nullFile);
        });
    }
}