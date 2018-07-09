package demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.junit.Assert.*;

public class TestEmployeeDetails {
  EmpBusinessLogic empBusinessLogic = new EmpBusinessLogic();
  EmployeeDetails employee = new EmployeeDetails();

  @Rule
  public ErrorCollector collector = new ErrorCollector();

  @Test
  public void testCalculateAppriasal() {
    employee.setName("David");
    employee.setAge(25);
    employee.setMonthlySalary(8000);

    double appraisal = empBusinessLogic.calculateAppraisal(employee);
    assertEquals(500, appraisal, 0.0);
  }

  @Test
  public void testCalculateYearLySalary() {
    employee.setName("David");
    employee.setAge(25);
    employee.setMonthlySalary(8000);

    double salary = empBusinessLogic.calculateYearlySalary(employee);
    assertEquals(96000, salary, 0.0);
  }

  @Test
  public void testGetAge() {
    employee.setName("David");
    employee.setAge(25);
    employee.setMonthlySalary(8000);

    try {
      assertEquals(30, employee.getAge());
    } catch (Throwable t) {
      collector.addError(t);
    }
    System.out.println("David");

  }

  @Test
  public void testGetAge2() {
    employee.setName("Ramos");
    employee.setAge(25);
    employee.setMonthlySalary(8000);
    System.out.println("Ramos");
    assertEquals(25, employee.getAge());
  }

  @Test
  public void testGetAge3() {
    employee.setName("Rose");
    employee.setAge(25);
    employee.setMonthlySalary(8000);

    System.out.println("Rose");
    assertEquals(40, employee.getAge());
  }
}
