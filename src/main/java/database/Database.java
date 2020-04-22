package database;

import logic.gym.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlets.dbOps.DBCreationSubmitServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Database {
    protected String connectionURL;
    protected String user;
    protected String password;
    protected boolean driverFound=false;
    protected static final Logger log = LogManager.getLogger(DBCreationSubmitServlet.class);

    public Database(String connectionURL, String user, String password) {
        this.connectionURL = connectionURL;
        this.user = user;
        this.password = password;
        try {
            Class.forName("org.h2.Driver");
            this.driverFound = true;
        } catch (ClassNotFoundException e) {
            log.warn(e);
        }
    }

    public boolean isConnected() {
        try {
            Connection con = DriverManager.getConnection(connectionURL, user, password);
            return true;
        } catch (SQLException e) {
            log.warn(e);
            log.info("database isn't connected");
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
            log.error(e);
            return false;
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if(!isConnected()) return null;
            Connection con = this.getConnection();
            ResultSet result = con.createStatement().executeQuery(query);
            return result;
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
        ResultSet res = null;
        try {
            res = executeQuery(String.format("UPDATE %s SET %s WHERE %s", tableID, setVariables, condition));
        } catch (SQLException e) {
            log.error("error occupied while updating table: ", e);
        }
        return res==null;
    }
    public boolean updateTable(String tableID, String setVariables) {
        return executeUpdate(String.format("UPDATE %s SET %s", tableID, setVariables));
    }

    public boolean deleteFromTable(String tableID, DBValue idValue) {
        return executeUpdate(String.format("DELETE FROM %s WHERE %s=%s", tableID, idValue.getTitle(), idValue.getValue()));
    }

    public IDBEntityFactory getFactoryByTableName(String tableID) {
        List<IDBEntityFactory> factories = getDBEntityFactories().stream()
                .filter(factory -> factory.create().getTableID().toLowerCase().equals(tableID.toLowerCase()))
                .collect(Collectors.toList());
        if(factories.size()>0)
            return factories.get(0);
        else
            return null;
    }

    public String getTableCreationQuery(DBEntity entity) {
        return "CREATE TABLE "+entity.getTableID()+"("+entity.getColumns(true, true)+")";
    }

    public boolean build() {
        List<String> queries = getDBEntities().stream()
                .map(dbEntity -> getTableCreationQuery(dbEntity))
                .collect(Collectors.toList());
        for(String query : queries) {
            executeUpdate(query);
        }
        return isDBcreated();
    }

    public boolean dropCurrentDB() {
        List<String> tableIDs = getDBEntities().stream()
                .sorted(
                        (e1, e2) -> Boolean.compare(e2.hasForeignKeys(), e1.hasForeignKeys())
                )
                .map(dbEntity -> dbEntity.tableID).collect(Collectors.toList());
        for(String tableID : tableIDs) {
            if(!executeUpdate("DROP TABLE " + tableID))
                return false;
        }
        return true;
    }

    /**
     * @return is connection successfully
     * @throws SQLException mean that connection failed somehow
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, user, password);
    }

    public DBEntity getEmptyEntity(String tableID) {
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

    /**
     * @param entity element which'll be added to a table
     * @return is add process finished successfully
     */
    public final DBEntity addToTable(DBEntity entity) {
        this.insertIntoTable(entity.getTableID(), entity.getColumns(false, false),
                entity.getVariables(false));
        try {
            List<DBEntity> entities = getFromEntityTable(entity.getFactory());
            return entities.get(entities.size()-1);
        } catch (ParseException | SQLException e) {
            log.error(e);
            return null;
        }
    }
}
