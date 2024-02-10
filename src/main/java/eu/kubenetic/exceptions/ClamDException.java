package eu.kubenetic.exceptions;

public class ClamDException extends RuntimeException {

    public ClamDException() {
    }

    public ClamDException(String message) {
        super(message);
    }

    public ClamDException(String message, Throwable cause) {
        super(message, cause);
    }
}
