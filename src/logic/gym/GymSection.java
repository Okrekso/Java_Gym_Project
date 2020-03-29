package logic.gym;

import database.ColumnBuilder;
import database.DBEntity;
import database.Database;
import database.GymDB;

import java.sql.JDBCType;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GymSection extends DBEntity {
    private String title;
    private String description;
    private int maxPeopleCapacity;
    private int gymID;

    public GymSection(int gymSectionID, int gymID, String title, String description, int maxPeopleCapacity) {
        super("GymSections", gymSectionID, new GymDB());
        this.title = title;
        this.description = description;
        this.maxPeopleCapacity = maxPeopleCapacity;
        this.gymID = gymID;
    }


    public int getMaxPeopleCapacity() {
        return maxPeopleCapacity;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
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
    public String getVariables() {
        return title + ", " + description + ", " + maxPeopleCapacity + ", " + gymID;
    }

    @Override
    public String getColumns() {
        List<String> columns = new ArrayList<String>(Arrays.asList(
                new ColumnBuilder<Integer>("gymSectionID", JDBCType.INTEGER).addPrimaryKey().build(),
                new ColumnBuilder<String>("title", JDBCType.NVARCHAR).addSize(255).build(),
                new ColumnBuilder<String>("description", JDBCType.NVARCHAR).addSize(255).build(),
                new ColumnBuilder<Integer>("maxPeopleCapacity", JDBCType.INTEGER).addNotNull().addDefaultValue(1).build(),
                new ColumnBuilder<Integer>("gymID", JDBCType.INTEGER).addForeignKey("Gyms", "GymID").build()
        ));
        return String.join(", ", columns);
    }
}
