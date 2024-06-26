package lt.esde.students.metadata.exif.scraper;

import lt.esde.students.metadata.exif.entities.ExifTag;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExifTagsScraper {
    private static final String EXIFTOOL_URL = "https://exiftool.org/TagNames/EXIF.html";

    public static List<ExifTag> scrapAllExistingExifTags() {
        List<ExifTag> allExifTagsList = new ArrayList<>();

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(EXIFTOOL_URL);

            List<HtmlElement> elements = page.getByXPath("//*");

            for (HtmlElement element : elements) {
                if (element instanceof HtmlTable table) {
                    for (final HtmlTableRow row : table.getRows()) {

                        List<HtmlTableCell> cells = row.getCells();
                        if (cells.size() != 5) continue;

                        String title = cells.getFirst().getAttribute("title");
                        if (title.trim().isEmpty()) continue;

                        title = title.substring(title.lastIndexOf("=") + 2).trim();
                        ExifTag tag = getExifTag(cells, title);
                        allExifTagsList.add(tag);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can not scrap exiftags");
        }

        return allExifTagsList;
    }

    private static ExifTag getExifTag(List<HtmlTableCell> cells, String title) {
        String tagName = cells.get(1).getTextContent();
        String writable = cells.get(2).getTextContent();
        String group = cells.get(3).getTextContent();
        String valuesNotes = cells.get(4).getTextContent();

        return new ExifTag(Integer.parseInt(title.trim()),
                tagName,
                writable,
                group,
                valuesNotes);
    }
}
