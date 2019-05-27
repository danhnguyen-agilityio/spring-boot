package employ;

import java.util.Comparator;

/**
 * Sort employees by salary
 */
public class SalaryComparator implements Comparator<Employee> {
    @Override
    /**
     * Used to sort employees by salary
     */
    public int compare(Employee o1, Employee o2) {
        if (o1 == null || o2 == null) return 0;
        return (int) (o1.getSalary() - o2.getSalary());
    }
}
