package structure.composite.pattern.demo1;

import java.util.ArrayList;
import java.util.List;

interface ITeacher {
  public String getDetails();
}

class Teacher implements ITeacher {
  private String teacherName;
  private String deptName;
  private List<ITeacher> controls;

  public Teacher(String teacherName, String deptName) {
    this.teacherName = teacherName;
    this.deptName = deptName;
    controls = new ArrayList<>();
  }
  public void add(Teacher teacher) {
    controls.add(teacher);
  }

  public void remove(Teacher teacher) {
    controls.remove(teacher);
  }

  public  List<ITeacher> getControllingDepts() {
    return controls;
  }

  @Override
  public String getDetails() {
    return teacherName + " is the " + deptName;
  }
}

public class CompositePatternEx {
  public static void main(String[] args) {
    Teacher Principal = new Teacher("Som", "Principal");

    Teacher hodMaths = new Teacher("Das", "Hod_Math");

    Teacher hodCompSc = new Teacher("Sarcar", "Hod-ComputerSc");

    Teacher mathTeacher1 = new Teacher("Teacher-1", "MathsTeacher");
    Teacher mathTeacher2 = new Teacher("Teacher-2", "MathsTeacher");

    Teacher cseTeacher1 = new Teacher("CSE Teacher-1", "CSETeacher");
    Teacher cseTeacher2 = new Teacher("CSE Teacher-2", "CSETeacher");
    Teacher cseTeacher3 = new Teacher("CSE Teacher-3", "CSETeacher");

    // Principal is on top of college
    Principal.add(hodMaths);
    Principal.add(hodCompSc);

    // Teacher of Mathematics directly reports to HOD-Maths
    hodMaths.add(mathTeacher1);
    hodMaths.add(mathTeacher2);

    // Teacher of Computer Sc directly reports to HOD-Comps
    hodCompSc.add(cseTeacher1);
    hodCompSc.add(cseTeacher2);
    hodCompSc.add(cseTeacher3);

    // Leaf nodes
    mathTeacher1.add(null);
    mathTeacher2.add(null);
    cseTeacher1.add(null);
    cseTeacher2.add(null);
    cseTeacher3.add(null);

    System.out.println("The college has following structure");
    System.out.println(Principal.getDetails());
    List<ITeacher> hods = Principal.getControllingDepts();
    for (int i = 0; i < hods.size(); i++) {
      System.out.println("\t" + hods.get(i).getDetails());
    }

    List<ITeacher> mathTeachers = hodMaths.getControllingDepts();
    for (int i = 0; i < mathTeachers.size(); i++) {
      System.out.println("\t\t" + mathTeachers.get(i).getDetails());
    }

    List<ITeacher> cseTeachers = hodCompSc.getControllingDepts();
    for (int i = 0; i < cseTeachers.size(); i++) {
      System.out.println("\t\t" + cseTeachers.get(i).getDetails());
    }
  }

}
