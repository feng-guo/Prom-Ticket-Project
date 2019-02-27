/* TicketingSystem.java
 * Purpose: User interface that gets information from the reader
 * Creators: Shi Han Qin and Feng Guo
 * Date: 2019-02-27
 */

//Imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class TicketingSystem extends JFrame{
  //Variable declaration
  private String eventName;
  private int numberOfTables;
  private int peoplePerTable;
  private ArrayList<Student> masterListOfStudents;
  private MainScreen mainScreen;
  private StudentForm studentForm;
  private StartScreen startScreen;
  private InformationScreen informationScreen;
  private SearchScreen searchScreen;
  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  private Font welcomeMessageFont = new Font("Vivaldi",Font.PLAIN,100);
  private Font generalButtonFont = new Font ("Bahnschrift", Font.PLAIN,20);
  private SeatingAlg alg;
  private ArrayList<Table> listOfTables;
  private FloorPlan floorPlan;
  private JFrame warningBox;
  private boolean hasSaved;
  
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
  
  /** 
   * TicketSystem
   * Sets up constructor
   */
  TicketingSystem() {
    super("Prom Ticketing System");
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize((int)(screenSize.getWidth()),(int)(screenSize.getHeight()));
    init();
    masterListOfStudents = new ArrayList<>();
    setScreen("StartScreen");
  }
  
  //Sets the specified screen that is called
  private void setScreen(String screen) {
    //Clears screen 
    remove(studentForm);
    remove(mainScreen);
    remove(startScreen);
    remove(informationScreen);
    remove(searchScreen);
    //See what parameter was inputted into the method to set the screen
    if (screen.equals("StudentForm")) {
      getContentPane().add(studentForm);
    } else if (screen.equals("MainScreen")) {
      getContentPane().add(mainScreen);
    } else if (screen.equals("StartScreen")) {
      getContentPane().add(startScreen);
    } else if (screen.equals("Search")) {
      getContentPane().add(searchScreen);
    } else if (screen.equals("InformationScreen")) {
      informationScreen.initializeInformation();
      informationScreen.displayInformation(0);
      getContentPane().add(informationScreen);
    }
    repaint();
    setVisible(true);
  }
  
  //Initializes the different screens
  private void init() {
    mainScreen = new MainScreen();
    studentForm = new StudentForm();
    startScreen = new StartScreen();
    informationScreen = new InformationScreen();
    searchScreen = new SearchScreen();
    floorPlan = new FloorPlan();
    alg = new SeatingAlg();
    warningBox = new JFrame();
    listOfTables = new ArrayList(); //Hi
  }
  
  //Clears information
  private void resetInformation() {
    eventName = "";
    numberOfTables = 0;
    peoplePerTable = 0;
    for (int i=0;i<masterListOfStudents.size();i++){
      masterListOfStudents.remove(i);
    }
  }
  
  private void repaintFrame() {
    repaint();
    setVisible(true);
  }
  
  //Used to modify student information
  private void modifyStudent(int arrayIndex){
    studentForm.modifyStudent(arrayIndex);
    repaintFrame();
  }
  
  public void updateStudent(int arrayIndex, Student updatedStudent){
    masterListOfStudents.set(arrayIndex, updatedStudent);
  }
  
  //Creates the Starting Screen, the first screen that shows up on the program
  private class StartScreen extends JPanel {
    //Components for the panel
    JButton openExistingPlan;
    JButton openNewPlan;
    JTextField eventNameTextField;
    JTextField numTablesTextField;
    JTextField peopleTablesTextField;
    JLabel welcomeMessage;
    JLabel eventNameLabel;
    JLabel numTablesLabel;
    JLabel peopleTablesLabel;
    
    //Background image
    Image startScreenBackground = Toolkit.getDefaultToolkit().createImage("StartScreen.png");
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(startScreenBackground, 0, 0, this.getWidth(), this.getHeight() + 100, this);
    }
    
    //Method run when start plan is clicked
    private void clickedStartPlan() {
      //Done button
      JButton doneButton = new JButton("Submit");
      doneButton.setBounds((int) (screenSize.getWidth() / 2 - 75), 650, 150, 50);
      doneButton.setFont(generalButtonFont);
      doneButton.setForeground(Color.WHITE);
      doneButton.setBackground(new Color(38, 77, 0));
      //Back to start screen
      JButton backToStartButton = new JButton("Back");
      backToStartButton.setBounds((int) (screenSize.getWidth() / 2 - 75), 725, 150, 50);
      backToStartButton.setFont(generalButtonFont);
      backToStartButton.setForeground(Color.WHITE);
      backToStartButton.setBackground(new Color(38, 77, 0));
      
      //Remove start buttons
      remove(openExistingPlan);
      remove(openNewPlan);
      remove(welcomeMessage);
      
      //Create labels
      eventNameLabel = new JLabel("Please enter the name of the event below:");
      eventNameLabel.setBounds((int) (screenSize.getWidth() / 2 - 200), 200,400,eventNameLabel.getPreferredSize().height);
      eventNameLabel.setFont(generalButtonFont);
      eventNameLabel.setForeground(Color.WHITE);
      numTablesLabel = new JLabel("How many tables are at the event venue?");
      numTablesLabel.setBounds((int) (screenSize.getWidth() / 2 - 200), 350,400,numTablesLabel.getPreferredSize().height);
      numTablesLabel.setFont(generalButtonFont);
      numTablesLabel.setForeground(Color.WHITE);
      peopleTablesLabel = new JLabel("How many seats are available at each table?");
      peopleTablesLabel.setBounds((int) (screenSize.getWidth() / 2 - 200), 500,400,peopleTablesLabel.getPreferredSize().height);
      peopleTablesLabel.setFont(generalButtonFont);
      peopleTablesLabel.setForeground(Color.WHITE);
      
      //Create fields
      eventNameTextField = new JTextField();
      eventNameTextField.setBounds((int) (screenSize.getWidth() / 2 - 150), 250, 300, 25);
      numTablesTextField = new JTextField();
      numTablesTextField.setBounds((int) (screenSize.getWidth() / 2 - 150), 400, 300, 25);
      peopleTablesTextField = new JTextField();
      peopleTablesTextField.setBounds((int) (screenSize.getWidth() / 2 - 150), 550, 300, 25);
      
      //Add components to panel
      add(eventNameTextField);
      add(eventNameLabel);
      add(numTablesTextField);
      add(numTablesLabel);
      add(peopleTablesTextField);
      add(peopleTablesLabel);
      add(doneButton);
      add(backToStartButton);
      
      //Action when submit button is clicked
      doneButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          try {
            eventName = eventNameTextField.getText();
            numberOfTables = Integer.parseInt(numTablesTextField.getText());
            peoplePerTable = Integer.parseInt(peopleTablesTextField.getText());
            if (!eventName.equals("") && numberOfTables != 0 && peoplePerTable != 0) {
              setScreen("MainScreen");
            } else {
              warningBox.setSize(100,200);
              JOptionPane.showMessageDialog(warningBox, "Not all of the fields were filled!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
          } catch (NumberFormatException e) {
            warningBox.setSize(100, 200);
            JOptionPane.showMessageDialog(warningBox, "Numbers were not entered properly!", "Error!", JOptionPane.ERROR_MESSAGE);
          }  
        }
      });
      //Action when back button is clicked
      backToStartButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("StartScreen");  
          resetInteractions();
          repaintFrame();
        }
      });
      repaintFrame();
    }
    
    /** 
     * StartScreen
     * Sets up start screen
     */
    StartScreen() {
      super();
      this.setLayout(null);
      
      //Welcome message
      welcomeMessage = new JLabel("Prom Ticketing System");
      welcomeMessage.setFont(welcomeMessageFont);
      welcomeMessage.setBounds((int) (screenSize.getWidth() / 2 - welcomeMessage.getPreferredSize().width / 2), 350, welcomeMessage.getPreferredSize().width, welcomeMessage.getPreferredSize().height);
      welcomeMessage.setForeground(new Color(238, 255, 230));
      //openExistingPlanButton
      openExistingPlan = new JButton("Open Existing Plan");
      openExistingPlan.setBounds((int) (screenSize.getWidth() / 2 - 225), (int) (screenSize.getHeight() / 2 + 25), 200, 50);
      openExistingPlan.setFont(generalButtonFont);
      openExistingPlan.setBackground(new Color(38, 77, 0));
      openExistingPlan.setForeground(new Color(255, 255, 255));
      //Opens an existing plan when the "Open Existing Plan" button is clicked
      openExistingPlan.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          openExistingPlan();
          repaintFrame();
        }
      });
      //openNewPlan Button
      openNewPlan = new JButton("Start New Plan");
      openNewPlan.setBounds((int) (screenSize.getWidth() / 2 + 25), (int) (screenSize.getHeight() / 2 + 25), 200, 50);
      openNewPlan.setFont(generalButtonFont);
      openNewPlan.setBackground(new Color(38, 77, 0));
      openNewPlan.setForeground(new Color(255, 255, 255));
      openNewPlan.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          clickedStartPlan();
          repaintFrame();
        }
      });
      
      //Add components to the frame
      add(openExistingPlan);
      add(openNewPlan);
      add(welcomeMessage);
    }
    
    private void openExistingPlan() {
      //Remove existing components
      remove(openExistingPlan);
      remove(openNewPlan);
      remove(welcomeMessage);
      
      //Prompt
      JLabel planNamePrompt = new JLabel("What is the name of your file?");
      planNamePrompt.setFont(generalButtonFont);
      planNamePrompt.setForeground(Color.WHITE);
      planNamePrompt.setBounds((int)(screenSize.getWidth()/2-planNamePrompt.getPreferredSize().width/2),100,planNamePrompt.getPreferredSize().width,planNamePrompt.getPreferredSize().height);
      
      //Text field for user to enter name of file
      JTextField planNameTextField = new JTextField();
      planNameTextField.setBounds((int)(screenSize.getWidth()/2-150),150,300,25);
      
      //Button for user to advance back to starting screen
      JButton backButton = new JButton("Back");
      backButton.setFont(generalButtonFont);
      backButton.setForeground(Color.WHITE);
      backButton.setBackground(new Color(0,77,13));
      backButton.setBounds((int)(screenSize.getWidth()/2-100),200,100,50);
      JButton okayButton = new JButton("Load"); //What is this for?
      okayButton.setFont(generalButtonFont);
      okayButton.setForeground(Color.WHITE);
      okayButton.setBackground(new Color(0,77,13));
      okayButton.setBounds((int)(screenSize.getWidth()/2),200,100,50);
      
      //Brings back to starting screen
      backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("StartScreen");  
          resetInteractions();
          repaintFrame();
        }
      });
      
      //Add to panel
      add(planNamePrompt);
      add(planNameTextField);
      
      //Loads name of the file to be parsed
      okayButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          String nameOfFile = planNameTextField.getText();
          //Appends .txt to the end of the string
          if (nameOfFile.length() > 4) {
            if (!nameOfFile.substring(nameOfFile.length() - 4, nameOfFile.length()).equals(".txt")) {
              nameOfFile = nameOfFile + ".txt";
            }
          } else {
            nameOfFile = nameOfFile + ".txt";
          }
          
          //Sends file name to parse the file
          if (!nameOfFile.equals("") && !nameOfFile.equals(".txt")) {
            boolean successful = parsePlanFile(nameOfFile);
            if (successful) {
              setScreen("MainScreen");
            }
          } else {
            warningBox.setSize(100, 200);
            JOptionPane.showMessageDialog(warningBox, "Not a valid file!", "Error!", JOptionPane.ERROR_MESSAGE);
          }
        }
      });
      
      //Add buttons and repaints the frame
      add(okayButton);
      add(backButton);
      repaintFrame();
    }
    
    private boolean parsePlanFile(String fileName) {
      try {
        //Reads the file and adds the information to the TicketingSystem class
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
              String[] toAdd = tempFriends.split(",");
              for (int i=0; i<toAdd.length; i++) {
                friends.add(toAdd[i]);
              }
            } 
            if (tempRestrictions.length() > 2) {
              tempRestrictions = tempRestrictions.substring(1, tempRestrictions.length() - 1);
              String[] toAdd = tempRestrictions.split(",");
              for (int i=0; i<toAdd.length; i++) {
                restrictions.add(toAdd[i]);
              }
            }
            masterListOfStudents.add(new Student(firstName, lastName, studentNumber, restrictions, friends));
          }
        }
        return true;
      } catch (FileNotFoundException e) {
        //Occurs when the file is not found
        warningBox.setSize(100,200);
        warningBox.setTitle("Error!");
        JOptionPane.showMessageDialog(warningBox, "File was not found!", "Error!", JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    
    private void resetInteractions() {
      //Resets the interactions
      removeAll();
      add(openExistingPlan);
      add(openNewPlan);
      add(welcomeMessage);
    }
  }//End of start screen class
  
  //Main screen of the program
  private class MainScreen extends JPanel {
    //Initialize buttons for panel
    JLabel menuTitle;
    JButton addStudentButton;
    JButton arrangeTablesButton;
    JButton saveButton;
    JButton backButton;
    JButton displayInformationButton;
    JButton searchButton;
    JButton displayTablesButton;
    
    //Background image
    Image mainScreenBackground = Toolkit.getDefaultToolkit().createImage("MainScreen.jpg");
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(mainScreenBackground, 0, 0, this.getWidth(), this.getHeight() + 100, this);
    }
    
    /** 
     * MainScreen
     * Sets up main screen
     */
    MainScreen() {
      super();
      this.setLayout(null);
      
      menuTitle = new JLabel("Menu");
      menuTitle.setForeground(Color.WHITE);
      menuTitle.setFont(welcomeMessageFont);
      menuTitle.setBounds((int)(screenSize.getWidth()/2-150),100,300,100);
      
      //addStudent button
      addStudentButton = new JButton("Add Student");
      addStudentButton.setForeground(Color.WHITE);
      addStudentButton.setBackground(new Color(68,102,0));
      addStudentButton.setFont(generalButtonFont);
      addStudentButton.setBounds((int)(screenSize.getWidth()/2-150),250,300,50);
      //Adds a new student to the plan
      addStudentButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("StudentForm");
        }
      });
      
      //search Button
      searchButton = new JButton("Search");
      searchButton.setForeground(Color.WHITE);
      searchButton.setBackground(new Color(68,102,0));
      searchButton.setFont(generalButtonFont);
      searchButton.setBounds((int)(screenSize.getWidth()/2-150),310,300,50);
      //Searches for student
      searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("Search");
        }
      });
      
      //arrangeTables button
      arrangeTablesButton = new JButton("Arrange Tables");
      arrangeTablesButton.setForeground(Color.WHITE);
      arrangeTablesButton.setBackground(new Color(68,102,0));
      arrangeTablesButton.setFont(generalButtonFont);
      arrangeTablesButton.setBounds((int)(screenSize.getWidth()/2-150),370,300,50);
      //Arranges tables
      arrangeTablesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          listOfTables = alg.generateTables(masterListOfStudents, peoplePerTable);
          floorPlan.generateFloorPlan(listOfTables);
        }
      });
      
      //save button
      saveButton = new JButton("Save");
      saveButton.setForeground(Color.WHITE);
      saveButton.setBackground(new Color(68,102,0));
      saveButton.setFont(generalButtonFont);
      saveButton.setBounds((int)(screenSize.getWidth()/2-150),550,300,50);
      saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          saveData();
          hasSaved = true;
        }
      });
      
      //back button
      backButton = new JButton("Back");
      backButton.setForeground(Color.WHITE);
      backButton.setBackground(new Color(68,102,0));
      backButton.setFont(generalButtonFont);
      backButton.setBounds((int)(screenSize.getWidth()/2-150),610,300,50);
      backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          if (!hasSaved) {
            warningBox.setSize(100,200);
            int decision = JOptionPane.showConfirmDialog(warningBox, "You have unsaved information! Do you want to save?");
            if (decision == 0) {
              saveData();
              resetInformation();
              setScreen("StartScreen");
            } else if (decision == 1) {
              resetInformation();
              setScreen("StartScreen");
            } 
          }
        }
      });
      
      //displayTables button
      displayTablesButton = new JButton("Display Tables");
      displayTablesButton.setForeground(Color.WHITE);
      displayTablesButton.setBackground(new Color(68,102,0));
      displayTablesButton.setFont(generalButtonFont);
      displayTablesButton.setBounds((int)(screenSize.getWidth()/2-150),490,300,50);
      displayTablesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          try {
            floorPlan.displayFloorPlan();
          } catch (NullPointerException e) {
            warningBox.setSize(100, 200);
            JOptionPane.showMessageDialog(warningBox, "Arrangement of tables not done!", "Error!", JOptionPane.ERROR_MESSAGE);
          }
        }
      });
      
      //Information button
      displayInformationButton = new JButton("Display Event Information");
      displayInformationButton.setForeground(Color.WHITE);
      displayInformationButton.setBackground(new Color(68,102,0));
      displayInformationButton.setFont(generalButtonFont);
      displayInformationButton.setBounds((int)(screenSize.getWidth()/2-150),430,300,50);
      displayInformationButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("InformationScreen");
        }
      });
      
      //Add buttons to the frame
      add(menuTitle);
      add(addStudentButton);
      add(searchButton);
      add(arrangeTablesButton);
      add(displayInformationButton);
      add(displayTablesButton);
      add(saveButton);
      add(backButton);
    }
    
    /*
     *
     * Writes the information in the TicketingSystem class into a .txt file
     *
     */
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
        //Nothing happens here because the file is created anyway
      }
    }
  }//End of main screen class
  
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
    
    ActionListener newStudent;
    ActionListener updateStudent;
    ActionListener addAnotherButtonListener;
    ActionListener tempDisabled;
    
    Image studentFormBackground = Toolkit.getDefaultToolkit().createImage("StudentForm.png");
    @Override
    protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(studentFormBackground, 0,0,this.getWidth(),this.getHeight()+100,this);
    }
    
    /** 
     * StudentForm
     * Sets up student form panel
     */
    StudentForm() {
      super();  
      this.setLayout(null);
      //int col1=(int)(screenSize.getWidth())/4-firstNameLabel.getPreferredSize().width/2;
      
      firstNameLabel = new JLabel("First Name:");
      firstNameLabel.setForeground(Color.WHITE);
      firstNameLabel.setFont(generalButtonFont);
      firstNameLabel.setBounds((int)(screenSize.getWidth())/5,100,firstNameLabel.getPreferredSize().width,firstNameLabel.getPreferredSize().height);
      lastNameLabel = new JLabel("Last Name:");
      lastNameLabel.setForeground(Color.WHITE);
      lastNameLabel.setBounds((int)(screenSize.getWidth())/5,250,firstNameLabel.getPreferredSize().width,lastNameLabel.getPreferredSize().height);
      lastNameLabel.setFont(generalButtonFont);
      studentNumberLabel = new JLabel("Student Number:");
      studentNumberLabel.setForeground(Color.WHITE);
      studentNumberLabel.setFont(generalButtonFont);
      studentNumberLabel.setBounds((int)(screenSize.getWidth())/5,400,studentNumberLabel.getPreferredSize().width,studentNumberLabel.getPreferredSize().height);
      
      allergiesLabel = new JLabel("Dietary Restrictions:");
      allergiesLabel.setForeground(Color.WHITE);
      allergiesLabel.setFont(generalButtonFont);
      allergiesLabel.setBounds(((int)(screenSize.getWidth())/5)*3,450,allergiesLabel.getPreferredSize().width,allergiesLabel.getPreferredSize().height);
      friendPreferencesLabel = new JLabel("Friend Student Numbers:");
      friendPreferencesLabel.setForeground(Color.WHITE);
      friendPreferencesLabel.setFont(generalButtonFont);
      friendPreferencesLabel.setBounds(((int)(screenSize.getWidth())/5)*3,100,friendPreferencesLabel.getPreferredSize().width,friendPreferencesLabel.getPreferredSize().height);
      
      firstNameTextField = new JTextField();
      firstNameTextField.setBounds((int)(screenSize.getWidth())/5,150,300,25);
      lastNameTextField = new JTextField();
      lastNameTextField.setBounds((int)(screenSize.getWidth())/5,300,300,25);
      studentNumberTextField = new JTextField();
      studentNumberTextField.setBounds((int)(screenSize.getWidth())/5,450,300,25);
      listOfAllergies[0] = new JLabel("Vegetarian");
      listOfAllergies[1] = new JLabel("Vegan");
      listOfAllergies[2] = new JLabel("Lactose Intolerant");
      listOfAllergies[3] = new JLabel("Gluten-Free");
      listOfAllergies[4] = new JLabel("Halal");
      listOfAllergies[5] = new JLabel("Kosher");
      listOfAllergies[6] = new JLabel("Nut Allergy");
      listOfAllergies[7] = new JLabel("Peanut Allergy");
      int allergiesY = 500;
      for(int i=0;i<=7;i++){
        listOfAllergies[i].setForeground(Color.WHITE);
        listOfAllergies[i].setFont(generalButtonFont);
        listOfAllergies[i].setBounds(((int)(screenSize.getWidth())/5)*3+50,allergiesY,listOfAllergies[i].getPreferredSize().width,listOfAllergies[i].getPreferredSize().height);
        allergiesY+=30;
      }
      
      add(firstNameLabel);
      add(firstNameTextField);
      add(lastNameLabel);
      add(lastNameTextField);
      add(studentNumberLabel);
      add(allergiesLabel);
      add(studentNumberTextField);
      int checkBoxY=500;
      for (int i=0;i<=7;i++) {
        checkOfAllergies[i] = new JCheckBox();
        checkOfAllergies[i].setBounds(((int)(screenSize.getWidth())/5)*3,checkBoxY,checkOfAllergies[i].getPreferredSize().width,checkOfAllergies[i].getPreferredSize().height);
        add(checkOfAllergies[i]);
        add(listOfAllergies[i]);
        checkBoxY+=30;
      }
      add(friendPreferencesLabel);
      int yheight = 150;
      for (int i=0;i<9;i++) {
        friendsTextField[i] = new JTextField();
        //friendsTextField[i].setFont(generalButtonFont);
        friendsTextField[i].setBounds(((int)(screenSize.getWidth())/5)*3,yheight, 300,25);
        add(friendsTextField[i]);
        friendsTextField[i].getText();
        yheight+=30;
      }
      saveButton = new JButton("Save");
      saveButton.setFont(generalButtonFont);
      saveButton.setForeground(Color.WHITE);
      saveButton.setBackground(Color.BLACK);
      saveButton.setBounds((int)(screenSize.getWidth()/2-230),800,100,50);
      newStudent = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          boolean completed = saveStudent();
          if (completed) {
            setScreen("MainScreen");
            resetInteractions();
          } else {
            resetInteractions();
          }
        }
      };
      saveButton.addActionListener(newStudent);
      addAnotherButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          boolean completed = saveStudent();
          if (completed) {
            resetInteractions();
          } else {
            warningBox.setSize(100, 200);
            JOptionPane.showMessageDialog(warningBox, "Not all required fields were filled!", "Error!", JOptionPane.ERROR_MESSAGE);
          }
        }
      };
      tempDisabled = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          warningBox.setSize(100,200);
          JOptionPane.showMessageDialog(warningBox, "This button is temporarily disabled!", "Sorry!", 0);
        }
      };
      add(saveButton);
      addAnotherButton = new JButton("Add Another Student");
      addAnotherButton.setFont(generalButtonFont);
      addAnotherButton.setForeground(Color.WHITE);
      addAnotherButton.setBackground(Color.BLACK);
      addAnotherButton.setBounds((int)(screenSize.getWidth()/2-125),800,250,50);
      addAnotherButton.addActionListener(addAnotherButtonListener);
      add(addAnotherButton);
      backButton = new JButton("Back");
      backButton.setFont(generalButtonFont);
      backButton.setForeground(Color.WHITE);
      backButton.setBackground(Color.BLACK);
      backButton.setBounds((int)(screenSize.getWidth()/2+130),800,100,50);
      backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          addAnotherButton.removeActionListener(tempDisabled);
          addAnotherButton.removeActionListener(addAnotherButtonListener);
          addAnotherButton.addActionListener(addAnotherButtonListener);
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
      if (!firstName.equals("") && !lastName.equals("") && !studentNumber.equals("")) {
        masterListOfStudents.add(new Student(firstName, lastName, studentNumber, dietaryRestrictions, friendStudentNumbers));
        hasSaved = false;
        return true;
      } else {
        warningBox.setSize(100,200);
        JOptionPane.showMessageDialog(warningBox, "You left something blank!", "Error!", 0);
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
      saveButton.removeActionListener(updateStudent);
      saveButton.removeActionListener(newStudent);
      saveButton.addActionListener(newStudent);
    }
    
    public void modifyStudent(int arrayIndex) {
      addAnotherButton.removeActionListener(addAnotherButtonListener);
      addAnotherButton.addActionListener(tempDisabled);
      Student student = masterListOfStudents.get(arrayIndex);
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
          Student intermediate = makeStudent();
          if (!intermediate.equals(null)) {
            updateStudent(arrayIndex, intermediate);
          }
          resetInteractions();
          addAnotherButton.removeActionListener(tempDisabled);
          addAnotherButton.addActionListener(addAnotherButtonListener);
          setScreen("MainScreen"); 
        }
      };
      saveButton.removeActionListener(newStudent);
      saveButton.addActionListener(updateStudent);
      hasSaved = false;
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
        warningBox.setSize(100,200);
        JOptionPane.showMessageDialog(warningBox, "You left something blank", "Error!", JOptionPane.ERROR_MESSAGE);
        return null;
      }
    }
  }//End of student form class
  
  private class SearchScreen extends JPanel {
    ArrayList<Student> listOfResults;
    ArrayList<ArrayList> pageList;
    HashMap<Student, Integer> resultsMap;
    JLabel search,results;
    JTextField searchField;
    JButton searchButton;
    JButton backButton;
    
    //Background image
    Image searchScreenBackground = Toolkit.getDefaultToolkit().createImage("StudentForm.png");
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(searchScreenBackground, 0, 0, this.getWidth(), this.getHeight() + 100, this);
    }
    
    /** 
     * SearchScreen
     * Sets up search screen panel
     */
    SearchScreen(){
      super();
      this.setLayout(null);
      resultsMap = new HashMap();
      backButton = new JButton("Back");
      backButton.setFont(generalButtonFont);
      backButton.setForeground(Color.WHITE);
      backButton.setBackground(new Color(13,77,0));
      backButton.setBounds((int)(screenSize.getWidth()/2-100),150,100,50);
      backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("MainScreen");
          resetInteractions();
        }
      });
      add(backButton);
      search = new JLabel("Search by Name or Student Number");
      search.setFont(generalButtonFont);
      search.setForeground(Color.WHITE);
      search.setBackground(new Color(13,77,0));
      search.setBounds((int)(screenSize.getWidth()/2-search.getPreferredSize().width/2),50,search.getPreferredSize().width,search.getPreferredSize().height);
      results = new JLabel("Results");
      searchField = new JTextField();
      searchField.setBounds((int)(screenSize.getWidth()/2-150),100,300,25);
      searchButton = new JButton("Search");
      searchButton.setFont(generalButtonFont);
      searchButton.setForeground(Color.WHITE);
      searchButton.setBackground(new Color(13,77,0));
      searchButton.setBounds((int)(screenSize.getWidth()/2),150,100,50);
      searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          search();
          initializeInformation();
          display(0);
        }
      });
      add(search);
      add(searchField);
      add(searchButton);
      listOfResults = new ArrayList<>();
      pageList = new ArrayList<>();
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
            resultsMap.put(masterListOfStudents.get(i), i);
          } else if (masterListOfStudents.get(i).getLastName().equalsIgnoreCase(searchQuery)){
            listOfResults.add(masterListOfStudents.get(i));
            resultsMap.put(masterListOfStudents.get(i), i);
          } else if (masterListOfStudents.get(i).getStudentNumber().equalsIgnoreCase(searchQuery)){
            listOfResults.add(masterListOfStudents.get(i));
            resultsMap.put(masterListOfStudents.get(i), i);
          }
        }
      }
    }
    
    private void display(int page) {
      removeAll();
      add(search);
      add(searchField);
      add(searchButton);
      add(backButton);
      if (pageList.get(page).size() > 0) {
        int displayHeight = 250;
        for (int i = 0; i < pageList.get(page).size(); i++) {
          JLabel firstNameLabel = new JLabel(((Student)pageList.get(page).get(i)).getFirstName());
          JLabel lastNameLabel = new JLabel(((Student)pageList.get(page).get(i)).getLastName());
          JLabel studentNumberLabel = new JLabel(((Student)pageList.get(page).get(i)).getStudentNumber());
          JButton modifyThisStudentButton = new JButton("Modify");
          JButton removeStudent = new JButton("Remove");
          
          Student referencedStudent = ((Student)pageList.get(page).get(i));
          int arrayIndex = (int)resultsMap.get(referencedStudent);
          
          modifyThisStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              modifyStudent(arrayIndex);
              setScreen("StudentForm");
            }
          });
          firstNameLabel.setFont(generalButtonFont);
          firstNameLabel.setForeground(Color.WHITE);
          firstNameLabel.setBounds((int)(screenSize.getWidth()/2-300),displayHeight,400,30);          
          lastNameLabel.setFont(generalButtonFont);
          lastNameLabel.setForeground(Color.WHITE);
          lastNameLabel.setBounds((int)(screenSize.getWidth()/2-300),displayHeight+30,400,30);          
          studentNumberLabel.setFont(generalButtonFont);
          studentNumberLabel.setForeground(Color.WHITE);
          studentNumberLabel.setBounds((int)(screenSize.getWidth()/2-300),displayHeight+60,400,30);
          modifyThisStudentButton.setFont(generalButtonFont);
          modifyThisStudentButton.setForeground(Color.WHITE);
          modifyThisStudentButton.setBackground(new Color(13,77,0));
          modifyThisStudentButton.setBounds((int)(screenSize.getWidth()/2+100),displayHeight+20,125,50);
          removeStudent.setFont(generalButtonFont);
          removeStudent.setForeground(Color.WHITE);
          removeStudent.setBackground(new Color(13,77,0));
          removeStudent.setBounds((int)(screenSize.getWidth()/2+225),displayHeight+20,125,50);
          displayHeight+=100;
          if (displayHeight>750){
            displayHeight = 250;
          }
          int finalPage = page;
          int loopIndex = i;
          removeStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              masterListOfStudents.remove(arrayIndex);
              pageList.get(page).remove(loopIndex);
              initializeInformation();
              display(finalPage);
              repaintFrame();
            }
          });
          add(firstNameLabel);
          add(lastNameLabel);
          add(studentNumberLabel);
          add(modifyThisStudentButton);
          add(removeStudent);
          
          JButton previousPage, nextPage;
          previousPage = new JButton("Previous Page");
          previousPage.setFont(generalButtonFont);
          previousPage.setForeground(Color.WHITE);
          previousPage.setBackground(new Color(13,77,0));
          previousPage.setBounds((int)(screenSize.getWidth()/2-205),(int)(screenSize.getHeight()-175),200,50);
          int newPagePrev = page - 1;
          previousPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              if (newPagePrev >= 0) {
                display(newPagePrev);
                repaintFrame();
              } else {
                warningBox.setSize(100,200);
                JOptionPane.showMessageDialog(warningBox, "Page index out of bounds!","Error!", JOptionPane.ERROR_MESSAGE);
              }
            }
          });
          nextPage = new JButton("Next Page");
          nextPage.setFont(generalButtonFont);
          nextPage.setForeground(Color.WHITE);
          nextPage.setBackground(new Color(13,77,0));
          nextPage.setBounds((int)(screenSize.getWidth()/2+5),(int)(screenSize.getHeight()-175),200,50);
          int newPageNext = page + 1;
          nextPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              if (newPageNext < pageList.size()) {
                display(newPageNext);
                repaintFrame();
              } else {
                warningBox.setSize(100, 200);
                JOptionPane.showMessageDialog(warningBox, "Page index out of bounds!", "Error!", JOptionPane.ERROR_MESSAGE);
              }
            }
          });
          
          add(previousPage);
          add(nextPage);
        }
      } else {
        JLabel noResultsLabel = new JLabel("No results found!");
        noResultsLabel.setFont(generalButtonFont);
        noResultsLabel.setForeground(Color.WHITE);
        noResultsLabel.setBounds((int)(screenSize.getWidth()/2-200),160,400,30);
        add(noResultsLabel);
      }
      repaintFrame();
    }
    
    private void resetInteractions() {
      removeAll();
      searchField.setText("");
      listOfResults.clear();
      pageList.clear();
      resultsMap.clear();
      add(search);
      add(searchField);
      add(searchButton);
      add(backButton);
    }
    
    private void initializeInformation() {
      if (pageList.size() > 0) {
        pageList.clear();
      }
      for (int i=0; i<(int)Math.ceil((double)listOfResults.size()/6); i++) {
        ArrayList<Student> page = new ArrayList<>();
        for (int j=0; j<6; j++) {
          if ((i*6 + j) < listOfResults.size()) {
            page.add(listOfResults.get(i*6 + j));
          } else {
            j = 10;
          }
        }
        pageList.add(page);
      }
    }
  }//End of search screen class
  
  private class InformationScreen extends JPanel {
    JButton backButton;
    //Background image
    Image infoScreenBackground;
    ArrayList<ArrayList> pageList;
    
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(infoScreenBackground, 0, 0, this.getWidth(), this.getHeight() + 100, this);
    }
    
    /** 
     * InformationScreen
     * Sets up info screen panel
     */
    InformationScreen() {
      super();
      this.setLayout(null);
      backButton = new JButton("Back");
      backButton.setFont(generalButtonFont);
      backButton.setForeground(Color.WHITE);
      backButton.setBackground(new Color(13,77,0));
      backButton.setBounds((int)(screenSize.getWidth()/2-50),170,100,50);
      backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          setScreen("MainScreen");
        }
      });
      infoScreenBackground = Toolkit.getDefaultToolkit().createImage("StudentForm.png");
      pageList = new ArrayList<>();
    }
    
    private void displayInformation(int page) {
      resetScreen();
      add(backButton);
      
      JLabel eventNameLabel, numberOfPeoplePerTableLabel, numberOfTablesLabel;
      eventNameLabel = new JLabel("Event Name: " + eventName);
      eventNameLabel.setFont(generalButtonFont);
      eventNameLabel.setForeground(Color.WHITE);
      eventNameLabel.setBounds((int)(screenSize.getWidth()/2-eventNameLabel.getPreferredSize().width/2),50,eventNameLabel.getPreferredSize().width,eventNameLabel.getPreferredSize().height);
      numberOfPeoplePerTableLabel = new JLabel("Number of people per table: " + Integer.toString(peoplePerTable));
      numberOfPeoplePerTableLabel.setFont(generalButtonFont);
      numberOfPeoplePerTableLabel.setForeground(Color.WHITE);
      numberOfPeoplePerTableLabel.setBounds((int)(screenSize.getWidth()/2-numberOfPeoplePerTableLabel.getPreferredSize().width/2),90,numberOfPeoplePerTableLabel.getPreferredSize().width,numberOfPeoplePerTableLabel.getPreferredSize().height);
      numberOfTablesLabel = new JLabel("Number of tables: " + Integer.toString(numberOfTables));
      numberOfTablesLabel.setFont(generalButtonFont);
      numberOfTablesLabel.setForeground(Color.WHITE);
      numberOfTablesLabel.setBounds((int)(screenSize.getWidth()/2-numberOfTablesLabel.getPreferredSize().width/2),130,numberOfTablesLabel.getPreferredSize().width,numberOfTablesLabel.getPreferredSize().height);
      add(eventNameLabel);
      add(numberOfPeoplePerTableLabel);
      add(numberOfTablesLabel);
      
      JButton previousPage, nextPage;
      previousPage = new JButton("Previous Page");
      previousPage.setFont(generalButtonFont);
      previousPage.setForeground(Color.WHITE);
      previousPage.setBackground(new Color(13,77,0));
      previousPage.setBounds((int)(screenSize.getWidth()/2-205),(int)(screenSize.getHeight()-175),200,50);
      int newPagePrev = page - 1;
      previousPage.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          if (newPagePrev >= 0) {
            displayInformation(newPagePrev);
            repaintFrame();
          } else {
            warningBox.setSize(100,200);
            JOptionPane.showMessageDialog(warningBox, "Page index out of bounds!","Error!", JOptionPane.ERROR_MESSAGE);
          }
        }
      });
      nextPage = new JButton("Next Page");
      nextPage.setFont(generalButtonFont);
      nextPage.setForeground(Color.WHITE);
      nextPage.setBackground(new Color(13,77,0));
      nextPage.setBounds((int)(screenSize.getWidth()/2+5),(int)(screenSize.getHeight()-175),200,50);
      int newPageNext = page + 1;
      nextPage.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          if (newPageNext < pageList.size()) {
            displayInformation(newPageNext);
            repaintFrame();
          } else {
            warningBox.setSize(100, 200);
            JOptionPane.showMessageDialog(warningBox, "Page index out of bounds!", "Error!", JOptionPane.ERROR_MESSAGE);
          }
        }
      });
      
      add(previousPage);
      add(nextPage);
      
      int displayHeight = 250;
      if (pageList.size() > 0) {
        try {
          for (int i=0; i<pageList.get(page).size(); i++) {
            JLabel firstNameLabel = new JLabel(((Student)pageList.get(page).get(i)).getFirstName());
            JLabel lastNameLabel = new JLabel(((Student)pageList.get(page).get(i)).getLastName());
            JLabel studentNumberLabel = new JLabel(((Student)pageList.get(page).get(i)).getStudentNumber());
            JButton modifyThisStudentButton = new JButton("Modify");
            JButton removeStudent = new JButton("Remove");
            
            //Format
            firstNameLabel.setFont(generalButtonFont);
            firstNameLabel.setForeground(Color.WHITE);
            firstNameLabel.setBounds((int)(screenSize.getWidth()/2-300),displayHeight,400,30);          
            lastNameLabel.setFont(generalButtonFont);
            lastNameLabel.setForeground(Color.WHITE);
            lastNameLabel.setBounds((int)(screenSize.getWidth()/2-300),displayHeight+30,400,30);          
            studentNumberLabel.setFont(generalButtonFont);
            studentNumberLabel.setForeground(Color.WHITE);
            studentNumberLabel.setBounds((int)(screenSize.getWidth()/2-300),displayHeight+60,400,30);
            modifyThisStudentButton.setFont(generalButtonFont);
            modifyThisStudentButton.setForeground(Color.WHITE);
            modifyThisStudentButton.setBackground(new Color(13,77,0));
            modifyThisStudentButton.setBounds((int)(screenSize.getWidth()/2+100),displayHeight+20,125,50);
            removeStudent.setFont(generalButtonFont);
            removeStudent.setForeground(Color.WHITE);
            removeStudent.setBackground(new Color(13,77,0));
            removeStudent.setBounds((int)(screenSize.getWidth()/2+225),displayHeight+20,125,50);
            displayHeight+=100;
            if (displayHeight>750){
              displayHeight = 250;
            }
            int arrayIndex = page*6 + i;
            Student referencedStudent = masterListOfStudents.get(i);
            modifyThisStudentButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                modifyStudent(arrayIndex);
                setScreen("StudentForm");
              }
            });
            int finalPage = page;
            removeStudent.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                masterListOfStudents.remove(arrayIndex);
                initializeInformation();
                displayInformation(finalPage);
                repaintFrame();
              }
            });
            add(firstNameLabel);
            add(lastNameLabel);
            add(studentNumberLabel);
            add(modifyThisStudentButton);
            add(removeStudent);
          }   
        } catch (ArrayIndexOutOfBoundsException exception) {
          page-=1;
        }
      }
    }
   
    private void initializeInformation() {
      if (pageList.size() > 0) {
        pageList.clear();
      }
      for (int i=0; i<(int)Math.ceil((double)masterListOfStudents.size()/6); i++) {
        ArrayList<Student> page = new ArrayList<>();
        for (int j=0; j<6; j++) {
          if ((i*6 + j) < masterListOfStudents.size()) {
            page.add(masterListOfStudents.get(i*6 + j));
          } else {
            j = 10;
          }
        }
        pageList.add(page);
      }
    }
    
    private void resetScreen() {
      removeAll();
    }
  }//End of info screen class
}//End of class