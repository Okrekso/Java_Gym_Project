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
    public boolean update() {
        return false;
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(title, description, maxPeopleCapacity, gymID);
    }
}
