package lt.esde.students.exceptions;

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
     * @param message
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
