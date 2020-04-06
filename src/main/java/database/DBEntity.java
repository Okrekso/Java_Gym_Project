package database;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DBEntity implements IDBEntity {
    protected String tableID;
    protected DBValue<Integer> entityID;
    protected Database db;

//    database accessibility parameters
    protected boolean deletable = false;
    protected boolean editable = false;
    protected boolean addable = false;

    protected DBEntity makeDeletable() {
        this.deletable = true;
        return this;
    }

    public boolean isAddable() {
        return addable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public boolean isEditable() {
        return editable;
    }

    protected DBEntity makeEditable() {
        this.editable = true;
        return this;
    }

    protected DBEntity makeAddable() {
        this.addable = true;
        return this;
    }

    @Override
    public Integer getEntityIDValue() {
        return entityID.getValue();
    }

    public DBValue getEntityID() {
        return entityID;
    }

    public String getTableID() {
        return tableID;
    }

    public Database getDatabase() {
        return db;
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
