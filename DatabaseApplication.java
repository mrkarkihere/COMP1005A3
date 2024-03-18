import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseApplication{

    private String url, user, password;
    private Connection conn;

    public DatabaseApplication(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
        this.connect();
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
        app.disconnect();
    }
    
}