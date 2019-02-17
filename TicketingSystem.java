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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.util.Scanner;

public class TicketingSystem extends JFrame{
    //Variable declaration
    private String eventName;
    private int numberOfTables;
    private int peoplePerTable;
    private ArrayList<Student> masterListOfStudents;
    private MainScreen mainScreen;
    private StudentForm studentForm;
    private StartScreen startScreen;
    private SearchScreen searchScreen;

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
            getContentPane().add(searchScreen);
        }
        repaint();
        setVisible(true);
    }

    private void init() {
        mainScreen = new MainScreen();
        studentForm = new StudentForm();
        startScreen = new StartScreen();
        searchScreen = new SearchScreen();
    }

    private void repaintFrame() {
        repaint();
        setVisible(true);
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
            openExistingPlan();
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

      private void openExistingPlan() {
          //Stuff to setup the new plan
          String givenName = "feng.txt"; //want the name of the file
          parsePlanFile(givenName);
      }

      private void parsePlanFile(String fileName) {
          try {
              File parsedFile = new File(fileName);
              Scanner fileReader = new Scanner(parsedFile);
              String stage = "Start";
              while (fileReader.hasNext()) {
                  if (stage.equals("Start")) {
                      if (fileReader.nextLine().equals("#")) {
                          stage = "Event details";
                      }
                  } else if (stage.equals("Event details")) {
                      eventName = fileReader.nextLine();
                      numberOfTables = fileReader.nextInt();
                      fileReader.nextLine();
                      peoplePerTable = fileReader.nextInt();
                      fileReader.nextLine();
                      stage = "Students";
                  } else if (stage.equals("Students")) {
                      if (fileReader.nextLine().equals("##")) {
                          stage = "Student";
                      }
                  } else if (stage.equals("Student")) {
                      String firstName, lastName, studentNumber;
                      String tempRestrictions, tempFriends;
                      ArrayList<String> friends, restrictions = new ArrayList<>();
                      firstName = fileReader.nextLine();
                      lastName = fileReader.nextLine();
                      studentNumber = fileReader.nextLine();
                      tempRestrictions = fileReader.nextLine();
                      tempFriends = fileReader.nextLine();
                      tempFriends = tempFriends.substring(1,tempFriends.length()-1);
                      tempRestrictions = tempFriends.substring(1, tempRestrictions.length()-1);
                      String add="";
                      for (int i=0;i<tempRestrictions.length();i++) {
                          if (!tempRestrictions.substring(i,i+1).equals(",")){
                              add = add + tempRestrictions.substring(i,i+1);
                          } else {
                              restrictions.add(add);
                              add = "";
                          }
                      }
                      add="";
                      for (int i=0;i<tempFriends.length();i++) {
                          if (!tempFriends.substring(i,i+1).equals(",")){
                              add = add + tempFriends.substring(i,i+1);
                          } else {
                              restrictions.add(add);
                              add = "";
                          }
                      }
                  }
              }
          } catch (FileNotFoundException e){
              //who cares
          }
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
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("Search");
                }
            });
            arrangeTablesButton = new JButton("Arrange Tables");
            saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    saveData();
                }
            });
            backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("StartScreen");
                }
            });
            displayTablesButton = new JButton("Display Tables");
            add(addStudentButton);
            add(searchButton);
            add(arrangeTablesButton);
            add(saveButton);
            add(backButton);
            add(displayTablesButton);
        }

        private void saveData() {
            try {
                File saveFile = new File("Goodeventname.txt");
                PrintWriter fileWriter = new PrintWriter(saveFile);
//                fileWriter.println("#");
//                fileWriter.println(eventName);
//                fileWriter.println(numberOfTables);
//                fileWriter.println(peoplePerTable);
                fileWriter.println("##");
                for (int i = 0; i < masterListOfStudents.size(); i++) {
                    fileWriter.println(masterListOfStudents.get(i).getFirstName());
                    fileWriter.println(masterListOfStudents.get(i).getLastName());
                    fileWriter.println(masterListOfStudents.get(i).getStudentNumber());
                    fileWriter.print("{");
                    for (int j = 0; j < masterListOfStudents.get(i).getDietaryRestrictions().size() - 1; j++) {
                        fileWriter.print(masterListOfStudents.get(i).getDietaryRestrictions().get(j) + ",");
                    }
                    fileWriter.println(masterListOfStudents.get(i).getDietaryRestrictions().get(masterListOfStudents.get(i).getDietaryRestrictions().size()-1) + "}");
                    fileWriter.print("{");
                    for (int j = 0; j < masterListOfStudents.get(i).getFriendStudentNumbers().size() - 1; j++) {
                        fileWriter.print(masterListOfStudents.get(i).getFriendStudentNumbers().get(j) + ",");
                    }
                    fileWriter.println(masterListOfStudents.get(i).getFriendStudentNumbers().get(masterListOfStudents.get(i).getFriendStudentNumbers().size()-1) + "}");

                }
                fileWriter.close();
            } catch (FileNotFoundException e) {

            }
        }
    }

    private class StudentForm extends JPanel {
        JLabel firstNameLabel;
        JLabel lastNameLabel;
        JLabel studentNumberLabel;
        JLabel allergiesLabel;
        JLabel friendPreferencesLabel;
        JLabel[] listOfAllergies = new JLabel[8];
        JCheckBox[] checkOfAllergies = new JCheckBox[8];
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
            firstNameTextField = new JTextField(null, 10);
            lastNameTextField = new JTextField(null, 10);
            studentNumberTextField = new JTextField(null, 10);
            listOfAllergies[0] = new JLabel("Vegetarian");
            listOfAllergies[1] = new JLabel("Vegan");
            listOfAllergies[2] = new JLabel("Lactose Intolerant");
            listOfAllergies[3] = new JLabel("Gluten-Free");
            listOfAllergies[4] = new JLabel("Halal");
            listOfAllergies[5] = new JLabel("Kosher");
            listOfAllergies[6] = new JLabel("Nut Allergy");
            listOfAllergies[7] = new JLabel("Peanut Allergy");
            add(firstNameLabel);
            add(firstNameTextField);
            add(lastNameLabel);
            add(lastNameTextField);
            add(studentNumberLabel);
            add(studentNumberTextField);
            for (int i=0;i<8;i++) {
                checkOfAllergies[i] = new JCheckBox();
                add(checkOfAllergies[i]);
                add(listOfAllergies[i]);
            }
            add(friendPreferencesLabel);
            for (int i=0;i<9;i++) {
                friendsTextField[i] = new JTextField(null, 9);
                add(friendsTextField[i]);
                friendsTextField[i].getText();
            }
            saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    boolean completed = saveStudent();
                    if (completed) {
                        setScreen("MainScreen");
                        resetInteractions();
                    }
                }
            });
            add(saveButton);
            addAnotherButton = new JButton("Add Another Student");
            addAnotherButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    boolean completed = saveStudent();
                    if (completed) {
                        resetInteractions();
                    }
                }
            });
            add(addAnotherButton);
            backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    resetInteractions();
                    setScreen("MainScreen");
                }
            });
            add(backButton);
        }

        private boolean saveStudent() {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String studentNumber = studentNumberTextField.getText();
            ArrayList<String> dietaryRestrictions = new ArrayList<>();
            if (checkOfAllergies[0].isSelected()) {
                dietaryRestrictions.add("Vegetarian");
            }
            if (checkOfAllergies[1].isSelected()) {
                dietaryRestrictions.add("Vegan");
            }
            if (checkOfAllergies[2].isSelected()) {
                dietaryRestrictions.add("Lactose Intolerant");
            }
            if (checkOfAllergies[3].isSelected()) {
                dietaryRestrictions.add("Gluten-Free");
            }
            if (checkOfAllergies[4].isSelected()) {
                dietaryRestrictions.add("Halal");
            }
            if (checkOfAllergies[6].isSelected()) {
                dietaryRestrictions.add("Kosher");
            }
            ArrayList<String> friendStudentNumbers = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (!friendsTextField[i].getText().equals("")) {
                    friendStudentNumbers.add(friendsTextField[i].getText());
                }
            }
            if (!firstName.equals("") || !lastName.equals("") && !studentNumber.equals("")) {
                masterListOfStudents.add(new Student(firstName, lastName, studentNumber, dietaryRestrictions, friendStudentNumbers));
                return true;
            } else {
                //Change this to a proper label later
                System.out.println("You left something blank");
                return false;
            }
          }
        
        private void resetInteractions() {
            firstNameTextField.setText(null);
            lastNameTextField.setText(null);
            studentNumberTextField.setText(null);
            for (int i=0;i<8;i++) {
              checkOfAllergies[i].setSelected(false);
            }
            for (int i=0;i<9;i++) {
                this.friendsTextField[i].setText(null);
            }
        }
    }

    private class SearchScreen extends JPanel {
        ArrayList<Student> listOfResults;
        //ArrayList<JButton> listOfButtons;
        JLabel search,results;
        JTextField searchField;
        JButton searchButton;

        SearchScreen(){
            search = new JLabel("Search by Name or Student Number");
            results = new JLabel("Results");
            searchField = new JTextField(10);
            searchButton = new JButton("Search");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    search();
                    display();
                }
            });
            add(search);
            add(searchField);
            add(searchButton);
            listOfResults = new ArrayList<>();
            //listOfButtons = new ArrayList<>();
        }

        private void search() {
            String searchQuery = searchField.getText();
            if (searchQuery.substring(searchQuery.length()-1, searchQuery.length()).equals(" ")){
                searchQuery = searchQuery.substring(0, searchQuery.length()-1);
            }
            if (!searchQuery.equals("")){
                for (int i=0; i<masterListOfStudents.size();i++){
                    if (masterListOfStudents.get(i).getFirstName().equalsIgnoreCase(searchQuery)){
                        listOfResults.add(masterListOfStudents.get(i));
                    } else if (masterListOfStudents.get(i).getLastName().equalsIgnoreCase(searchQuery)){
                        listOfResults.add(masterListOfStudents.get(i));
                    } else if (masterListOfStudents.get(i).getStudentNumber().equalsIgnoreCase(searchQuery)){
                        listOfResults.add(masterListOfStudents.get(i));
                    }
                }
            }
        }

        private void display() {
            for(int i=0;i<listOfResults.size();i++) {
                JLabel firstNameLabel = new JLabel(listOfResults.get(i).getFirstName());
                JLabel lastNameLabel = new JLabel(listOfResults.get(i).getLastName());
                JLabel studentNumberLabel = new JLabel(listOfResults.get(i).getStudentNumber());
                JButton modifyThisStudentButton = new JButton("Modify Information");
                add(firstNameLabel);
                add(lastNameLabel);
                add(studentNumberLabel);
                add(modifyThisStudentButton);
            }
            repaintFrame();
        }

        private void resetInteractions() {
            for (int i=0; i<listOfResults.size();i++){
                //remove() remove the necessary shit from the panel
                listOfResults.remove(i);
            }
        }
    }
}