import exceptions.EmployeeAlreadyExistsException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidAttendanceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages employee registration and attendance records.
 */
public class AttendanceManager {
    private final List<Employee> employees;
    private final List<AttendanceRecord> attendanceRecords;

    /**
     * Creates a new attendance manager and initializes its internal collections.
     */
    public AttendanceManager() {
        this.employees = new ArrayList<>();
        this.attendanceRecords = new ArrayList<>();
    }

    /**
     * Registers a new employee.
     *
     * @param employee employee to register
     * @throws EmployeeAlreadyExistsException when the employee ID is already in use
     */
    public void registerEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        boolean exists = employees.stream()
                .anyMatch(existingEmployee -> existingEmployee.getEmployeeId().equals(employee.getEmployeeId()));

        if (exists) {
            throw new EmployeeAlreadyExistsException("Employee with ID " + employee.getEmployeeId() + " already exists.");
        }

        employees.add(employee);
    }

    /**
     * Finds an employee by their ID.
     *
     * @param employeeId employee ID to search for
     * @return matching employee
     * @throws EmployeeNotFoundException when the employee does not exist
     */
    public Employee findEmployeeById(String employeeId) throws EmployeeNotFoundException {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + employeeId + " was not found."));
    }

    /**
     * Records the employee's time-in.
     *
     * @param employeeId employee ID to log in
     * @throws EmployeeNotFoundException when the employee does not exist
     * @throws InvalidAttendanceException when the employee is already clocked in
     */
    public void timeIn(String employeeId)
            throws EmployeeNotFoundException, InvalidAttendanceException {
        findEmployeeById(employeeId);

        boolean hasOpenAttendance = attendanceRecords.stream()
                .anyMatch(record -> record.getEmployeeId().equals(employeeId) && record.getTimeOut() == null);

        if (hasOpenAttendance) {
            throw new InvalidAttendanceException("Employee with ID " + employeeId + " is already timed in.");
        }

        attendanceRecords.add(new AttendanceRecord(employeeId, LocalDateTime.now()));
    }

    /**
     * Records the employee's time-out.
     *
     * @param employeeId employee ID to log out
     * @throws EmployeeNotFoundException when the employee does not exist
     * @throws InvalidAttendanceException when the employee is not currently timed in
     */
    public void timeOut(String employeeId)
            throws EmployeeNotFoundException, InvalidAttendanceException {
        findEmployeeById(employeeId);

        AttendanceRecord openRecord = attendanceRecords.stream()
                .filter(record -> record.getEmployeeId().equals(employeeId) && record.getTimeOut() == null)
                .reduce((first, second) -> second)
                .orElse(null);

        if (openRecord == null) {
            throw new InvalidAttendanceException("Employee with ID " + employeeId + " has not timed in yet or has already timed out.");
        }

        openRecord.setTimeOut(LocalDateTime.now());
    }

    /**
     * Returns all completed attendance records for the specified employee.
     *
     * @param employeeId employee ID to retrieve records for
     * @return list of completed attendance records
     * @throws EmployeeNotFoundException when the employee does not exist
     */
    public List<AttendanceRecord> getCompletedAttendanceRecords(String employeeId) throws EmployeeNotFoundException {
        findEmployeeById(employeeId);

        return attendanceRecords.stream()
                .filter(record -> record.getEmployeeId().equals(employeeId) && record.getTimeOut() != null)
                .collect(Collectors.toList());
    }
}
