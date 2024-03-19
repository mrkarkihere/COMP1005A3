import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DatabaseApplication class represents a simple application
 * that interacts with a PostgreSQL database to manage student records.
 * It provides methods to retrieve, add, update, and delete student records
 * from the database.
 * 
 * @author Arun Karki
 * @version March 18, 2024
 */

public class DatabaseApplication{

    /**
     * The URL of the PostgreSQL database.
     */
    private String url;

    /**
     * The username used to connect to the PostgreSQL database.
     */
    private String user;

    /**
     * The password used to connect to the PostgreSQL database.
     */
    private String password;

    /**
     * The connection to the PostgreSQL database.
     */
    private Connection conn;

    /**
     * Constructs a DatabaseApplication object with the specified
     * URL, username, and password, and establishes a connection to
     * the PostgreSQL database.
     *
     * @param url      the URL of the PostgreSQL database
     * @param user     the username used to connect to the database
     * @param password the password used to connect to the database
     */
    public DatabaseApplication(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        this.connect(); // etablish connection to database
    }

    /**
     * Retrieves all student records from the database and prints them to the console.
     *
     * @throws SQLException if a database access error occurs
     */
    public void getAllStudents() throws SQLException {

        // query to select all columns from table
        String query = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);

        // loop through every column and print out each value
        while(result.next()){
            System.out.println(
                result.getInt("student_id") + "\t" +
                result.getString("first_name") + "\t" +
                result.getString("last_name") + "\t" +
                result.getString("email") + "\t" +
                result.getDate("enrollment_date")
            );
        }
        result.close();
        stmt.close();
    }

    /**
     * Adds a new student record to the database with the specified details.
     *
     * @param firstName      the first name of the student
     * @param lastName       the last name of the student
     * @param email          the email address of the student
     * @param enrollmentDate the enrollment date of the student
     * @throws SQLException if a database access error occurs
     */
    public void addStudent(String firstName, String lastName, String email, String enrollmentDate) throws SQLException{

        // query insert A, B, C, D attributes to table
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(query);
        // insert to corresponding ? in the query
        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, email);
        pstmt.setDate(4, Date.valueOf(enrollmentDate));

        int result = pstmt.executeUpdate();

        // something was inserted
        if(result > 0){
            System.out.println("A student was inserted successfully!");
        }else{
            System.out.println("Failure to insert new student.");
        }
        pstmt.close();
    }

    /**
     * Updates the email address of the student with the specified ID in the database.
     *
     * @param student_id the ID of the student to update
     * @param new_email  the new email address for the student
     * @throws SQLException if a database access error occurs
     */
    public void updateStudentEmail(int student_id, String new_email) throws SQLException {

        // query to update email with student id
        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // replace corresponding ? in query
        pstmt.setString(1, new_email);
        pstmt.setInt(2, student_id);

        int result = pstmt.executeUpdate();

        if(result > 0){
            System.out.println("student_" + student_id + "'s email was updated successfully!");
        }else{
            System.out.println("No student found with ID " + student_id);
        }

        pstmt.close();
    }

    /**
     * Deletes the student record with the specified ID from the database.
     *
     * @param student_id the ID of the student to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteStudent(int student_id) throws SQLException{

        // query to delete student_id column
        String query = "DELETE FROM students WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);

        // replace ? in the query with student_id
        pstmt.setInt(1, student_id);

        int result = pstmt.executeUpdate();

        if (result > 0) {
            System.out.println("Student with ID " + student_id + " deleted.");
        } else {
            System.out.println("No student found with ID " + student_id);
        }
        pstmt.close();
    }

    /**
     * Establishes a connection to the PostgreSQL database using the specified
     * URL, username, and password.
     */
    public void connect(){
        try {
            // load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            
            // connect to the database
            this.conn = DriverManager.getConnection(url, user, password);
            
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the PostgreSQL database.
     */
    public void disconnect(){
        try {
            this.conn.close();
            System.out.println("Disconnected from PostgreSQL successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method serves as a test bed for the DatabaseApplication class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Assignment3";
        String user = "postgres";
        String password = "test"; 
    
        DatabaseApplication app = new DatabaseApplication(url, user, password);

        // testing
        try {
            app.getAllStudents();
            app.addStudent("Arun", "Karki", "ARUNKARKI@cmail.carleton.ca", "2024-03-18");
            app.updateStudentEmail(1, "john.doe@example.com@example.com");
            app.deleteStudent(11);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        app.disconnect();
    }
    
}