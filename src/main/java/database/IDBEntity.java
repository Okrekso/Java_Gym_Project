package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface IDBEntity {
//    List<IDBEntity> getEntities() throws SQLException;
    default boolean delete() {
        return this.getDatabase().deleteFromTable(this.getTableID(), this.getEntityID());
    }

    Database getDatabase();

    boolean update();

    Integer getEntityIDValue();
    DBValue getEntityID();
    String getTableID();

    /**
     * @param set if true returned variables will be in format Param1='param1' instead of 'param1'
     * @return string variable of all fields of entity
     */
    String getVariables(boolean set);

    /**
     * @return variables as list of dbvalues
     */
    List<DBValue> getVariables();

    default List<DBValue> getVariablesWithID() {
        List<DBValue> variables = new ArrayList<>(getVariables());
        variables.add(0, getEntityID());
        return variables;
    }
    /**
     * @return get column titles and types for query
     * @param initialization if it's true than column should be returned for table initialization else just names
     */
    String getColumns(boolean initialization, boolean withID);

    /**
     * @return display value which represent the most relevant part of entity
     */
    String getDisplayValue();
    List<IDBEntity> getListFromResultSet(ResultSet resultSet) throws SQLException;
}
