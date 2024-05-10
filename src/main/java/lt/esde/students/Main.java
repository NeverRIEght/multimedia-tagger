package lt.esde.students;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    /**
     * Name of the folder with test images
     */
    public static final String TEST_FOLDER_NAME = "testimg";
    /**
     * Path to the folder with test images
     */
    public static final String TEST_IMG_FOLDER_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + TEST_FOLDER_NAME;

    /**
     * Path to the test jpg file, which has exif field with creation date
     */
    public static final String TEST_IMG_WITH_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + TEST_FOLDER_NAME + File.separator + "eifel.jpg";

    /**
     * Path to the test jpg file, which has no exif field with creation date
     */
    public static final String TEST_IMG_WITHOUT_METADATA_PATH = Paths.get("")
            .toAbsolutePath() + File.separator + TEST_FOLDER_NAME + File.separator + "maricat.jpg";

    public static void main(String[] args) {
        List<ExifTag> exifTags = new ArrayList<>();
        try (final WebClient webClient = new WebClient()) {
            // Получаем страницу
            final HtmlPage page = webClient.getPage("https://exiftool.org/TagNames/EXIF.html");

            // Получаем все элементы на странице
            List<HtmlElement> elements = page.getByXPath("//*");

            // Проходимся по каждому элементу
            for (HtmlElement element : elements) {
                // Проверяем, является ли элемент таблицей
                if (element instanceof HtmlTable) {
                    HtmlTable table = (HtmlTable) element;

                    // Проходимся по каждой строке в таблице
                    for (final HtmlTableRow row : table.getRows()) {
                        // Получаем ячейки в строке
                        List<HtmlTableCell> cells = row.getCells();

                        // Проверяем, что у нас достаточно ячеек
                        if (cells.size() == 5) {
                            // Получаем значения из нужных ячеек
                            String title = cells.getFirst().getAttribute("title");
                            if (!title.trim().isEmpty()) {
                                title = title.substring(title.lastIndexOf("=") + 2, title.length());
                            }
                            String TagTypeHex = cells.get(0).getTextContent();
                            String tagName = cells.get(1).getTextContent();
                            String writable = cells.get(2).getTextContent();
                            String group = cells.get(3).getTextContent();
                            String valuesNotes = cells.get(4).getTextContent();

                            // Выводим значения
//                            System.out.println("title: " + title);
//                            System.out.println("TagTypeHex: " + TagTypeHex);
//                            System.out.println("tagName: " + tagName);
//                            System.out.println("writable: " + writable);
//                            System.out.println("group: " + group);
//                            System.out.println("valuesNotes: " + valuesNotes);

                            //Записываем в объект
                            if (!title.trim().isEmpty()) {
                                ExifTag tag = new ExifTag(Integer.parseInt(title.trim()), tagName, writable, group, valuesNotes);
                                exifTags.add(tag);
                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(exifTags.getFirst());

        //ExifReader.readExifTags(TEST_IMG_PHOTOS_LIST.getFirst());

//        for(File f : TEST_IMG_PHOTOS_LIST) {
//            Map<String, String> tagsMap = ExifReader.readExifTags(f);
//            System.out.println("--------------------------------------");
//            System.out.println("--------------------------------------");
//            System.out.println(f.getAbsolutePath());
//            System.out.println("--------------------------------------");
//            System.out.println("--------------------------------------");
//
//            int maximumLength = -1;
//
//            for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
//                int currentLength = entry.getKey().length();
//                if (currentLength > maximumLength) {
//                    maximumLength = currentLength;
//                }
//            }
//
//            for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
//                int currentLength = entry.getKey().length();
//                StringBuilder sb = new StringBuilder();
//                sb.append(entry.getKey());
//                sb.append((" ").repeat(maximumLength - currentLength));
//                sb.append(" -> ");
//                sb.append(entry.getValue());
//                System.out.println(sb);
//            }
//        }
    }
}