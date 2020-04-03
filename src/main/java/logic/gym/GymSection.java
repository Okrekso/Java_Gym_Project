package logic.gym;

import database.DBValue;
import database.DBEntity;
import database.GymDB;
import database.IDBEntity;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GymSection extends DBEntity {
    private DBValue<String> title;
    private DBValue<String> description;
    private DBValue<Integer> maxPeopleCapacity;
    private DBValue<Integer> gymID;

    public GymSection(int gymSectionID, int gymID, String title, String description, int maxPeopleCapacity) {
        super("GymSections", new DBValue("gymSectionID", gymSectionID, JDBCType.INTEGER), new GymDB());
        this.title = new DBValue<>("title", title, JDBCType.NVARCHAR).addSize(255);
        this.description = new DBValue<>("description", description, JDBCType.NVARCHAR).addSize(255);
        this.maxPeopleCapacity = new DBValue<>("maxPeopleCapacity", maxPeopleCapacity, JDBCType.INTEGER)
                .addNotNull().addDefaultValue(1);
        this.gymID = new DBValue<>("gymID", gymID, JDBCType.INTEGER).addForeignKey("Gyms", "gymID");
    }


    public int getMaxPeopleCapacity() {
        return maxPeopleCapacity.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public String getTitle() {
        return title.getValue();
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
    public String getVariables(boolean set) {
        List<DBValue>vars = Arrays.asList(title, description, maxPeopleCapacity, gymID);
        return vars.stream().map((val)->set ? val.forSet() : val.inQuotes()).collect(Collectors.joining(", "));
    }

    @Override
    public String getColumns(boolean initialization, boolean withID) {

        return  super.getColumns(Arrays.asList(entityID,
                title, description, maxPeopleCapacity, gymID), initialization, withID);
    }

    @Override
    public String getDisplayValue() {
        return null;
    }

    @Override
    public List<IDBEntity> getListFromResultSet(ResultSet resultSet) {
        return null;
    }
}
