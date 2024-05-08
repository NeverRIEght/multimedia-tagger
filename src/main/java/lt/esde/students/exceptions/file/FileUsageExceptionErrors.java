package lt.esde.students.exceptions.file;

public enum FileUsageExceptionErrors implements MediaTaggerExceptionErrors {
    READ_ERROR("Read error"),
    WRITE_ERROR("Write error"),;

    private final String error;
    FileUsageExceptionErrors(String error) {
        this.error = error;
    }

    @Override
    public String getError() {
        return this.error;
    }
}
