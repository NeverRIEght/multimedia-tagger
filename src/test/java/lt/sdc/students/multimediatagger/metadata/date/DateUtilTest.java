package lt.sdc.students.multimediatagger.metadata.date;

import lt.sdc.students.multimediatagger.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {
    @Test
    void getAllMetadataDatesNullTest() {
        assertThrows(NullPointerException.class, () ->
                DateUtil.getAllMetadataDates(null));
    }

    @Test
    void getAllMetadataDatesNonExistentFileTest() {
        assertThrows(NullPointerException.class, () ->
                DateUtil.getAllMetadataDates(new File("non-existent-file.jpg")));
    }

    @Test
    void getAllMetadataDatesInvalidExtensionTest() {
        File testFile = new File(Main.TEST_IMG_FOLDER_PATH + File.separator + "eifel.png");
        assertThrows(NullPointerException.class, () ->
                DateUtil.getAllMetadataDates(testFile));
    }

    @Test
    void getAllMetadataDatesNoExtensionTest() {
        File testFile = new File(Main.TEST_IMG_FOLDER_PATH + File.separator + "eifel");
        assertThrows(NullPointerException.class, () ->
                DateUtil.getAllMetadataDates(testFile));
    }

    @Test
    void getOldestDateNullTest() {
        assertThrows(NullPointerException.class, () ->
                DateUtil.getOldestDate(null));
    }

    @Test
    void getOldestDateEmptyTest() {
        List<LocalDateTime> testList = new ArrayList<>();
        assertThrows(RuntimeException.class, () ->
                DateUtil.getOldestDate(testList));
    }

    @Test
    void getOldestDateSingleItemTest() {
        List<LocalDateTime> testList = new ArrayList<>(1);
        LocalDateTime sampleDateTime = LocalDateTime.parse("2010-10-10T10:00:01");
        testList.add(sampleDateTime);
        Assertions.assertEquals(sampleDateTime, DateUtil.getOldestDate(testList));
    }

    @Test
    void getOldestDateDuplicateItemsTest() {
        List<LocalDateTime> testList = new ArrayList<>(1);
        LocalDateTime sampleDateTime = LocalDateTime.parse("2010-10-10T10:00:01");
        testList.add(sampleDateTime);
        testList.add(sampleDateTime);
        Assertions.assertEquals(sampleDateTime, DateUtil.getOldestDate(testList));
    }
}