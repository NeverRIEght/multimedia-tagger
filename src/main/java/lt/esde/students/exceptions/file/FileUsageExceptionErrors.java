package lt.esde.students.exceptions.file;

public enum FileUsageExceptionErrors {
    READ_ERROR("Read error"),
    WRITE_ERROR("Write error"),;

    private final String error;
    FileUsageExceptionErrors(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
