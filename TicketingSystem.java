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

public class TicketingSystem extends JFrame{
    //Variable declaration
    private String eventName;
    private int numberOfTables;
    private int peoplePerTable;
    private ArrayList<Student> masterListOfStudents;
    private MainScreen mainScreen;
    private StudentForm studentForm;
    private StartScreen startScreen;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Font welcomeMessageFont = new Font("Vivaldi",Font.PLAIN,100);
    private Font generalButtonFont = new Font ("Bahnschrift", Font.PLAIN,20);

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
      JLabel welcomeMessage;
      JLabel eventNameLabel;
      JLabel numTablesLabel;
      JLabel peopleTablesLabel;  
      
      //Background image
      Image startScreenBackground = Toolkit.getDefaultToolkit().createImage("StartScreen.png");
      @Override
      protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(startScreenBackground, 0,0,this.getWidth(),this.getHeight()+100,this);
      }
     
      //Method run when start plan is clicked
      private void clickedStartPlan(){          
        //Done button
        JButton doneButton = new JButton("Submit");
        doneButton.setBounds((int)(screenSize.getWidth()/2-75),700,150,50);
        doneButton.setFont(generalButtonFont);
        doneButton.setForeground(Color.WHITE);
        doneButton.setBackground(new Color(38,77,0));
        
        //Remove start buttons
        remove(openExistingPlan);
        remove(openNewPlan);
        remove(welcomeMessage);
        
        //Create labels
        eventNameLabel = new JLabel("Please enter the name of the event below:");
        eventNameLabel.setBounds((int)(screenSize.getWidth()/2-eventNameLabel.getPreferredSize().width/2),200,eventNameLabel.getPreferredSize().width,eventNameLabel.getPreferredSize().height);
        eventNameLabel.setFont(generalButtonFont);
        eventNameLabel.setForeground(Color.WHITE);
        numTablesLabel = new JLabel("How many tables are at the event venue?");
        numTablesLabel.setBounds((int)(screenSize.getWidth()/2-numTablesLabel.getPreferredSize().width/2),350,numTablesLabel.getPreferredSize().width,numTablesLabel.getPreferredSize().height);
        numTablesLabel.setFont(generalButtonFont);
        numTablesLabel.setForeground(Color.WHITE);
        peopleTablesLabel = new JLabel("How many seats are available at each table?");
        peopleTablesLabel.setBounds((int)(screenSize.getWidth()/2-peopleTablesLabel.getPreferredSize().width/2),500,peopleTablesLabel.getPreferredSize().width,peopleTablesLabel.getPreferredSize().height);
        peopleTablesLabel.setFont(generalButtonFont);
        peopleTablesLabel.setForeground(Color.WHITE);
        
        //Create fields
        eventNameTextField = new JTextField();
        eventNameTextField.setBounds((int)(screenSize.getWidth()/2-150),250,300,25);
        numTablesTextField = new JTextField();
        numTablesTextField.setBounds((int)(screenSize.getWidth()/2-150),400,300,25);
        peopleTablesTextField = new JTextField();
        peopleTablesTextField.setBounds((int)(screenSize.getWidth()/2-150),550,300,25);
        
        //Add components to panel
        add(eventNameTextField);
        add(eventNameLabel);
        add(numTablesTextField);
        add(numTablesLabel);
        add(peopleTablesTextField);
        add(peopleTablesLabel);
        add(doneButton);
        
        //After user enters info //Add more error checking
        doneButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
//            if(eventNameTextField.getText()==null || numTablesTextField.getText()==null || peopleTablesTextField.getText()==null){
//              JLabel errorMessage = new JLabel ("Please fill out all fields");
//              errorMessage.setFont(generalButtonFont);
//              errorMessage.setForeground(Color.WHITE);
//              errorMessage.setBounds((int)(screenSize.getWidth()/2-errorMessage.getPreferredSize().width/2),650,errorMessage.getPreferredSize().width,errorMessage.getPreferredSize().height);
//            }else{
            //Store user inputs
            eventName = eventNameTextField.getText();
            numberOfTables = Integer.parseInt(numTablesTextField.getText());
            peoplePerTable = Integer.parseInt(peopleTablesTextField.getText());
            setScreen("MainScreen");
          //  }
          }
        });
        repaint();
      }
      
      StartScreen(){
        super();
        this.setLayout(null);

        //Welcome message
        welcomeMessage = new JLabel("Prom Ticketing System");
        welcomeMessage.setFont(welcomeMessageFont);
        welcomeMessage.setBounds((int)(screenSize.getWidth()/2-welcomeMessage.getPreferredSize().width/2),10,welcomeMessage.getPreferredSize().width,welcomeMessage.getPreferredSize().width);
        welcomeMessage.setForeground(new Color(238,255,230));
        //openExistingPlanButton
        openExistingPlan = new JButton("Open Existing Plan");
        openExistingPlan.setBounds((int)(screenSize.getWidth()/2-225), (int)(screenSize.getHeight()/2+25),200,50);
        openExistingPlan.setFont(generalButtonFont);
        openExistingPlan.setBackground(new Color(38,77,0));
        openExistingPlan.setForeground(new Color(255,255,255));
        openExistingPlan.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            //Open existing plan
          }
        });
        openNewPlan = new JButton ("Start New Plan");
        openNewPlan.setBounds((int)(screenSize.getWidth()/2+25), (int)(screenSize.getHeight()/2+25),200,50);
        openNewPlan.setFont(generalButtonFont);
        openNewPlan.setBackground(new Color(38,77,0));
        openNewPlan.setForeground(new Color(255,255,255));
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
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    saveData();
                }
            });
            backButton = new JButton("Back");
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
            friendsTextField[i].setFont(generalButtonFont);
            friendsTextField[i].setForeground(Color.WHITE);
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
}