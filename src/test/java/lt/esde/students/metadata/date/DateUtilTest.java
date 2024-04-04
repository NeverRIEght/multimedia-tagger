package lt.esde.students.metadata.date;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lt.esde.students.Main.TEST_IMG_FOLDER_PATH;
import static lt.esde.students.metadata.date.DateUtil.getAllMetadataDates;
import static lt.esde.students.metadata.date.DateUtil.getOldestDate;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {
    @Test
    void getAllMetadataDatesNullTest() {
        assertThrows(NullPointerException.class, () ->
                getAllMetadataDates(null));
    }

    @Test
    void getAllMetadataDatesNonExistentFileTest() {
        assertThrows(NullPointerException.class, () ->
                getAllMetadataDates(new File("non-existent-file.jpg")));
    }

    @Test
    void getAllMetadataDatesInvalidExtensionTest() {
        File testFile = new File(TEST_IMG_FOLDER_PATH + File.separator + "eifel.png");
        assertThrows(NullPointerException.class, () ->
                getAllMetadataDates(testFile));
    }

    @Test
    void getAllMetadataDatesNoExtensionTest() {
        File testFile = new File(TEST_IMG_FOLDER_PATH + File.separator + "eifel");
        assertThrows(NullPointerException.class, () ->
                getAllMetadataDates(testFile));
    }

    @Test
    void getOldestDateNullTest() {
        assertThrows(NullPointerException.class, () ->
                getOldestDate(null));
    }

    @Test
    void getOldestDateEmptyTest() {
        List<LocalDateTime> testList = new ArrayList<>();
        assertThrows(RuntimeException.class, () ->
                getOldestDate(testList));
    }

    @Test
    void getOldestDateSingleItemTest() {
        List<LocalDateTime> testList = new ArrayList<>(1);
        LocalDateTime sampleDateTime = LocalDateTime.parse("2010-10-10T10:00:01");
        testList.add(sampleDateTime);
        assertEquals(sampleDateTime, getOldestDate(testList));
    }

    @Test
    void getOldestDateDuplicateItemsTest() {
        List<LocalDateTime> testList = new ArrayList<>(1);
        LocalDateTime sampleDateTime = LocalDateTime.parse("2010-10-10T10:00:01");
        testList.add(sampleDateTime);
        testList.add(sampleDateTime);
        assertEquals(sampleDateTime, getOldestDate(testList));
    }
}