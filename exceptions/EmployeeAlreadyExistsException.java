package exceptions;

/**
 * Thrown when attempting to register an employee whose ID already exists.
 */
public class EmployeeAlreadyExistsException extends Exception {
    /**
     * Creates an exception with a descriptive message.
     *
     * @param message explanation of the duplicate employee ID
     */
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
