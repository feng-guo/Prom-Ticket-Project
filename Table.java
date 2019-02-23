import java.util.ArrayList;

public class Table {
  private int size;
  private ArrayList<Student> students;

  Table(int var1) {
    this.size = var1;
    this.students = new ArrayList();
  }

  public ArrayList<Student> getStudents() {
    return this.students;
  }

  public int getNumStudents() {
    return this.students.size();
  }

  public void setStudents(ArrayList<Student> var1) {
    this.students = var1;
  }

  public void addStudent(Student var1) {
    this.students.add(var1);
  }

  public void addStudents(ArrayList<Student> var1) {
    this.students.addAll(var1);
  }
}
