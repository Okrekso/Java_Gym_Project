package database;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DBEntity implements IDBEntity {
    protected String tableID;
    protected DBValue<Integer> entityID;
    protected Database db;

    public String getTableID() {
        return tableID;
    }

    protected String getColumns(List<DBValue> variables, boolean initialization, boolean withID) {
        return variables
                .stream()
                .skip(withID ? 0 : 1)
                .map(dbValue -> initialization ? dbValue.build() : dbValue.getTitle())
                .collect(Collectors.joining(", "));
    }

    protected DBEntity(String tableID, DBValue entityID, Database db) {
        this.tableID = tableID;
        this.entityID = entityID.addPrimaryKey().addAutoIncrement();
        this.db = db;
    }
}
