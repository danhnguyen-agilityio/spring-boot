package structure.composite.pattern.demo;

import java.util.ArrayList;

abstract class TaskItem {
  public abstract double getTime();
}

class Task extends TaskItem {
  String name;
  double time;

  public Task() {}

  public Task(String name, double time) {
    this.name = name;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public double getTime() {
    return time;
  }

  public void setTime(double time) {
    this.time = time;
  }
}

class Project extends TaskItem {
  String name;
  ArrayList<TaskItem> subTask = new ArrayList<>();

  public Project(String name, ArrayList<TaskItem> subTask) {
    this.name = name;
    this.subTask = subTask;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<TaskItem> getSubTask() {
    return subTask;
  }

  public void setSubTask(ArrayList<TaskItem> subTask) {
    this.subTask = subTask;
  }

  @Override
  public double getTime() {
    double time = 0;
    for (int i = 0; i < subTask.size(); i++) {
      time += subTask.get(i).getTime();
    }
    return time;
  }

  public void addTask(TaskItem taskItem) {
    if (subTask.contains(taskItem) == false) {
      subTask.add(taskItem);
    }
  }

  public void removeTask(TaskItem taskItem) {
    subTask.remove(taskItem);
  }
}

public class CompositePatternEx {
  public static void main(String[] args) {
    Task task1 = new Task("Requirement", 50);
    Task task2 = new Task("Requirement", 34);
    Task task3 = new Task("Requirement", 65);
    Task task4 = new Task("Requirement", 23);
    Task task5 = new Task("Requirement", 32);

    ArrayList<TaskItem> subTask = new ArrayList<>();
    subTask.add(task1);
    subTask.add(task2);
    subTask.add(task3);
    subTask.add(task4);
    subTask.add(task5);

    Project project = new Project("quan li diem", subTask);

    System.out.println("Total time: " + project.getTime());
  }
}
