package logic.gym;

import database.DBValue;
import database.DBEntity;
import database.GymDB;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Log class for database. Existence of entities inside this table means that database were created.
 */
public class Info extends DBEntity {

    DBValue<Integer> infoID;
    DBValue<Date> date;
    DBValue<String> event;

    public Info(int infoID, Date date, String event) {
        super("Info", new DBValue("infoID", infoID, JDBCType.INTEGER), new GymDB());
        this.date = new DBValue<>("date", date, JDBCType.DATE);
        this.event = new DBValue<>("event", event, JDBCType.NVARCHAR).addSize(255);
    }

    @Override
    public boolean delete() {
        return false;
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
    public String getColumns() {
        return Arrays.asList(
                entityID.build(),
                date.build(),
                event.build()
        ).stream().collect(Collectors.joining(", "));
    }
}
