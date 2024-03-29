package lt.esde.students.Entities;

import java.time.LocalDateTime;
import java.util.List;

public class ImageFile extends MultimediaFile {

    private String modifiedPath;

    private List<LocalDateTime> dates;

    private ExifMetadata metadata;

    public ImageFile(String name,
                     String originalPath,
                     LocalDateTime creationDateTime,
                     LocalDateTime modificationDateTime) {
        super(name, originalPath, creationDateTime, modificationDateTime);
    }


    public String getModifiedPath() {
        return modifiedPath;
    }

    public void setModifiedPath(String modifiedPath) {
        this.modifiedPath = modifiedPath;
    }

    public List<LocalDateTime> getDates() {
        return dates;
    }

    public void setDates(List<LocalDateTime> dates) {
        this.dates = dates;
    }

    public boolean addDate(LocalDateTime date) {
        if (date == null) {
            throw new NullPointerException("date is null");
        }

        if (!dates.contains(date)) {
            dates.add(date);
            return true;
        }

        return false;
    }

    public ExifMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ExifMetadata metadata) {
        this.metadata = metadata;
    }
}
