import javax.swing.*;
import java.util.ArrayList;
import java.awt.Toolkit;

public class TicketingSystem extends JFrame{
    private String eventName;
    private int numberOfTables;
    private int peoplePerTable;
    private ArrayList<Student> masterListOfStudents;

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
     

    }
}
