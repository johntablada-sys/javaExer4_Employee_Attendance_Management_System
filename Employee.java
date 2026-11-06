/**
 * Represents an employee in the attendance system.
 */
public class Employee {
    private final String employeeId;
    private final String name;
    private final String department;

    /**
     * Creates a new employee.
     *
     * @param employeeId unique employee identifier
     * @param name employee full name
     * @param department employee department
     */
    public Employee(String employeeId, String name, String department) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
    }

    /**
     * Gets the employee ID.
     *
     * @return employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the employee name.
     *
     * @return employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the employee department.
     *
     * @return employee department
     */
    public String getDepartment() {
        return department;
    }
}
