package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public abstract class Database {
    protected String connectionURL;
    protected String user;
    protected String password;

    public Database(String connectionURL, String user, String password) {
        this.connectionURL = connectionURL;
        this.user = user;
        this.password = password;
    }

    public boolean isConnected() {
        try {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection(connectionURL, user, password);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param query query to execute on database
     * @return is query execution was successful, returns ResultSet
     */
    public ResultSet execute(String query) {
        try {
            Connection con = this.getConnection();
            ResultSet result = con.createStatement().executeQuery(query);
            con.close();
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * @return is connection successfully
     * @throws SQLException mean that connection failed somehow
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, user, password);
    }

    /**
     * @param entity element which'll be added to a table
     * @return is add process finished successfully
     */
    public abstract boolean addToTable(DBEntity entity);

    /**
     * @param entity element which'll be base for a table to create
     * @return is creation successfully
     */
    public abstract boolean createTableFromEntity(DBEntity entity);
}
