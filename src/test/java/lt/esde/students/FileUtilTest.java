package lt.esde.students;

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

        assertThrows(NullPointerException.class, () -> {
            FileUtil.getFileExtension(nullFile);
        });
    }

    @Test
    void getFileExtensionWithNoExtension() {
        File testFileWithoutExtension = new File("FileWithoutExtension");

        try {
            testFileWithoutExtension.createNewFile();
            assertThrows(IllegalArgumentException.class, () -> {
                FileUtil.getFileExtension(testFileWithoutExtension);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileWithoutExtension.delete();
        }
    }

    @Test
    void getFileExtensionTxt() {
        File testFileTxt = new File("Example.txt");

        try {
            testFileTxt.createNewFile();
            assertEquals("txt", FileUtil.getFileExtension(testFileTxt));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileTxt.delete();
        }
    }

    @Test
    void getFileExtensionExe() {
        File testFileExe = new File("ExeFile.exe");

        try {
            testFileExe.createNewFile();
            assertEquals("exe", FileUtil.getFileExtension(testFileExe));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileExe.delete();
        }
    }

    @Test
    void getFileExtensionWithSpaces() {
        File testFileWithSpace = new File("File with spaces.txt");

        try {
            testFileWithSpace.createNewFile();
            assertEquals("txt", FileUtil.getFileExtension(testFileWithSpace));
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
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                testFileWithPoints.delete();
            }
        }
    }

    @Test
    void getFileExtensionWithBigLetters() {
        File testFileWithBigLetters = new File("FileWithBigLetters.TXT");

        try {
            testFileWithBigLetters.createNewFile();
            assertEquals("txt", FileUtil.getFileExtension(testFileWithBigLetters));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            testFileWithBigLetters.delete();
        }
    }
}