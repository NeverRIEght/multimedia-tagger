package lt.esde.students.exceptions;

public class MediaTaggerException extends Throwable{

    public MediaTaggerException(String message) {
        super(message);
    }

    private static String getMessage(String message, MediaTaggerExceptionErrors error) {
        StringBuilder sb = new StringBuilder();

        sb.append(error.getError());
        sb.append(": ");
        sb.append(message);
        return sb.toString();
    }
}
