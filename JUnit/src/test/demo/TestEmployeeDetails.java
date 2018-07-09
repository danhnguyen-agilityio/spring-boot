package demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestEmployeeDetails {
  EmpBusinessLogic empBusinessLogic = new EmpBusinessLogic();
  EmployeeDetails employee = new EmployeeDetails();

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

}
