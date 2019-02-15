/* TicketingSystem.java
 * Purpose: User interface that gets information from the reader
 * Creators: Shi Han Qin and Feng Guo
 * Date: 2019-02-14
 */

//Imports
import javax.swing.*;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Toolkit;

public class TicketingSystem extends JFrame{
    //Variable declaration
    private String eventName;
    private int numberOfTables;
    private int peoplePerTable;
    private ArrayList<Student> masterListOfStudents;
    private MainScreen mainScreen;
    private StudentForm studentForm;
    private StartScreen startScreen;

    /** 
     * getNumberOfTables
     * Gets the number of tables user enters 
     * @return numberOfTables the number of tables for the venue
     */
    public int getNumberOfTables() {
        return numberOfTables;
    }

    /** 
     * setNumberOfTables
     * Set number of tables for the venue
     * @param numberOfTables number of tables for the venue
     */
    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    /** 
     * getPeoplePerTable
     * Gets the number of people per table
     * @return peoplePerTable amount of people per table
     */
    public int getPeoplePerTable() {
        return peoplePerTable;
    }

    /** 
     * setPeoplePerTable
     * Sets the number of people per table
     * @param peoplePerTable amount of people per table
     */
    public void setPeoplePerTable(int peoplePerTable) {
        this.peoplePerTable = peoplePerTable;
    }

    /** 
     * getMasterListOfStudents
     * Gets the masterlist of students
     * @return masterListOfStudents the arraylist of students for masterlist
     */
    public ArrayList<Student> getMasterListOfStudents() {
        return masterListOfStudents;
    }

    /** 
     * setMasterListOfStudents
     * Sets the list of students
     * @param masterListOfStudents arraylist of students for masterlist
     */
    public void setMasterListOfStudents(ArrayList<Student> masterListOfStudents) {
        this.masterListOfStudents = masterListOfStudents;
    }

    /** 
     * getEventName
     * Gets the event name 
     * @return eventName name of the event
     */
    public String getEventName() {
        return eventName;
    }

    /** 
     * setEventName
     * Sets the event name for the plan
     * @param eventName the name for the event
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    //Main
    public static void main(String[] args) {
        TicketingSystem ticketingSystem = new TicketingSystem();
    }

    //Constructor
    TicketingSystem() {
     super("Prom Ticketing System");
     this.setVisible(true);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setSize(1000,1000);
     init();
     masterListOfStudents = new ArrayList<Student>();
     setScreen("MainScreen");
    }

    private void setScreen(String screen) {
        remove(studentForm);
        remove(mainScreen);
        remove(startScreen);
        if (screen.equals("StudentForm")) {
            getContentPane().add(studentForm);
        } else if (screen.equals("MainScreen")) {
            getContentPane().add(mainScreen);
        } else if (screen.equals("StartScreen")) {
            getContentPane().add(startScreen);
        } else if (screen.equals("Search")) {
            //build it
        }
        repaint();
        setVisible(true);
    }

    private void init() {
        mainScreen = new MainScreen();
        studentForm = new StudentForm();
        startScreen = new StartScreen();
    }
    
    private class StartScreen extends JPanel{
      JButton openExistingPlan;
      JButton openNewPlan;
      JButton okButton;
      JButton backButton;
      JTextField eventNameTextField;
      JTextField numTablesTextField;
      JTextField peopleTablesTextField;
      JLabel eventNameLabel;
      JLabel numTablesLabel;
      JLabel peopleTablesLabel;       
      
      private void clickedStartPlan(){          
        //Done button
        JButton doneButton = new JButton("Submit");
        
        //Remove start buttons
        remove(openExistingPlan);
        remove(openNewPlan);
        
        //Create fields and labels
        eventNameTextField = new JTextField(20);
        numTablesTextField = new JTextField(20);
        peopleTablesTextField = new JTextField(20);
        eventNameLabel = new JLabel();
        numTablesLabel = new JLabel();
        peopleTablesLabel = new JLabel(); 
      
        //Add components to panel
        add(eventNameTextField);
        add(eventNameLabel);
        add(numTablesTextField);
        add(numTablesLabel);
        add(peopleTablesTextField);
        add(peopleTablesLabel);
        add(doneButton);
        
        //After user enters info
        doneButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            //Store user inputs
            eventName = eventNameTextField.getText();
            numberOfTables = Integer.parseInt(numTablesTextField.getText());
            peoplePerTable = Integer.parseInt(peopleTablesTextField.getText());
            setScreen("MainScreen");
          }
        });
        repaint();
      }
      
      StartScreen(){
        //super(LayoutManager GridLayout);
        openExistingPlan = new JButton("Open Existing Plan");
        openExistingPlan.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            //Open existing plan
          }
        });
        openNewPlan = new JButton ("Start New Plan");
        openNewPlan.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            clickedStartPlan();
            repaint();
          }
        });
        add(openExistingPlan);
        add(openNewPlan);
      }
    }
    
    private class MainScreen extends JPanel {
        JButton addStudentButton;
        JButton searchButton;
        JButton arrangeTablesButton;
        JButton saveButton;
        JButton backButton;
        JButton displayTablesButton;

        MainScreen() {
            super();
            addStudentButton = new JButton("Add Student");
            addStudentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("StudentForm");
                }
            });
            searchButton = new JButton("Search");
            arrangeTablesButton = new JButton("Arrange Tables");
            saveButton = new JButton("Save");
            backButton = new JButton("Back");
            displayTablesButton = new JButton("Display Tables");
            add(addStudentButton);
            add(searchButton);
            add(arrangeTablesButton);
            add(saveButton);
            add(backButton);
            add(displayTablesButton);
        }
    }

    private class StudentForm extends JPanel {
        JLabel firstNameLabel;
        JLabel lastNameLabel;
        JLabel studentNumberLabel;
        JLabel allergiesLabel;
        JLabel friendPreferencesLabel;
        JLabel[] listOfAllergies = new JLabel[6];
        JCheckBox[] checkOfAllergies = new JCheckBox[6];
        JTextField firstNameTextField;
        JTextField lastNameTextField;
        JTextField studentNumberTextField;
        JTextField[] friendsTextField = new JTextField[9];

        JButton saveButton;
        JButton addAnotherButton;
        JButton backButton;

        StudentForm() {
            firstNameLabel = new JLabel("First Name");
            lastNameLabel = new JLabel("Last Name");
            studentNumberLabel = new JLabel("Student Number");
            allergiesLabel = new JLabel("Allergies");
            friendPreferencesLabel = new JLabel("Friend Student Numbers");
            firstNameTextField = new JTextField(10);
            lastNameTextField = new JTextField(10);
            studentNumberTextField = new JTextField(10);
            JTextField[] friendsTextField = new JTextField[9];
            listOfAllergies[0] = new JLabel("Vegetarian");
            listOfAllergies[1] = new JLabel("Vegan");
            listOfAllergies[2] = new JLabel("Lactose Intolerant");
            listOfAllergies[3] = new JLabel("Gluten-Free");
            listOfAllergies[4] = new JLabel("Halal");
            listOfAllergies[5] = new JLabel("Kosher");
            firstNameTextField.setText(null);
            lastNameTextField.setText(null);
            studentNumberTextField.setText(null);
            add(firstNameLabel);
            add(firstNameTextField);
            add(lastNameLabel);
            add(lastNameTextField);
            add(studentNumberLabel);
            add(studentNumberTextField);
            for (int i=0;i<6;i++) {
                checkOfAllergies[i] = new JCheckBox();
                add(checkOfAllergies[i]);
                add(listOfAllergies[i]);
            }
            add(friendPreferencesLabel);
            for (int i=0;i<9;i++) {
                friendsTextField[i] = new JTextField(9);
                add(friendsTextField[i]);
            }
            saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    saveStudent();
                    setScreen("MainScreen");
                }
            });
            add(saveButton);
        }

        private void saveStudent() {
            String firstName = "";
            if (!firstNameTextField.getText().equals("")) {
                firstName = firstNameTextField.getText();
            } else {
                System.out.println("Frick you");
            }
            String lastName = lastNameTextField.getText();
            String studentNumber = studentNumberTextField.getText();
            ArrayList<String> dietaryRestrictions = new ArrayList();
            for (int i=0;i<6;i++) {
                dietaryRestrictions.add("Vegan");
            }
            ArrayList<String> friendStudentNumbers = new ArrayList();
            for (int i=0;i<9;i++) {
                dietaryRestrictions.add("200000");
            }
            masterListOfStudents.add(new Student(firstName, lastName, studentNumber, dietaryRestrictions, friendStudentNumbers));
        }
    }
}