package employ;

import java.util.Comparator;
import java.util.EnumMap;

/**
 * Class FirstLastNameComparator is used to sort Employee
 */
public class FirstLastNameComparator implements Comparator<Employee> {
    /**
     * Used for sorting in ascending order of first name and last name
     * @param a Employee a
     * @param b Employee b
     * @return 1 if a > b, 0 if a = b and -1 if a < b
     */
    public int compare(Employee a, Employee b) {
        if (a == null || b == null) {
            return 0;
        }
        int result = a.getFirstName().compareToIgnoreCase(b.getFirstName());
        if (result == 0) {
            result = a.getLastName().compareToIgnoreCase(b.getLastName());
        }
        return result;
    }
}
