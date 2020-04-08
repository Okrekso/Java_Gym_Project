package database;

import logic.gym.Info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @Override
    public String getTableID() {
        return tableID;
    }

    public DBValue getEntityID() {
        return entityID;
    }


    @Override
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
