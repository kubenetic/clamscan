package eu.kubenetic.exceptions;

public class SizeLimitExceededException extends RuntimeException {
    public SizeLimitExceededException() {
    }

    public SizeLimitExceededException(String message) {
        super(message);
    }

    public SizeLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
