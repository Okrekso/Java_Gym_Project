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
import java.util.*;
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
    public List<DBValue> getVariables() {
        return Arrays.asList(date, event);
    }
}
