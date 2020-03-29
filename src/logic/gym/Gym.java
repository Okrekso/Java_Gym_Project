package logic.gym;

import database.ColumnBuilder;
import database.DBEntity;
import database.GymDB;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Gym extends DBEntity {
    private List<GymSection> gymSections;
    private List<Subscription> subscriptions;

    private String title;
    private String address;

    protected Gym(int gymID, String title, String address) {
        super("Gyms", gymID, new GymDB());
        this.title = title;
        this.address = address;
    }


    public List<GymSection> getGymSections() {
        return gymSections;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
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
        return this.title+", "+this.address;
    }

    @Override
    public String getColumns() {
        List<String> columns = new ArrayList<>(Arrays.asList(
                new ColumnBuilder<Integer>("gymID", JDBCType.INTEGER).build(),
                new ColumnBuilder<String>("title", JDBCType.NVARCHAR).addSize(255).addNotNull().build(),
                new ColumnBuilder<String>("address",JDBCType.NVARCHAR).addSize(255).addNotNull().build()
        ));
        return String.join(", ", columns);
    }
}
