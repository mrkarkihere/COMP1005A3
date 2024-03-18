import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseApplication{

    private String url, user, password;
    private Connection conn;

    public DatabaseApplication(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        this.connect();
    }

    public void getAllStudents() throws SQLException {
        String query = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);

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

    public void addStudent(String firstName, String lastName, String email, String enrollmentDate) throws SQLException{

        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, firstName);
        pstmt.setString(2, lastName);
        pstmt.setString(3, email);
        pstmt.setDate(4, Date.valueOf(enrollmentDate));

        int result = pstmt.executeUpdate();

        if(result > 1){
            System.out.println("A student was inserted successfully!");
        }
        pstmt.close();
    }

    public void updateStudentEmail(int student_id, String new_email) throws SQLException {

        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);

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

    public void deleteStudent(int student_id) throws SQLException{

        String query = "DELETE FROM students WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.setInt(1, student_id);

        int result = pstmt.executeUpdate();

        if (result > 0) {
            System.out.println("Student with ID " + student_id + " deleted.");
        } else {
            System.out.println("No student found with ID " + student_id);
        }
        pstmt.close();
    }

    public void connect(){
        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            
            // Connect to the database
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

    public void disconnect(){
        try {
            this.conn.close();
            System.out.println("Disconnected from PostgreSQL successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Assignment3";
        String user = "postgres";
        String password = "test"; 
    
        DatabaseApplication app = new DatabaseApplication(url, user, password);
        // testing
        try {
            //app.getAllStudents();
            //app.addStudent("Arun", "Karki", "ARUNKARKI@cmail.carleton.ca", "2024-03-18");
            //app.updateStudentEmail(1, "john.doe@example.com");
            app.deleteStudent(8);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        app.disconnect();
    }
    
}