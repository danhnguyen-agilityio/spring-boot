package employ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Class Employee
 */
public class Employee {
    long id;
    String firstName;
    String lastName;
    String sex;
    String dayOfBirth;
    int level;
    double basicSalary;

    public Employee(String firstName, String lastName, String sex, String dayOfBirth, int level, double basicSalary) {
        Date date = new Date();
        this.id = date.getTime();
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.dayOfBirth = dayOfBirth;
        this.level = level;
        this.basicSalary = basicSalary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String setFirstName(String firstName) {
        String temp = this.firstName;
        this.firstName = firstName;
        return temp;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    /**
     * Get salary
     * @return salary of employee
     */
    public double getSalary() {
        return level * basicSalary;
    }

    /**
     * Get full name
     * @return full name of employee
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Get age of employee
     * @return age of employee
     */
    public int getAge() throws ParseException {
        int currentYear = new Date().getYear();
        int birthYear = new SimpleDateFormat("dd/MM/yyyy").parse(getDayOfBirth()).getYear();
        return currentYear - birthYear + 1;
    }

    /**
     * Get info when user want to see
     * @return info employee
     */
    public String toString() {
        String result = printSpace(Long.toString(id), 20)
            + printSpace(firstName, 10)
            + printSpace(lastName, 15)
            + printSpace(sex, 7)
            + printSpace(dayOfBirth, 15)
            + printSpace(Integer.toString(level), 3)
            + printSpace(Double.toString(basicSalary), 10);

        return result;
    }

    /**
     * Print additional space after word to have specific length
     * @param word
     * @param length
     */
    public String printSpace(String word, int length) {
        for (int i = word.length() + 1; i <= length; i++) {
            word += " ";
        }
        return word;
    }
}
