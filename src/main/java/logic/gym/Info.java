package logic.gym;

import database.DBValue;
import database.DBEntity;
import database.GymDB;
import database.IDBEntity;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Log class for database. Existence of entities inside this table means that database were created.
 */
public class Info extends DBEntity {

    DBValue<String> date;
    DBValue<String> event;

    public Info(int infoID, Date date, String event) {
        super("Infos", new DBValue("infoID", infoID, JDBCType.INTEGER), new GymDB());
        this.makeDeletable();
        this.makeAddable();
        this.date = new DBValue<>("date", new SimpleDateFormat("YYYY-MM-dd").format(date), JDBCType.DATE);
        this.event = new DBValue<>("event", event, JDBCType.NVARCHAR).addSize(255);
    }

    @Override
    public boolean update() {
        return new GymDB().updateTable("Info", getVariables(true));
    }

    @Override
    public String getVariables(boolean set) {
        List<DBValue>vars = Arrays.asList(date, event);
        return vars.stream().map((val)->set ? val.forSet() : val.inQuotes()).collect(Collectors.joining(", "));
    }

    @Override
    public String getColumns(boolean initialization, boolean withID) {
        return super.getColumns(Arrays.asList(entityID, date, event), initialization, withID);
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(entityID, date, event);
    }

    @Override
    public String getDisplayValue() {
        return Arrays.asList(this.event.getValue(), date.getValue())
                .stream().collect(Collectors.joining(", "));
    }

    @Override
    public List<IDBEntity> getListFromResultSet(ResultSet resultSet) throws SQLException {
            if(resultSet==null) return null;
            List<IDBEntity> entities = new ArrayList<>();
            boolean is = resultSet.isClosed();
            while(resultSet.next()) {
                Integer infoID = (Integer)resultSet.getObject(entityID.getTitle());
                Date date = resultSet.getDate(this.date.getTitle());
                String event = (String)resultSet.getObject(this.event.getTitle());
                entities.add(new Info(infoID, date, event));
            }
            return entities;
    }
}
