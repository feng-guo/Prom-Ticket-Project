import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Toolkit;

public class TicketingSystem extends JFrame{
    private String eventName;
    private int numberOfTables;
    private int peoplePerTable;
    private ArrayList<Student> masterListOfStudents;
    private MainScreen mainScreen;
    private StudentForm studentForm;

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public int getPeoplePerTable() {
        return peoplePerTable;
    }

    public void setPeoplePerTable(int peoplePerTable) {
        this.peoplePerTable = peoplePerTable;
    }

    public ArrayList<Student> getMasterListOfStudents() {
        return masterListOfStudents;
    }

    public void setMasterListOfStudents(ArrayList<Student> masterListOfStudents) {
        this.masterListOfStudents = masterListOfStudents;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public static void main(String[] args) {
        TicketingSystem ticketingSystem = new TicketingSystem();
    }

    TicketingSystem() {
     super("Prom Ticketing System");
     this.setVisible(true);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
     init();
     setScreen("MainScreen");

    }

    private void setScreen(String screen) {
        getContentPane().removeAll();
        if (screen.equals("StudentForm")) {
            getContentPane().add(studentForm);
        } else if (screen.equals("MainScreen")) {
            getContentPane().add(mainScreen);
        } else if (screen.equals("StartScreen")) {
            //build it
        } else if (screen.equals("Search")) {
            //build it
        }
    }

    private void init() {
        mainScreen = new MainScreen();
        studentForm = new StudentForm();
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
        JTextField firstNameTextField;
        JTextField lastNameTextField;
        JTextField studentNumberTextField;
        JTextField[] friendsTextField = new JTextField[9];

        StudentForm() {
            firstNameLabel = new JLabel("First Name");
            lastNameLabel = new JLabel("Last Name");
            studentNumberLabel = new JLabel("Student Number");
            allergiesLabel = new JLabel("Allergies");
            friendPreferencesLabel = new JLabel("Friend Student Numbers");
            firstNameTextField = new JTextField();
            lastNameTextField = new JTextField();
            studentNumberTextField = new JTextField();
            //JTextField[] friendsTextField = new JTextField[9];
            add(firstNameLabel);
            add(firstNameTextField);
            add(lastNameLabel);
            add(lastNameTextField);
        }
    }
}
