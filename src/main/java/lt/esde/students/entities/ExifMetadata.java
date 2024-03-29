package lt.esde.students.Entities;

public class ExifMetadata {

    private LocalDateTime dateTimeOriginal;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Integer timeZoneOffset;

    private String imageHistory;

    private int exifImageWidth;

    private int exifImageHeight;

    private String xpAuthor;

    private String software;

    private String imageDescription;

    public ExifMetadata() { }

    public ExifMetadata(LocalDateTime dateTimeOriginal,
                        LocalDateTime createDate,
                        LocalDateTime modifyDate,
                        Integer timeZoneOffset,
                        String imageHistory,
                        int exifImageWidth,
                        int exifImageHeight,
                        String xpAuthor,
                        String software,
                        String imageDescription) {
        this.dateTimeOriginal = dateTimeOriginal;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.timeZoneOffset = timeZoneOffset;
        this.imageHistory = imageHistory;
        this.exifImageWidth = exifImageWidth;
        this.exifImageHeight = exifImageHeight;
        this.xpAuthor = xpAuthor;
        this.software = software;
        this.imageDescription = imageDescription;
    }

    public LocalDateTime getDateTimeOriginal() {
        return dateTimeOriginal;
    }

    public void setDateTimeOriginal(LocalDateTime dateTimeOriginal) {
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(Integer timeZoneOffset) {
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

    public String getXpAuthor() {
        return xpAuthor;
    }

    public void setXpAuthor(String xpAuthor) {
        this.xpAuthor = xpAuthor;
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
