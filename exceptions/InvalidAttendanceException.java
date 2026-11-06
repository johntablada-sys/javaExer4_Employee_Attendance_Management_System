package exceptions;

/**
 * Thrown when an attendance operation violates the attendance rules.
 */
public class InvalidAttendanceException extends Exception {
    /**
     * Creates an exception with a descriptive message.
     *
     * @param message explanation of the invalid attendance operation
     */
    public InvalidAttendanceException(String message) {
        super(message);
    }
}
