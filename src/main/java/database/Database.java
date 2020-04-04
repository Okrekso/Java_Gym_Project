package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean execute(String query) {
        if(!isConnected()) return false;
        try {
            Connection con = this.getConnection();
            int result = con.createStatement().executeUpdate(query);
            con.commit();
            con.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ResultSet executeQuery(String query) {
        if(!isConnected()) return null;
        try {
            Connection con = this.getConnection();
            ResultSet result = con.createStatement().executeQuery(query);
//            con.close();
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    protected boolean insertIntoTable(String tableID, String dataTemplate, String data) {
        return execute(String.format("INSERT INTO %s(%s) VALUES(%s)", tableID, dataTemplate, data));
    }

    public List<IDBEntity> getFromEntityTable(DBEntity entity, String condition) throws SQLException {
        ResultSet set = executeQuery(String.format("SELECT * FROM %s WHERE %s", entity.getTableID(), condition));
        return entity.getListFromResultSet(set);
    }
    public List<IDBEntity> getFromEntityTable(DBEntity entity) throws SQLException {
        ResultSet set = executeQuery(String.format("SELECT * FROM %s", entity.getTableID()));
        return entity.getListFromResultSet(set);
    }

    public boolean updateTable(String tableID, String setVariables, String condition) {
        ResultSet res = executeQuery(String.format("UPDATE %s SET %s WHERE %s", tableID, setVariables, condition));
        return res==null;
    }
    public boolean updateTable(String tableID, String setVariables) {
        return execute(String.format("UPDATE %s SET %s", tableID, setVariables));
    }

    /**
     * @return is connection successfully
     * @throws SQLException mean that connection failed somehow
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, user, password);
    }

    public DBEntity getEmptyEntity(Class classType) {
        return this.getDBentities().stream().filter(dbEntity -> dbEntity.getClass() == classType)
                .collect(Collectors.toList()).get(0);
    }

    public abstract boolean isDBcreated();

    public abstract List<DBEntity>getDBentities();

    /**
     * @param entity element which'll be added to a table
     * @return is add process finished successfully
     */
    public abstract boolean addToTable(DBEntity entity);
}
