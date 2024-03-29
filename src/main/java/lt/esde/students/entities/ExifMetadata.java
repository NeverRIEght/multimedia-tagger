package lt.esde.students.Entities;

public class ExifMetadata {
    private int timeZoneOffset;

    private String imageHistory;

    private int exifImageWidth;

    private int exifImageHeight;

    private String software;

    private String imageDescription;

    public ExifMetadata(int timeZoneOffset, String imageHistory, int exifImageWidth, int exifImageHeight, String software, String imageDescription) {
        this.timeZoneOffset = timeZoneOffset;
        this.imageHistory = imageHistory;
        this.exifImageWidth = exifImageWidth;
        this.exifImageHeight = exifImageHeight;
        this.software = software;
        this.imageDescription = imageDescription;
    }

    public int getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(int timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public String getImageHistory() {
        return imageHistory;
    }

    public void setImageHistory(String imageHistory) {
        this.imageHistory = imageHistory;
    }

    public int getExifImageWidth() {
        return exifImageWidth;
    }

    public void setExifImageWidth(int exifImageWidth) {
        this.exifImageWidth = exifImageWidth;
    }

    public int getExifImageHeight() {
        return exifImageHeight;
    }

    public void setExifImageHeight(int exifImageHeight) {
        this.exifImageHeight = exifImageHeight;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
}
