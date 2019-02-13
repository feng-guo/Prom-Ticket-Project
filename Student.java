/* Student.java
  Purpose: A student object with certain characteristics. A masterlist of students is saved in the ticketing system class
  Creators: Shi Han Qin and Feng Guo
  Date: 2019-02-12
 */

import java.util.ArrayList;

public class Student {
    private String firstName;
    private String lastName;
    private String fullName;
    private String studentNumber;
    private ArrayList<String> dietaryRestrictions;
    private ArrayList<String> friendStudentNumbers;

    Student(String firstName, String lastName, String studentNumber, ArrayList<String> dietaryRestrictions, ArrayList<String> friendStudentNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.fullName = fixName(firstName, lastName);
        this.dietaryRestrictions = dietaryRestrictions;
        this.friendStudentNumbers = friendStudentNumbers;
    }

    private String fixName(String firstName, String lastName) {
        //Fix method later
        return firstName + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.fullName = fixName(firstName, this.lastName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.fullName = fixName(this.firstName, lastName);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public ArrayList<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(ArrayList<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public ArrayList<String> getFriendStudentNumbers() {
        return friendStudentNumbers;
    }

    public void setFriendStudentNumbers(ArrayList<String> friendStudentNumbers) {
        this.friendStudentNumbers = friendStudentNumbers;
    }
}
