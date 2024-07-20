package lt.sdc.students.multimediatagger;

import lt.sdc.students.multimediatagger.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileUtilTest {
    @Test
    void getFileExtensionWithNullFile() {
        File nullFile = null;

        assertThrows(NullPointerException.class, () -> FileUtil.getFileExtension(nullFile));
    }

    @Test
    void getFileExtensionWithNoExtension() {
        File testFileWithoutExtension = new File("FileWithoutExtension");

        try {
            testFileWithoutExtension.createNewFile();
            assertThrows(IllegalArgumentException.class, () -> FileUtil.getFileExtension(testFileWithoutExtension));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileWithoutExtension.delete();
        }
    }

    @Test
    void getFileExtensionTxt() {
        String testStringTxt = "Example.txt";
        File testFileTxt = new File(testStringTxt);

        try {
            testFileTxt.createNewFile();
            assertEquals("txt", FileUtil.getFileExtension(testFileTxt));
            assertEquals("txt", FileUtil.getFileExtension(testStringTxt));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileTxt.delete();
        }
    }

    @Test
    void getFileExtensionExe() {
        String testStringExe = "ExeFile.exe";
        File testFileExe = new File(testStringExe);

        try {
            testFileExe.createNewFile();
            assertEquals("exe", FileUtil.getFileExtension(testFileExe));
            assertEquals("exe", FileUtil.getFileExtension(testStringExe));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileExe.delete();
        }
    }

    @Test
    void getFileExtensionWithSpaces() {
        String testStringWithSpace = "File with spaces.txt";
        File testFileWithSpace = new File(testStringWithSpace);

        try {
            testFileWithSpace.createNewFile();
            assertEquals("txt", FileUtil.getFileExtension(testFileWithSpace));
            assertEquals("txt", FileUtil.getFileExtension(testStringWithSpace));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileWithSpace.delete();
        }
    }

    @Test
    void getFileExtensionWithMultipleDots() {
        for (int i = 0; i < 10; i++) {
            int numberOfDots = new Random().nextInt(10) + 1;
            StringBuilder fileName = new StringBuilder("FileWithMultipleDots");

            fileName.append(".".repeat(numberOfDots));
            fileName.append("txt");

            File testFileWithPoints = new File(fileName.toString());

            try {
                testFileWithPoints.createNewFile();
                assertEquals("txt", FileUtil.getFileExtension(testFileWithPoints));
                assertEquals("txt", FileUtil.getFileExtension(fileName.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                testFileWithPoints.delete();
            }
        }
    }

    @Test
    void getFileExtensionWithBigLetters() {
        String testStringWithBigLetters = "FileWithBigLetters.TXT";
        File testFileWithBigLetters = new File(testStringWithBigLetters);

        try {
            testFileWithBigLetters.createNewFile();
            assertEquals("txt", FileUtil.getFileExtension(testFileWithBigLetters));
            assertEquals("txt", FileUtil.getFileExtension(testStringWithBigLetters));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileWithBigLetters.delete();
        }
    }

    @Test
    void getStringFileExtensionWithNoExtension() {
        String testFileWithoutExtension = "FileWithoutExtension";

        assertThrows(IllegalArgumentException.class, () -> FileUtil.getFileExtension(testFileWithoutExtension));
    }
}