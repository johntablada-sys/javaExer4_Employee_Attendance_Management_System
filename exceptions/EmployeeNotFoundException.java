package exceptions;

/**
 * Thrown when an employee lookup is performed for an ID that does not exist.
 */
public class EmployeeNotFoundException extends Exception {
    /**
     * Creates an exception with a descriptive message.
     *
     * @param message explanation that the employee ID was not found
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
