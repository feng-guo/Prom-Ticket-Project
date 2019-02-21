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
     SeatingAlg alg = new SeatingAlg();
     FloorPlan floorPlan = new floorPlan();
     this.setVisible(true);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setSize((int)(screenSize.getWidth()),(int)(screenSize.getHeight()));
     init();
     masterListOfStudents = new ArrayList<>();
     setScreen("StartScreen");
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
        } else if (screen.equals("InformationScreen")) {
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
        informationScreen = new InformationScreen();
        searchScreen = new SearchScreen();
    }

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

    private void modifyStudent(int arrayIndex, Student student){
        studentForm.modifyStudent(arrayIndex, student);
    }
    
    private class StartScreen extends JPanel {
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

            //After user enters info //Add more error checking
            doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
//            if(eventNameTextField.getText()==null || numTablesTextField.getText()==null || peopleTablesTextField.getText()==null){
//              JLabel errorMessage = new JLabel ("Please fill out all fields");
//              errorMessage.setFont(generalButtonFont);
//              errorMessage.setForeground(Color.WHITE);
//              errorMessage.setBounds((int)(screenSize.getWidth()/2-errorMessage.getPreferredSize().width/2),650,errorMessage.getPreferredSize().width,errorMessage.getPreferredSize().height);
//            }else{}
                    //Store user inputs
                    try {
                        eventName = eventNameTextField.getText();
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
            backToStartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                  setScreen("StartScreen");
                  //resetInformation();
                }
            });
            repaint();
        }

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
            openExistingPlan.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    openExistingPlan();
                    System.out.println("Clicked");
                    repaintFrame();
                }
            });
            openNewPlan = new JButton("Start New Plan");
            openNewPlan.setBounds((int) (screenSize.getWidth() / 2 + 25), (int) (screenSize.getHeight() / 2 + 25), 200, 50);
            openNewPlan.setFont(generalButtonFont);
            openNewPlan.setBackground(new Color(38, 77, 0));
            openNewPlan.setForeground(new Color(255, 255, 255));
            openNewPlan.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    clickedStartPlan();
                    repaint();
                }
            });
            add(openExistingPlan);
            add(openNewPlan);
            add(welcomeMessage);
        }

        private void openExistingPlan() {
            remove(openExistingPlan);
            remove(openNewPlan);
            remove(welcomeMessage);
            //Stuff to setup the new plan
            System.out.println("Why");
            JLabel planNamePrompt = new JLabel("What is the name of your file?");
            planNamePrompt.setFont(generalButtonFont);
            planNamePrompt.setForeground(Color.WHITE);
            planNamePrompt.setBounds((int)(screenSize.getWidth()/2-planNamePrompt.getPreferredSize().width/2),100,planNamePrompt.getPreferredSize().width,planNamePrompt.getPreferredSize().height);
            JTextField planNameTextField = new JTextField();
            planNameTextField.setBounds((int)(screenSize.getWidth()/2-150),150,300,25);
            JButton backButton = new JButton("Back");
            backButton.setFont(generalButtonFont);
            backButton.setForeground(Color.WHITE);
            backButton.setBackground(new Color(0,77,13));
            backButton.setBounds((int)(screenSize.getWidth()/2-100),200,100,50);
            JButton okayButton = new JButton("Load");
            okayButton.setFont(generalButtonFont);
            okayButton.setForeground(Color.WHITE);
            okayButton.setBackground(new Color(0,77,13));
            okayButton.setBounds((int)(screenSize.getWidth()/2),200,100,50);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    resetInformation();
                }
            });

            add(planNamePrompt);
            add(planNameTextField);
            okayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String nameOfFile = planNameTextField.getText();
                    if (nameOfFile.length() > 4) {
                        if (!nameOfFile.substring(nameOfFile.length() - 4, nameOfFile.length()).equals(".txt")) {
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
            setVisible(true);
            repaint();
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
                        String add = "";
                        for (int i = 0; i < tempRestrictions.length(); i++) {
                            if (!tempRestrictions.substring(i, i + 1).equals(",")) {
                                add = add + tempRestrictions.substring(i, i + 1);
                            } else {
                                restrictions.add(add);
                                add = "";
                            }
                        }
                        add = "";
                        for (int i = 0; i < tempFriends.length(); i++) {
                            if (!tempFriends.substring(i, i + 1).equals(",")) {
                                add = add + tempFriends.substring(i, i + 1);
                            } else {
                                friends.add(add);
                                add = "";
                            }
                        }
                        masterListOfStudents.add(new Student(firstName, lastName, studentNumber, restrictions, friends));
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Oopsies Owu. we did a fucky wucky!!");
            }
        }
    }

    
    private class MainScreen extends JPanel {
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
        
        MainScreen() {
            super();
            this.setLayout(null);
            addStudentButton = new JButton("Add Student");
            addStudentButton.setForeground(Color.WHITE);
            addStudentButton.setBackground(new Color(68,102,0));
            addStudentButton.setFont(generalButtonFont);
            addStudentButton.setBounds((int)(screenSize.getWidth()-350),200,300,50);
            addStudentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("StudentForm");
                }
            });
            searchButton = new JButton("Search");
            searchButton.setForeground(Color.WHITE);
            searchButton.setBackground(new Color(68,102,0));
            searchButton.setFont(generalButtonFont);
            searchButton.setBounds((int)(screenSize.getWidth()-350),260,300,50);
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("Search");
                }
            });
            arrangeTablesButton = new JButton("Arrange Tables");
            arrangeTablesButton.setForeground(Color.WHITE);
            arrangeTablesButton.setBackground(new Color(68,102,0));
            arrangeTablesButton.setFont(generalButtonFont);
            arrangeTablesButton.setBounds((int)(screenSize.getWidth()-350),320,300,50);
            arrangeTablesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    listOfTables = alg.generateTables(masterListOfStudents, peoplePerTable);
                    floorPlan.generateFloorPlan(listOfTables);
                }
            });
            saveButton = new JButton("Save");
            saveButton.setForeground(Color.WHITE);
            saveButton.setBackground(new Color(68,102,0));
            saveButton.setFont(generalButtonFont);
            saveButton.setBounds((int)(screenSize.getWidth()-350),500,300,50);
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    saveData();
                }
            });
            backButton = new JButton("Back");
            backButton.setForeground(Color.WHITE);
            backButton.setBackground(new Color(68,102,0));
            backButton.setFont(generalButtonFont);
            backButton.setBounds((int)(screenSize.getWidth()-350),560,300,50);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                  setScreen("StartScreen");
                  resetInformation();
                }
            });
            displayTablesButton = new JButton("Display Tables");
            displayTablesButton.setForeground(Color.WHITE);
            displayTablesButton.setBackground(new Color(68,102,0));
            displayTablesButton.setFont(generalButtonFont);
            displayTablesButton.setBounds((int)(screenSize.getWidth()-350),440,300,50);
            displayTablesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    floorPlan.displayFloorPlan(listOfTables);
                }
            });
            displayInformationButton = new JButton("Display Event Information");
            displayInformationButton.setForeground(Color.WHITE);
            displayInformationButton.setBackground(new Color(68,102,0));
            displayInformationButton.setFont(generalButtonFont);
            displayInformationButton.setBounds((int)(screenSize.getWidth()-350),380,300,50);
            displayInformationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setScreen("InformationScreen");
                }
            });
            add(addStudentButton);
            add(searchButton);
            add(arrangeTablesButton);
            add(displayInformationButton);
            add(displayTablesButton);
            add(saveButton);
            add(backButton);
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
        JButton saveButton;
        JButton addAnotherButton;
        JButton backButton;

        ActionListener newStudent;
        ActionListener updateStudent;
        
        Image studentFormBackground = Toolkit.getDefaultToolkit().createImage("StudentForm.png");
        @Override
        protected void paintComponent(Graphics g){
          super.paintComponent(g);
          g.drawImage(studentFormBackground, 0,0,this.getWidth(),this.getHeight()+100,this);
        }
        
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
          lastNameLabel.setBounds((int)(screenSize.getWidth())/5,250,lastNameLabel.getPreferredSize().width,lastNameLabel.getPreferredSize().height);
          lastNameLabel.setFont(generalButtonFont);
          studentNumberLabel = new JLabel("Student Number:");
          studentNumberLabel.setForeground(Color.WHITE);
          studentNumberLabel.setFont(generalButtonFont);
          studentNumberLabel.setBounds((int)(screenSize.getWidth())/5,400,studentNumberLabel.getPreferredSize().width,studentNumberLabel.getPreferredSize().height);
          
          allergiesLabel = new JLabel("Allergies");
          allergiesLabel.setForeground(Color.WHITE);
          allergiesLabel.setFont(generalButtonFont);
          allergiesLabel.setBounds(((int)(screenSize.getWidth())/5)*3,450,allergiesLabel.getPreferredSize().width,allergiesLabel.getPreferredSize().height);
          friendPreferencesLabel = new JLabel("Friend Student Numbers");
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
                  }
              }
          };
          saveButton.addActionListener(newStudent);
          add(saveButton);
          
          addAnotherButton = new JButton("Add Another Student");
          addAnotherButton.setFont(generalButtonFont);
          addAnotherButton.setForeground(Color.WHITE);
          addAnotherButton.setBackground(Color.BLACK);
          addAnotherButton.setBounds((int)(screenSize.getWidth()/2-125),800,250,50);
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
            backButton.setFont(generalButtonFont);
            backButton.setForeground(Color.WHITE);
            backButton.setBackground(Color.BLACK);
            backButton.setBounds((int)(screenSize.getWidth()/2+130),800,100,50);
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
                    saveButton.addActionListener(newStudent);
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
        
        //Background image
        Image searchScreenBackground = Toolkit.getDefaultToolkit().createImage("StudentForm.png");

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(searchScreenBackground, 0, 0, this.getWidth(), this.getHeight() + 100, this);
        }

        SearchScreen(){
          super();
          this.setLayout(null);
          backButton = new JButton("Back");
          backButton.setFont(generalButtonFont);
          backButton.setForeground(Color.WHITE);
          backButton.setBackground(new Color(13,77,0));
          backButton.setBounds((int)(screenSize.getWidth()/2-100),150,100,50);
          backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              setScreen("MainScreen");
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
          searchField.setFont(generalButtonFont);
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
        //Background image
        Image infoScreenBackground = Toolkit.getDefaultToolkit().createImage("StudentForm.png");
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(infoScreenBackground, 0, 0, this.getWidth(), this.getHeight() + 100, this);
        }
        InformationScreen() {
            super();
            this.setLayout(null);
            backButton = new JButton("Back");
            backButton.setFont(generalButtonFont);
            backButton.setForeground(Color.WHITE);
            backButton.setBackground(new Color(13,77,0));
            backButton.setBounds((int)(screenSize.getWidth()/2-50),150,100,50);
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