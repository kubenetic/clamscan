package eu.kubenetic.exceptions;

public class ScanException extends RuntimeException {

    public ScanException() {
    }

    public ScanException(String message) {
        super(message);
    }

    public ScanException(String message, Throwable cause) {
        super(message, cause);
    }
}
