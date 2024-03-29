package lt.esde.students;

import java.io.File;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {


    @org.junit.jupiter.api.Test
    void convertToTiff() {
    }

    @org.junit.jupiter.api.Test
    void getCreationDateTime() {


    }



    @org.junit.jupiter.api.Test
    void getFileExtensionTxt() {
        File testTxtFile= new File("Example.txt");
        assertEquals(".txt",FileUtil.getFileExtension(testTxtFile));

    }

    @org.junit.jupiter.api.Test
    void getFileExtensionExe() {
        File testExeFile= new File("ExeFile.exe");

        File testNullFile= null;
        assertEquals(".exe",FileUtil.getFileExtension(testExeFile));

    }
    @org.junit.jupiter.api.Test
    void getFileWithoutException() {
        File testWithoutExceptionFile= new File("FileWithoutException");

        assertEquals("Incorrect File",FileUtil.getFileExtension(testWithoutExceptionFile));

        };
    @org.junit.jupiter.api.Test
        void getFileExtensionWitchSpace() {
            File testFileWitchSpace= new File("File witch space.txt");

            assertEquals(".txt",FileUtil.getFileExtension(testFileWitchSpace));


        }
    @org.junit.jupiter.api.Test
        void getFileExtensionWitchDoublePoint() {
            File testFileWitchDoublePoint= new File("FileWitchDoublePoint..txt");

            assertEquals(".txt",FileUtil.getFileExtension(testFileWitchDoublePoint));


        }

    @org.junit.jupiter.api.Test
    void getFileExtensionWitchNull() {
        File nullFile= null;

        assertThrows(NullPointerException.class, () -> {
            FileUtil.getFileExtension(nullFile);
        });


    }

    }
