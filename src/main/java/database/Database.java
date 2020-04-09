package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
    public boolean executeUpdate(String query) {
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
        return executeUpdate(String.format("INSERT INTO %s(%s) VALUES(%s)", tableID, dataTemplate, data));
    }


    public List<DBEntity> getFromEntityTable(IDBEntityFactory factory) throws SQLException, ParseException {
        DBEntity entity = factory.create();
        ResultSet set = executeQuery(String.format("SELECT * FROM %s", entity.getTableID()));
        return entity.getListFromResultSet(set, factory);
    }

    public List<DBEntity> getFromEntityTable(IDBEntityFactory factory, String condition) throws SQLException, ParseException {
        DBEntity entity = factory.create();
        ResultSet set = executeQuery(String.format("SELECT * FROM %s WHERE %s", entity.getTableID(), condition));
        return entity.getListFromResultSet(set, factory);
    }

    public List<DBEntity> getFromEntityTableById(IDBEntityFactory factory, Integer id) throws SQLException, ParseException {
        return getFromEntityTable(factory, String.format("%s=%s", factory.create().getEntityID().getTitle(), id));
    }

    public boolean updateTable(String tableID, String setVariables, String condition) {
        ResultSet res = executeQuery(String.format("UPDATE %s SET %s WHERE %s", tableID, setVariables, condition));
        return res==null;
    }
    public boolean updateTable(String tableID, String setVariables) {
        return executeUpdate(String.format("UPDATE %s SET %s", tableID, setVariables));
    }

    public boolean deleteFromTable(String tableID, DBValue idValue) {
        return executeUpdate(String.format("DELETE FROM %s WHERE %s=%s", tableID, idValue.getTitle(), idValue.getValue()));
    }

    /**
     * @return is connection successfully
     * @throws SQLException mean that connection failed somehow
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, user, password);
    }

    public DBEntity getEmptyEntity(String tableID) {
        List<DBEntity> list = this.getDBEntities();
        return this.getDBEntities()
                .stream()
                .filter(dbEntity-> dbEntity.getTableID().toLowerCase().equals(tableID.toLowerCase()))
                .collect(Collectors.toList()).get(0);
    }

    public abstract boolean isDBcreated();

    public final List<DBEntity> getDBEntities() {
        return this.getDBEntityFactories().stream()
                .map(idbEntityFactory -> idbEntityFactory.create())
                .collect(Collectors.toList());
    }

    public abstract List<IDBEntityFactory> getDBEntityFactories();

    public abstract boolean build();

    /**
     * @param entity element which'll be added to a table
     * @return is add process finished successfully
     */
    public abstract boolean addToTable(DBEntity entity);
}
