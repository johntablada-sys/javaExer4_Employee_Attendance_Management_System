import exceptions.EmployeeAlreadyExistsException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidAttendanceException;
import java.time.format.DateTimeFormatter;

/**
 * Runs the test cases for the employee attendance management system.
 */
public class Main {
    /**
     * Starts the attendance system test cases.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * Runs each attendance system test case.
     */
    private static void runAllTests() {
        System.out.println("Test Cases\n");

        testRegisterEmployees();
        testPreventDuplicateRegistration();
        testFindExistingEmployee();
        testFindUnregisteredEmployee();
        testRecordTimeIn();
        testPreventMultipleActiveRecords();
        testPreventTimeOutWithoutTimeIn();
        testRecordTimeOut();
        testAllowNewAttendanceAfterCompletion();
        testFilterCompletedAttendanceRecords();

        System.out.println("\nAll tests completed");
    }

    /**
     * Displays a test result or exception message.
     *
     * @param details result text to display
     */
    private static void printResult(String details) {
        System.out.println(details);
    }

    /**
     * Tests registration of two employees.
     */
    private static void testRegisterEmployees() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("Test 1");
        System.out.println("Input: registerEmployee(E001, Riri Williams, IT); registerEmployee(E002, Nick Fury, HR)");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.registerEmployee(new Employee("E002", "Nick Fury", "HR"));
            printResult("Employee records created successfully.");
        } catch (EmployeeAlreadyExistsException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests that registering a duplicate employee ID is rejected.
     */
    private static void testPreventDuplicateRegistration() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 2");
        System.out.println("Input: registerEmployee(E001, Thor Son of Odin, Electrical) after E001 is already registered");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.registerEmployee(new Employee("E001", "Thor Son of Odin", "Electrical"));
            printResult("Duplicate registration was allowed.");
        } catch (EmployeeAlreadyExistsException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests finding and displaying a registered employee.
     */
    private static void testFindExistingEmployee() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 3");
        System.out.println("Input: findEmployeeById(E001)");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            Employee employee = manager.findEmployeeById("E001");
            printResult("Found employee: " + employee.getName() + " | Department: " + employee.getDepartment());
        } catch (EmployeeAlreadyExistsException | EmployeeNotFoundException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests the not-found exception for an unregistered employee ID.
     */
    private static void testFindUnregisteredEmployee() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 4");
        System.out.println("Input: findEmployeeById(E999)");

        try {
            manager.findEmployeeById("E999");
            printResult("No exception was thrown.");
        } catch (EmployeeNotFoundException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests recording an employee's time-in.
     */
    private static void testRecordTimeIn() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 5");
        System.out.println("Input: timeIn(E001)");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.timeIn("E001");
            printResult("timeIn recorded successfully. timeOut = null");
        } catch (EmployeeAlreadyExistsException | EmployeeNotFoundException | InvalidAttendanceException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests that an employee cannot have multiple active attendance records.
     */
    private static void testPreventMultipleActiveRecords() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 6");
        System.out.println("Input: timeIn(E001) twice without recording a time-out first");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.timeIn("E001");
            manager.timeIn("E001");
            printResult("Second timeIn was allowed.");
        } catch (EmployeeAlreadyExistsException e) {
            printResult(e.getMessage());
        } catch (InvalidAttendanceException | EmployeeNotFoundException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests that time-out is rejected when there is no active attendance record.
     */
    private static void testPreventTimeOutWithoutTimeIn() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 7");
        System.out.println("Input: timeOut(E002) where E002 has no active attendance record");

        try {
            manager.registerEmployee(new Employee("E002", "Nick Fury", "HR"));
            manager.timeOut("E002");
            printResult("timeOut was allowed without timeIn.");
        } catch (EmployeeAlreadyExistsException e) {
            printResult(e.getMessage());
        } catch (InvalidAttendanceException | EmployeeNotFoundException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests recording an employee's time-out.
     */
    private static void testRecordTimeOut() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 8");
        System.out.println("Input: timeIn(E001); timeOut(E001)");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.timeIn("E001");
            manager.timeOut("E001");
            printResult("timeOut recorded successfully.");
        } catch (EmployeeAlreadyExistsException | EmployeeNotFoundException | InvalidAttendanceException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests starting a new attendance record after completing a previous one.
     */
    private static void testAllowNewAttendanceAfterCompletion() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 9");
        System.out.println("Input: timeIn(E001); timeOut(E001); timeIn(E001) again");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.timeIn("E001");
            manager.timeOut("E001");
            manager.timeIn("E001");
            printResult("A new active attendance record is allowed after completion.");
        } catch (EmployeeAlreadyExistsException | EmployeeNotFoundException | InvalidAttendanceException e) {
            printResult(e.getMessage());
        }
    }

    /**
     * Tests retrieval and display of completed attendance records.
     */
    private static void testFilterCompletedAttendanceRecords() {
        AttendanceManager manager = new AttendanceManager();

        System.out.println("\nTest 10");
        System.out.println("Input: timeIn(E001); timeOut(E001); timeIn(E001); getCompletedAttendanceRecords(E001)");

        try {
            manager.registerEmployee(new Employee("E001", "Riri Williams", "IT"));
            manager.timeIn("E001");
            manager.timeOut("E001");
            manager.timeIn("E001");

            java.util.List<AttendanceRecord> completed = manager.getCompletedAttendanceRecords("E001");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            printResult("Completed records returned: " + completed.size());
            for (AttendanceRecord record : completed) {
                System.out.println("Time-in: " + record.getTimeIn().format(formatter)
                        + " | Time-out: " + record.getTimeOut().format(formatter));
            }
        } catch (EmployeeAlreadyExistsException | EmployeeNotFoundException | InvalidAttendanceException e) {
            printResult(e.getMessage());
        }
    }
}
