/* Student.java
 * Purpose: A student object with certain characteristics. A masterlist of students is saved in the ticketing system class
 * Creators: Shi Han Qin and Feng Guo
 * Date: 2019-02-14
 */

import java.util.ArrayList;

public class Student {
    private String firstName;
    private String lastName;
    private String fullName;
    private String studentNumber;
    private ArrayList<String> dietaryRestrictions;
    private ArrayList<String> friendStudentNumbers;

    //Constructor
    Student(String firstName, String lastName, String studentNumber, ArrayList<String> dietaryRestrictions, ArrayList<String> friendStudentNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.fullName = fixName(firstName, lastName);
        this.dietaryRestrictions = dietaryRestrictions;
        this.friendStudentNumbers = friendStudentNumbers;
    }
    
    Student(String fullName, String studentNumber, ArrayList<String> dietaryRestrictions, ArrayList<String> friendStudentNumbers) {
        String[] name = fullName.split(" ");
        this.fullName = fullName;
        this.firstName = name[0];
        this.lastName = name[1];
        this.studentNumber = studentNumber;
        this.fullName = fixName(firstName, lastName);
        this.dietaryRestrictions = dietaryRestrictions;
        this.friendStudentNumbers = friendStudentNumbers;
    }

    /** 
     * fixName
     * Concatenates the changed first and last name
     * @param firstName the new first name of the student
     * @param lastName the new last name of the student 
     * @return fullName the new full name
     */
    private String fixName(String firstName, String lastName) {
        //Fix method later
        String newName;
        newName = firstName + " " + lastName;
        return newName;
    }

    /** 
     * getFirstName
     * Gets the first name of the student
     * @return firstName the first name of the student
     */
    public String getFirstName() {
        return firstName;
    }

    /** 
     * setFirstName
     * Sets the first name of the student
     * @param firstName first name of student
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.fullName = fixName(firstName, this.lastName);
    }

    /** 
     * getLastName
     * Gets the last name of the student
     * @return lastName the last name of the student
     */
    public String getLastName() {
        return lastName;
    }

    /** 
     * setLastName
     * Sets the last name of the student
     * @param lastName the last name of the student
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.fullName = fixName(this.firstName, lastName);
    }

    /** 
     * getStudentNumber
     * Gets the student number of the student
     * @return studentNumber the studentNumber of the student
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /** 
     * setStudentNumber
     * Sets the student number of the student
     * @param studentNumber the student number of the student
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /** 
     * getName
     * Gets the name of the student
     * @return name the full name of the student
     */
    public String getName() {
        return fullName;
    }

    /**
     * setName
     * Gets the full name of the student
     * @param name the full name of the student
     */
    public void setName(String name) {
        this.fullName = name;
    }

    /** 
     * getDietaryRestrictions 
     * Gets all the dietary restrictions of the student
     * @return dietaryRestrictions a string arraylist of dietary restrictions
     */
    public ArrayList<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    /** 
     * setDietaryRestrictions
     * Sets the dietary restrictions of the student
     * @param dietaryRestrictions string arraylist of dietary restrictions
     */
    public void setDietaryRestrictions(ArrayList<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }
    
    /** 
     * getFriendStudentNumbers
     * Gets the friend student numbers
     * @return friendStudentNumbers the arraylist of friend student numbers
     */
    public ArrayList<String> getFriendStudentNumbers() {
        return friendStudentNumbers;
    }
    
    /** 
     * setFriendStudentNumbers
     * Sets the friend student numbers
     * @param friendStudentNumbers the string arraylist of friend student numbers
     */
    public void setFriendStudentNumbers(ArrayList<String> friendStudentNumbers) {
        this.friendStudentNumbers = friendStudentNumbers;
    }
}