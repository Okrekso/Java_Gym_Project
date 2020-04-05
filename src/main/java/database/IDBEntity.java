package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IDBEntity {
//    List<IDBEntity> getEntities() throws SQLException;
    boolean delete();
    boolean update();

    Integer getEntityID();

    /**
     * @param set if true returned variables will be in format Param1='param1' instead of 'param1'
     * @return string variable of all fields of entity
     */
    String getVariables(boolean set);

    /**
     * @return variables as list of dbvalues
     */
    List<DBValue> getVariables();
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
