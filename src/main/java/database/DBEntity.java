package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class DBEntity implements IDBEntity {
    protected String tableID;
    protected DBValue<Integer> entityID;
    protected Database db;

//    database accessibility parameters
    protected boolean deletable = false;
    protected boolean editable = false;
    protected boolean addable = false;

    protected DBEntity(String tableID, DBValue entityID, Database db) {
        this.tableID = tableID;
        this.entityID = entityID.addPrimaryKey().addAutoIncrement();
        this.db = db;
    }

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
    public String toString() {
        return this.getVariablesWithID().stream()
                .map(element-> element.getValue().toString())
                .collect(Collectors.joining(", "));
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

    protected List<IDBEntity> getListFromResultSet(ResultSet resultSet, IDBEntityFactory factory) throws SQLException, ParseException {
        if(resultSet==null) return null;
        List<IDBEntity> entities = new ArrayList<>();
        while(resultSet.next()) {
            factory.create().getVariablesWithID().get(0).getTitle();
            Map<String, String> readyValues = factory.create().getVariablesWithID().stream()
                    .map(dbValue -> dbValue.getTitle())
                    .collect(Collectors.toMap(title->title, title-> {
                        try {
                            return resultSet.getString(title);
                        } catch (SQLException e) {
                            return null;
                        }
                    }));
            entities.add(factory.create(readyValues));
        }
        return entities;
    }
}
