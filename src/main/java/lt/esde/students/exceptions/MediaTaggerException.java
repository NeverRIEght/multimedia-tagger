package lt.esde.students.exceptions;

public class MediaTaggerException extends RuntimeException{

    private final String message;

    public MediaTaggerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("MediaTaggerException: ");
        sb.append(this.message);

        return sb.toString();
    }
}
