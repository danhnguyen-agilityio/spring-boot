package employ;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Get info when user want to see
     * @return info employee
     */
    public String toString() {
        String result = id + "  " + firstName + "  " + lastName + "  " + sex + "  " +
                dayOfBirth + "  " + level + "  " + basicSalary;
        return result;
    }
}
