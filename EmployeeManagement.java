import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple console application for managing employee details.
 * Employee records are kept in memory and are cleared when the program exits.
 */
public class EmployeeManagement {
    private static final Map<Integer, Employee> employees = new LinkedHashMap<>();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                System.out.println("\n=== Employee Management ===");
                System.out.println("1. Add employee");
                System.out.println("2. Display employees");
                System.out.println("3. Search employee by ID");
                System.out.println("4. Update employee details");
                System.out.println("5. Delete employee");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        addEmployee(scanner);
                        break;
                    case "2":
                        displayEmployees();
                        break;
                    case "3":
                        searchEmployee(scanner);
                        break;
                    case "4":
                        updateEmployee(scanner);
                        break;
                    case "5":
                        deleteEmployee(scanner);
                        break;
                    case "6":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number from 1 to 6.");
                }
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter employee ID: ");
        if (id == null) {
            return;
        }
        if (employees.containsKey(id)) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readRequiredText(scanner, "Enter name: ");
        String department = readRequiredText(scanner, "Enter department: ");
        Double salary = readSalary(scanner, "Enter salary: ");
        if (salary == null) {
            return;
        }

        employees.put(id, new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        System.out.printf("%-12s %-24s %-20s %12s%n", "Employee ID", "Name", "Department", "Salary");
        System.out.println("-----------------------------------------------------------------------");
        for (Employee employee : employees.values()) {
            System.out.printf("%-12d %-24s %-20s %12.2f%n",
                    employee.id, employee.name, employee.department, employee.salary);
        }
    }

    private static void searchEmployee(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter employee ID to search: ");
        if (id == null) {
            return;
        }

        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            displayEmployee(employee);
        }
    }

    private static void updateEmployee(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter employee ID to update: ");
        if (id == null) {
            return;
        }

        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
            return;
        }

        System.out.println("Enter the new details for employee ID " + id + ":");
        String name = readRequiredText(scanner, "Enter name: ");
        String department = readRequiredText(scanner, "Enter department: ");
        Double salary = readSalary(scanner, "Enter salary: ");
        if (salary == null) {
            System.out.println("Update cancelled; employee details were not changed.");
            return;
        }

        employees.put(id, new Employee(id, name, department, salary));
        System.out.println("Employee details updated successfully.");
    }

    private static void deleteEmployee(Scanner scanner) {
        Integer id = readInteger(scanner, "Enter employee ID to delete: ");
        if (id == null) {
            return;
        }

        Employee removed = employees.remove(id);
        if (removed == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println("Employee deleted successfully.");
        }
    }

    private static void displayEmployee(Employee employee) {
        System.out.println("Employee details:");
        System.out.println("ID: " + employee.id);
        System.out.println("Name: " + employee.name);
        System.out.println("Department: " + employee.department);
        System.out.printf("Salary: %.2f%n", employee.salary);
    }

    private static Integer readInteger(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a valid whole number.");
            return null;
        }
    }

    private static String readRequiredText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static Double readSalary(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            double salary = Double.parseDouble(scanner.nextLine().trim());
            if (Double.isFinite(salary) && salary >= 0) {
                return salary;
            }
        } catch (NumberFormatException exception) {
            // Show the same friendly message for invalid numeric input.
        }
        System.out.println("Please enter a valid non-negative salary.");
        return null;
    }

    private static final class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
    }
}
