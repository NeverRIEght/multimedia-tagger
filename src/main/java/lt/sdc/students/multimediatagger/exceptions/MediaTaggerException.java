package lt.sdc.students.multimediatagger.exceptions;

/**
 * Main exception for hierarchy custom exceptions
 */
public class MediaTaggerException extends RuntimeException{

    /**
     * Message for exception
     */
    private final String message;

    /**
     * Constructor
     * @param message Message for exception
     */
    public MediaTaggerException(String message) {
        this.message = message;
    }

    /**
     * Get message from exception
     * @return Custom message
     */
    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(": ");
        sb.append(this.message);

        return sb.toString();
    }
}
