package database;

import java.sql.SQLException;
import java.util.List;

public interface IDBEntity {
//    List<IDBEntity> getEntities() throws SQLException;
    boolean delete();
    boolean update();
    /**
     * @return string variable of all fields of entity
     */
    String getVariables();
    /**
     * @return get column titles and types for query
     */
    String getColumns();
}
