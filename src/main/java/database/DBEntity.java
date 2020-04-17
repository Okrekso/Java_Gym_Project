package database;

import logic.gym.GymSection;
import logic.gym.GymSectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlets.dbOps.DBCreationSubmitServlet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class DBEntity implements IDBEntity {
    protected String tableID;
    protected DBValue<Integer> entityID;
    protected Database db;
    protected static final Logger log = LogManager.getLogger(DBCreationSubmitServlet.class);

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

    public final boolean update() {
        boolean result = getDatabase().updateTable(getTableID(), getVariables(true));
        if(result) {
            log.info("entity " + toString() + " updated successfully");
        } else {
            log.error("update of entity ");
        }
        return result;
    };


    @Override
    public String toString() {
        return String.format("(%s)", getTableID()) + " #" + this.getEntityID().getValue()
                + ". (" + this.getVariables().stream()
                .map(element-> element.getValue().toString())
                .collect(Collectors.joining(", ")) + ")";
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

    protected List<DBEntity> getListFromResultSet(ResultSet resultSet, IDBEntityFactory factory) throws SQLException, ParseException {
        if(resultSet==null) return null;
        List<DBEntity> entities = new ArrayList<>();
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

    protected List getRelativeEntityList(DBValue foreignKeyValue, IDBEntityFactory foreignKeyFactory)
            throws SQLException, ParseException {
        List<DBEntity> entities = db.getFromEntityTable(
                foreignKeyFactory,
                String.format("%s=%s", foreignKeyValue.getTitle(), foreignKeyValue.getValue())
        );
        if(entities!=null && entities.size()>0) {
            return entities.stream().map(entity -> foreignKeyFactory.create().getClass().cast(entity))
                    .collect(Collectors.toList());
        }
        return new ArrayList();
    }
}
