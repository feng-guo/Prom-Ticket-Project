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
    searchButton = new JButton("Search)";
    arrangeTablesButton = new JButton("Arrange Tables");
    saveButton = new JButton("Save");
    backButton = new JButton("Back");
    displayTablesButton = new JButton("Display Tables");