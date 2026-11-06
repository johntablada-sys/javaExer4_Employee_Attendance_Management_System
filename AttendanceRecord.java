import java.time.LocalDateTime;

/**
 * Represents an employee attendance transaction.
 */
public class AttendanceRecord {
    private final String employeeId;
    private final LocalDateTime timeIn;
    private LocalDateTime timeOut;

    /**
     * Creates an attendance record with the employee ID and time-in value.
     *
     * @param employeeId unique employee identifier
     * @param timeIn the employee's time-in value
     */
    public AttendanceRecord(String employeeId, LocalDateTime timeIn) {
        this.employeeId = employeeId;
        this.timeIn = timeIn;
    }

    /**
     * Gets the employee ID associated with this record.
     *
     * @return employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the time-in value.
     *
     * @return time-in value
     */
    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    /**
     * Gets the time-out value.
     *
     * @return time-out value
     */
    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    /**
     * Sets the time-out value.
     *
     * @param timeOut the employee's time-out value
     */
    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }
}
