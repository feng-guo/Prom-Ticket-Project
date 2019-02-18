/* TicketingSystem.java
 * Purpose: User interface that gets information from the reader
 * Creators: Shi Han Qin and Feng Guo
 * Date: 2019-02-14
 */

//Imports
import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

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
    private InformationScreen informationScreen;

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
     setScreen("StartScreen");
    }

    private void resetInformation() {
        eventName = "";
        numberOfTables = 0;
        peoplePerTable = 0;
        for (int i=0;i<masterListOfStudents.size();i++){
            masterListOfStudents.remove(i);
        }
    }
    private void setScreen(String screen) {
        remove(studentForm);
        remove(mainScreen);
        remove(startScreen);
        remove(informationScreen);
        remove(searchScreen);
        if (screen.equals("StudentForm")) {
            getContentPane().add(studentForm);
        } else if (screen.equals("MainScreen")) {
            getContentPane().add(mainScreen);
        } else if (screen.equals("StartScreen")) {
            getContentPane().add(startScreen);
        } else if (screen.equals("Search")) {
            getContentPane().add(searchScreen);
        } else if (screen.equals("DisplayInformation")) {
            informationScreen.displayInformation();
            getContentPane().add(informationScreen);
        }
        repaint();
        setVisible(true);
    }

    private void init() {
        mainScreen = new MainScreen();
        studentForm = new StudentForm();
        startScreen = new StartScreen();
        searchScreen = new SearchScreen();
        informationScreen = new InformationScreen();
    }

    private void repaintFrame() {
        repaint();
        setVisible(true);
    }

    private void modifyStudent(int arrayIndex, Student student){
        studentForm.modifyStudent(arrayIndex, student);
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

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetInformation();
                setScreen("StartScreen");
            }
        });
      
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
            try {
                numberOfTables = Integer.parseInt(numTablesTextField.getText());
                peoplePerTable = Integer.parseInt(peopleTablesTextField.getText());
            } catch (NumberFormatException e) {
                System.out.println("stop yourself");
            }
            if (!eventName.equals("") && numberOfTables != 0 && peoplePerTable != 0) {
                setScreen("MainScreen");
            } else {
                System.out.println("Frick you");
            }
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
            repaintFrame();
          }
        });
        add(openExistingPlan);
        add(openNewPlan);
      }

      private void startStartScreen() {
          removeAll();
          add(openExistingPlan);
          add(openNewPlan);
          repaintFrame();
      }

      private void openExistingPlan() {
          //Stuff to setup the new plan
          removeAll();
          repaintFrame();
          JLabel planNamePrompt = new JLabel("What is the name of your file?");
          JTextField planNameTextField = new JTextField(9);
          JButton backButton = new JButton("Back");
          JButton okayButton = new JButton("Load");
          backButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                  resetInformation();
                  startStartScreen();
              }
          });

          add(planNamePrompt);
          add(planNameTextField);
          okayButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                  String nameOfFile = planNameTextField.getText();
                  if (nameOfFile.length()>4) {
                      if (!nameOfFile.substring(nameOfFile.length()-4, nameOfFile.length()).equals(".txt")){
                          nameOfFile = nameOfFile + ".txt";
                      }
                  }
                  if (!nameOfFile.equals("")) {
                      parsePlanFile(nameOfFile);
                      setScreen("MainScreen");
                  } else {
                      System.out.println("Frick you");
                  }
              }
          });
          add(okayButton);
          add(backButton);
          repaintFrame();
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
                      ArrayList<String> friends = new ArrayList<>(), restrictions = new ArrayList<>();
                      firstName = fileReader.nextLine();
                      lastName = fileReader.nextLine();
                      studentNumber = fileReader.nextLine();
                      tempRestrictions = fileReader.nextLine();
                      tempFriends = fileReader.nextLine();
                      if (tempFriends.length() > 2) {
                          tempFriends = tempFriends.substring(1, tempFriends.length() - 1);
                      } else {
                          tempFriends = "";
                      }
                      if (tempRestrictions.length() > 2) {
                          tempRestrictions = tempRestrictions.substring(1, tempRestrictions.length() - 1);
                      } else {
                          tempRestrictions = "";
                      }
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
                              friends.add(add);
                              add = "";
                          }
                      }
                      masterListOfStudents.add(new Student(firstName, lastName, studentNumber, restrictions, friends));
                  }
              }
          } catch (FileNotFoundException e){
              System.out.println("Oopsies Owu. we did a fucky wucky!!");
          }
      }
    }
    
    private class MainScreen extends JPanel {
        JButton addStudentButton;
        JButton searchButton;
        JButton arrangeTablesButton;
        JButton displayInformationButton;
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
            displayInformationButton = new JButton("Display Event Information");
            displayInformationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("DisplayInformation");
                }
            });
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
            add(displayInformationButton);
            add(saveButton);
            add(backButton);
            add(displayTablesButton);
        }

        private void saveData() {
            try {
                File saveFile = new File(eventName + ".txt");
                PrintWriter fileWriter = new PrintWriter(saveFile);
                fileWriter.println("#");
                fileWriter.println(eventName);
                fileWriter.println(numberOfTables);
                fileWriter.println(peoplePerTable);
                fileWriter.println("##");
                for (int i = 0; i < masterListOfStudents.size(); i++) {
                    fileWriter.println(masterListOfStudents.get(i).getFirstName());
                    fileWriter.println(masterListOfStudents.get(i).getLastName());
                    fileWriter.println(masterListOfStudents.get(i).getStudentNumber());
                    if (masterListOfStudents.get(i).getDietaryRestrictions().size()>0) {
                        fileWriter.print("{");
                        for (int j = 0; j < masterListOfStudents.get(i).getDietaryRestrictions().size() - 1; j++) {
                            fileWriter.print(masterListOfStudents.get(i).getDietaryRestrictions().get(j) + ",");
                        }
                        fileWriter.println(masterListOfStudents.get(i).getDietaryRestrictions().get(masterListOfStudents.get(i).getDietaryRestrictions().size() - 1) + "}");
                    } else {
                        fileWriter.println("{}");
                    }
                    if (masterListOfStudents.get(i).getFriendStudentNumbers().size()>0) {
                        fileWriter.print("{");
                        for (int j = 0; j < masterListOfStudents.get(i).getFriendStudentNumbers().size() - 1; j++) {
                            fileWriter.print(masterListOfStudents.get(i).getFriendStudentNumbers().get(j) + ",");
                        }
                        fileWriter.println(masterListOfStudents.get(i).getFriendStudentNumbers().get(masterListOfStudents.get(i).getFriendStudentNumbers().size() - 1) + "}");
                    } else {
                        fileWriter.println("{}");
                    }

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
        ActionListener newStudent;
        ActionListener updateStudent;

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
            newStudent = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    boolean completed = saveStudent();
                    if (completed) {
                        setScreen("MainScreen");
                        resetInteractions();
                    }
                }
            };
            saveButton.addActionListener(newStudent);
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
            if (checkOfAllergies[5].isSelected()) {
                dietaryRestrictions.add("Kosher");
            }
            if (checkOfAllergies[6].isSelected()) {
                dietaryRestrictions.add("Nut Allergy");
            }
            if (checkOfAllergies[7].isSelected()) {
                dietaryRestrictions.add("Peanut Allergy");
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

        public void modifyStudent(int arrayIndex, Student student) {
            resetInteractions();
            firstNameTextField.setText(student.getFirstName());
            lastNameTextField.setText(student.getLastName());
            studentNumberTextField.setText(student.getStudentNumber());
            for (int i=0;i<student.getDietaryRestrictions().size();i++) {
                if (student.getDietaryRestrictions().get(i).equals("Vegetarian")) {
                    checkOfAllergies[0].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Vegan")) {
                    checkOfAllergies[1].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Lactose Intolerant")) {
                checkOfAllergies[2].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Gluten-Free")) {
                    checkOfAllergies[3].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Halal")) {
                    checkOfAllergies[4].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Kosher")) {
                    checkOfAllergies[5].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Nut Allergy")) {
                    checkOfAllergies[6].setSelected(true);
                } else if (student.getDietaryRestrictions().get(i).equals("Peanut Allergy")) {
                    checkOfAllergies[7].setSelected(true);
                }
            }
            for (int i=0; i<student.getFriendStudentNumbers().size();i++) {
                friendsTextField[i].setText(student.getFriendStudentNumbers().get(i));
            }
            saveButton.removeActionListener(newStudent);
            updateStudent = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    searchScreen.updateStudent(arrayIndex, makeStudent());
                    resetInteractions();
                    setScreen("MainScreen");
                }
            };
            saveButton.addActionListener(updateStudent);
        }

        private Student makeStudent() {
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
            if (checkOfAllergies[5].isSelected()) {
                dietaryRestrictions.add("Kosher");
            }
            if (checkOfAllergies[6].isSelected()) {
                dietaryRestrictions.add("Nut Allergy");
            }
            if (checkOfAllergies[7].isSelected()) {
                dietaryRestrictions.add("Peanut Allergy");
            }
            ArrayList<String> friendStudentNumbers = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (!friendsTextField[i].getText().equals("")) {
                    friendStudentNumbers.add(friendsTextField[i].getText());
                }
            }
            if (!firstName.equals("") || !lastName.equals("") && !studentNumber.equals("")) {
                return new Student(firstName, lastName, studentNumber, dietaryRestrictions, friendStudentNumbers);
            } else {
                //Change this to a proper label later
                System.out.println("You left something blank");
                return null;
            }
        }
    }

    private class SearchScreen extends JPanel {
        ArrayList<Student> listOfResults;
        ArrayList<Integer> masterListIndex;
        JLabel search,results;
        JTextField searchField;
        JButton searchButton;
        JButton backButton;

        SearchScreen(){
            backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("MainScreen");
                }
            });
            add(backButton);
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
            masterListIndex = new ArrayList<>();
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
                        masterListIndex.add(i);
                    } else if (masterListOfStudents.get(i).getLastName().equalsIgnoreCase(searchQuery)){
                        listOfResults.add(masterListOfStudents.get(i));
                        masterListIndex.add(i);
                    } else if (masterListOfStudents.get(i).getStudentNumber().equalsIgnoreCase(searchQuery)){
                        listOfResults.add(masterListOfStudents.get(i));
                        masterListIndex.add(i);
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
                Student referencedStudent = listOfResults.get(i);
                int arrayIndex = masterListIndex.get(i);
                modifyThisStudentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        setScreen("StudentForm");
                        modifyStudent(arrayIndex, referencedStudent);
                    }
                });
                add(firstNameLabel);
                add(lastNameLabel);
                add(studentNumberLabel);
                add(modifyThisStudentButton);
            }
            repaintFrame();
        }
        public void updateStudent(int arrayIndex, Student updatedStudent){
            masterListOfStudents.set(arrayIndex, updatedStudent);
        }
        private void resetInteractions() {
            for (int i=0; i<listOfResults.size();i++){
                //remove() remove the necessary shit from the panel
                listOfResults.remove(i);
                masterListIndex.remove(i);
            }
        }
    }

    private class InformationScreen extends JPanel {
        JButton backButton;
        InformationScreen() {
            backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("MainScreen");
                }
            });
        }

        private void displayInformation() {
            resetScreen();
            add(backButton);
            JLabel eventNameLabel, numberOfPeoplePerTableLabel, numberOfTablesLabel;
            eventNameLabel = new JLabel("Event Name: " + eventName);
            numberOfPeoplePerTableLabel = new JLabel("Number of people per table: " + Integer.toString(numberOfTables));
            numberOfTablesLabel = new JLabel("Number of tables: " + Integer.toString(peoplePerTable));
            add(eventNameLabel);
            add(numberOfPeoplePerTableLabel);
            add(numberOfTablesLabel);
            for (int i=0; i<masterListOfStudents.size(); i++) {
                JLabel firstNameLabel = new JLabel(masterListOfStudents.get(i).getFirstName());
                JLabel lastNameLabel = new JLabel(masterListOfStudents.get(i).getLastName());
                JLabel studentNumberLabel = new JLabel(masterListOfStudents.get(i).getStudentNumber());
                JButton modifyThisStudentButton = new JButton("Modify Information");
                int arrayIndex = i;
                Student referencedStudent = masterListOfStudents.get(i);
                modifyThisStudentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        setScreen("StudentForm");
                        modifyStudent(arrayIndex, referencedStudent);
                    }
                });
                add(firstNameLabel);
                add(lastNameLabel);
                add(studentNumberLabel);
                add(modifyThisStudentButton);
            }
        }

        private void resetScreen() {
            removeAll();
        }
    }
}