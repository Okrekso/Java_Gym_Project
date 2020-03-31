package logic.visitors;

import database.DBEntity;
import database.DBValue;
import database.Database;
import database.GymDB;

import java.sql.JDBCType;
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
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean add() {
        return false;
    }

    @Override
    public String getVariables(boolean set) {
        List<DBValue> vars = Arrays.asList(name, surname, birthday);
        return vars.stream().map((val)->set ? val.forSet() : val.inQuotes()).collect(Collectors.joining(", "));
    }

    @Override
    public String getColumns() {
        return Arrays.asList(
                entityID.build(),
                name.build(),
                surname.build(),
                birthday.build()
        ).stream().collect(Collectors.joining(", "));
    }
}
