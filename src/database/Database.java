package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Database {
    protected String connectionURL;
    protected String user;
    protected String password;
    protected boolean driverFound=false;

    public Database(String connectionURL, String user, String password) {
        this.connectionURL = connectionURL;
        this.user = user;
        this.password = password;
        try {
            Class.forName("org.h2.Driver");
            this.driverFound = true;
        } catch (ClassNotFoundException e) {

        }
    }

    public boolean isConnected() {
        try {
            Connection con = DriverManager.getConnection(connectionURL, user, password);
            return true;
        } catch (SQLException e) {
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

    public boolean insertIntoTable(String tableID, String dataTemplate, String data) {
        ResultSet res = execute(String.format("INSERT INTO %s(%s) VALUES(%s)", tableID, dataTemplate, data));
        return res==null;
    }

    public boolean updateTable(String tableID, String setVariables, String condition) {
        ResultSet res = execute(String.format("UPDATE %s SET %s WHERE %s", tableID, setVariables, condition));
        return res==null;
    }
    public boolean updateTable(String tableID, String setVariables) {
        ResultSet res = execute(String.format("UPDATE %s SET %s", tableID, setVariables));
        return res==null;
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
}
