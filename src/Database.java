import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String connectionURL;
    private String user;
    private String password;

    public Database(String connectionURL, String user, String password) {
        this.connectionURL = connectionURL;
        this.user = user;
        this.password = password;
    }

    public boolean connect() {
        try {
            Class.forName("org.h2.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
