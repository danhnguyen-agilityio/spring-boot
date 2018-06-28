package employ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/*
 * Class ArrayEmployee manipulate employee by using array
 */
public class ArrayEmployee {
    int size = 0;
    Employee[] employees = new Employee[100];

    /*
     * Initial employee when run app
     */
    public void initialEmployee() {
        Employee employee = null;

        employee = new Employee("Danh", "Nguyen", "Male", "07/11/1993", 1, 1000000);
        employees[size++] = employee;

        employee = new Employee("Tu", "Nguyen", "Male", "04/03/1980", 2, 2000000);
        employees[size++] = employee;

        employee = new Employee("Tai", "Vo Van", "Male", "11/11/1973", 3, 5000000);
        employees[size++] = employee;

        employee = new Employee("Thuong", "Le", "Female", "05/07/1994", 5, 7000000);
        employees[size++] = employee;

        employee = new Employee("Banh", "Hung", "Female", "05/07/1994", 6, 7000000);
        employees[size++] = employee;
    }

    /**
     * Create employee
     * @param employee Employee is added to array
     */
    public void create(Employee employee) {
        employees[size++] = employee;
    }

    /**
     * Create employee
     */
    public void createEmployee() {
        Scanner scanner;

        System.out.println("Please enter first name: ");
        scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();

        System.out.println("Please enter last name: ");
        scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();

        System.out.println("Please enter sex: ");
        scanner = new Scanner(System.in);
        String sex = scanner.nextLine();

        System.out.println("Please enter day of birth: ");
        scanner = new Scanner(System.in);
        String dayOfBirth = scanner.nextLine();

        System.out.println("Please enter level: ");
        scanner = new Scanner(System.in);
        int level = scanner.nextInt();

        System.out.println("Please enter basic salary: ");
        scanner = new Scanner(System.in);
        double basicSalary = scanner.nextDouble();

        // Create employee instance
        Employee employee = new Employee(firstName, lastName, sex, dayOfBirth, level, basicSalary);

        // Assign employee to array
        employees[size++] = employee;
    }

    /**
     * Get employee by index
     * @param index index of array
     * @return employee at specific index
     */
    public Employee getEmployee(int index) {
        return employees[index];
    }

     /**
     * Get employee by id
     * @param id id of employee
     * @return employee have expect id or null if id not exist
     */
    public Employee getEmployee(long id) {
        Employee employee;
        for (int i = 0; i < size ; i++) {
            employee = employees[i];
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Get employee by full name
     * @param fullName full name of employee
     * @return Employee with specific full name
     */
    public Employee getEmployee(String fullName) {
        Employee employee;
        for (int i = 0; i < size ; i++) {
            employee = employees[i];
            if ((employee.getFullName()).equalsIgnoreCase(fullName)) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Get employees have greater than 30 years old
     * @param yearsOld years old
     * @return Employees greater than 30 years old
     */
    public ArrayEmployee greaterYearsOld(int yearsOld) throws ParseException {
        Employee employee;
        int age;
        Date date;
        ArrayEmployee result = new ArrayEmployee();

        for (int i = 0; i < size ; i++) {
            employee = employees[i];
            if (employee.getAge() >= yearsOld) {
                result.create(employee);
            }
        }
        return result;
    }

    /**
     * Show info employees
     */
    public void showEmployees() {
        Employee employee = null;
        for (int i = 0; i < size; i++) {
            employee = employees[i];
            System.out.println(employee);
        }
    }

    /**
     * Remove employee by id
     * @param id id of employ need remove
     * @return Removed employee
     */
    public Employee remove(long id) {
        Employee employee;
        for (int i = 0; i < size ; i++) {
            employee = employees[i];
            if (employee.getId() == id) {
                remove(i);
                return employee;
            }
        }
        return null;
    }

    /**
     * Remove employee at specific index
     * @param index index of employee need removed
     * @return Removed employee
     */
    public Employee remove(int index) {
        if (index < 0 && index >= size) {
            return null;
        }
        Employee employee = employees[index];

        // shifting element after index to left
        for (int i = index; i < size - 1; i++) {
            employees[i] = employees[i + 1];
        }
        size--;
        return employee;
    }

    /**
     * Edit first name of employee
     * @param id id of employee need change
     * @param firstName new first name
     * @return old first name
     */
    public String editFirstName(long id, String firstName) {
        String oldFirstName;
        Employee employee = getEmployee(id);
        if (employee == null) {
            oldFirstName = null;
        } else {
            oldFirstName = employee.getFirstName();
            employee.setFirstName(firstName);
        }
        return oldFirstName;
    }

    /**
     * Sort employee by first name and last name
     */
    public void sortByFirstNameAndLastName() {
        Employee[] temp = new Employee[size];
        for (int i = 0; i < size; i++) {
            temp[i] = employees[i];
        }
        Arrays.sort(temp, new FirstLastNameComparator());
        for (int i = 0; i < size; i++) {
            employees[i] = temp[i];
        }
    }

    /**
     * Sort employee by salary
     */
    public void sortBySalary() {
        Arrays.sort(employees, new SalaryComparator());
    }

    public static void main(String[] args) throws Exception {
        Employee employee;
        long id;

        ArrayEmployee arrayEmployee = new ArrayEmployee();
        arrayEmployee.initialEmployee();

//        arrayEmployee.createEmployee();
        System.out.println("-------------------------------");
        System.out.println("Info employees after add new: ");
        arrayEmployee.showEmployees();

        employee = arrayEmployee.getEmployee(1000l);
        System.out.println("-------------------------------");
        System.out.println("Employee correspond with id 1000: ");
        System.out.println(employee);

        id = arrayEmployee.getEmployee(0).getId();
        employee = arrayEmployee.getEmployee(id);
        System.out.println("-------------------------------");
        System.out.println("Employee correspond with id " + id);
        System.out.println(employee);

        employee = arrayEmployee.remove(id);
        System.out.println("-------------------------------");
        System.out.println("Removed employee correspond with id " + id);
        System.out.println(employee);

        System.out.println("-------------------------------");
        System.out.println("Info employees");
        arrayEmployee.showEmployees();

        id = arrayEmployee.getEmployee(0).getId();
        System.out.println("-------------------------------");
        String oldFirstName = arrayEmployee.editFirstName(id, "NewName");
        System.out.println("Old first name: " + oldFirstName);

        System.out.println("-------------------------------");
        System.out.println("Info employees");
        arrayEmployee.showEmployees();

        System.out.println("---------------------------------");
        String fullName = "tai vo van";
        employee = arrayEmployee.getEmployee(fullName);
        System.out.println("Employee correspond with full name: " + fullName);
        System.out.println(employee);

        System.out.println("---------------------------------");
        ArrayEmployee employeesGreaterYearOld = arrayEmployee.greaterYearsOld(30);
        System.out.println("Employee correspond with age >= 30: ");
        employeesGreaterYearOld.showEmployees();

        System.out.println("---------------------------------");
        arrayEmployee.sortByFirstNameAndLastName();
        System.out.println("Employees after sort: ");
        arrayEmployee.showEmployees();

        System.out.println("---------------------------------");
        arrayEmployee.sortByFirstNameAndLastName();
        System.out.println("Employees after sort: ");
        arrayEmployee.showEmployees();

        System.out.println("---------------------------------");
        arrayEmployee.sortBySalary();
        System.out.println("Employees after sort by salary: ");
        arrayEmployee.showEmployees();
    }
}
