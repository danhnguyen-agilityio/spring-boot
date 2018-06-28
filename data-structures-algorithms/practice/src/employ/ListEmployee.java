package employ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class ListEmployee use ArrayList structure to implement SalaryEmployee
 * @author Danh Nguyen
 */
public class ListEmployee implements SalaryEmployee {
    private List<Employee> list = new ArrayList<>();

    @Override
    public void initial() {
        Employee employee;

        employee = new Employee("Danh", "Nguyen", "Male", "07/11/1993", 1, 1000000);
        create(employee);

        employee = new Employee("Tu", "Nguyen", "Male", "04/03/1980", 2, 2000000);
        create(employee);

        employee = new Employee("Tai", "Vo Van", "Male", "11/11/1973", 3, 5000000);
        create(employee);

        employee = new Employee("Thuong", "Le", "Female", "05/07/1994", 5, 7000000);
        create(employee);

        employee = new Employee("Banh", "Hung", "Female", "05/07/1994", 6, 7000000);
        create(employee);
    }

    @Override
    public void create(Employee employee) {
        list.add(employee);
    }

    @Override
    public Employee get(int index) {
        return list.get(index);
    }

    @Override
    public Employee get(long id) {
        for (Employee employee : list) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public Employee get(String fullName) {
        for (Employee employee : list) {
            if (employee.getFullName().equalsIgnoreCase(fullName)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public void show() {
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    @Override
    public Employee remove(long id) {
        Iterator<Employee> itr = list.iterator();
        while (itr.hasNext()) {
            Employee employee = itr.next();
            if (employee.id == id) {
                itr.remove();
                return employee;
            }
        }
        return null;
    }

    @Override
    public Employee remove(int index) {
        return list.remove(index);
    }

    @Override
    public String editFirstName(long id, String firstName) {
        Employee employee = get(id);
        if (employee == null) {
            return null;
        }
        return employee.setFirstName(firstName);
    }

    @Override
    public void sortByFirstNameAndLastName() {
        list.sort(new FirstLastNameComparator());
    }

    @Override
    public void sortBySalary() {
        list.sort(new SalaryComparator());
    }

    public static void main(String[] args) {
        ListEmployee listEmployee = new ListEmployee();

        System.out.println("--------------------------");
        listEmployee.initial();
        System.out.println("Initial employees: ");
        listEmployee.show();

        System.out.println("--------------------------");
        long id = listEmployee.get(0).getId();
        Employee employee = listEmployee.get(id);
        System.out.println("Employee with id = " + id);
        System.out.println(employee);

        System.out.println("--------------------------");
        String fullName = "Thuong Le";
        employee = listEmployee.get(fullName);
        System.out.println("Employee with fullname: " + fullName);
        System.out.println(employee);


        System.out.println("--------------------------");
        String oldFirstName = listEmployee.editFirstName(id, "NewName");
        System.out.println("Old first name of employee with id: " + id);
        System.out.println(oldFirstName);
        System.out.println("List after rename: ");
        listEmployee.show();

        System.out.println("--------------------------");
        employee = listEmployee.remove(id);
        System.out.println("Employee removed with id = " + id);
        System.out.println(employee);
        System.out.println("List after remove: ");
        listEmployee.show();

        System.out.println("--------------------------");
        listEmployee.sortByFirstNameAndLastName();
        System.out.println("List after sort by first name and last name: ");
        listEmployee.show();

        System.out.println("--------------------------");
        listEmployee.sortBySalary();
        System.out.println("List after sort by salary: ");
        listEmployee.show();
    }
}
