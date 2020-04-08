package logic.visitors;

import database.*;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Trainer extends DBEntity {
    private DBValue<String> name;
    private DBValue<String> surname;
    private DBValue<Date> birthday;

    protected Trainer(DBValue trainerID, String name, String surname, Date birthday) {
        super("Trainers", trainerID, new GymDB());
        this.name = new DBValue<>("name", name, JDBCType.NVARCHAR).addSize(255).addNotNull();
        this.surname = new DBValue<>("surname", surname, JDBCType.NVARCHAR).addSize(255).addNotNull();
        this.birthday = new DBValue<>("birthday", birthday, JDBCType.DATE);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(name, surname, birthday);
    }
}
