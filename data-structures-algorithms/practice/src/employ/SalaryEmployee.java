package employ;

/**
 * Interface SalaryEmployee used to mange salary of employee
 */
public interface SalaryEmployee {

    /**
     * Initial employees
     */
    void initial();


    /**
     * Create employee
     */
    void create(Employee employee);

    /**
     * Get employee by index
     */
    Employee get(int index);

    /**
     * Get employee by id
     */
    Employee get(long id);

    /**
     * Get employee by full name
     */
    Employee get(String fullName);

    /**
     * Show info employees
     */
    void show();

    /**
     * Remove employee by id
     */
    Employee remove(long id);

    /**
     * Remove employee at specific index
     */
    Employee remove(int index);

    /**
     * Edit first name of employee
     */
    String editFirstName(long id, String firstName);

    /**
     * Sort employee by first name and last name
     */
    void sortByFirstNameAndLastName();

    /**
     * Sort by salary
     */
    void sortBySalary();

}
