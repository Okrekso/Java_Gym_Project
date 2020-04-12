package database;

import java.io.ObjectOutput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IDBEntity {
//    List<IDBEntity> getEntities() throws SQLException;
    default boolean delete() {
        return this.getDatabase().deleteFromTable(this.getTableID(), this.getEntityID());
    }

    Database getDatabase();

    Integer getEntityIDValue();
    DBValue getEntityID();
    String getTableID();
    IDBEntityFactory getFactory();

    default String getColumns(boolean initialization, boolean withID) {
        return getVariablesWithID()
                .stream()
                .skip(withID ? 0 : 1)
                .map(dbValue -> initialization ? dbValue.build() : dbValue.getTitle())
                .collect(Collectors.joining(", "));
    }

    /**
     * @param set if true returned variables will be in format Param1='param1' instead of 'param1'
     * @return string variable of all fields of entity
     */
    default String getVariables(boolean set) {
        return getVariables().stream().map((val)->set ? val.forSet() : val.inQuotes())
                .collect(Collectors.joining(", "));
    }

    /**
     * @return variables as list of dbvalues
     */
    List<DBValue> getVariables();

    default List<DBValue> getVariablesWithID() {
        List<DBValue> variables = new ArrayList<>(getVariables());
        variables.add(0, getEntityID());
        return variables;
    }

//    List<IDBEntity> getListFromResultSet(ResultSet resultSet) throws SQLException;
}
