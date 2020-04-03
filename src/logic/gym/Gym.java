package logic.gym;

import database.DBValue;
import database.DBEntity;
import database.GymDB;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Gym extends DBEntity {
    private List<GymSection> gymSections;
    private List<Subscription> subscriptions;

    private DBValue<String> title;
    private DBValue<String> address;

    public Gym(int gymID, String title, String address) {
        super("Gyms", new DBValue("gymID", gymID, JDBCType.INTEGER), new GymDB());
        this.title = new DBValue<>("title", title, JDBCType.NVARCHAR).addSize(255).addNotNull();
        this.address = new DBValue<>("address", address, JDBCType.NVARCHAR).addSize(255).addNotNull();
    }


    public List<GymSection> getGymSections() {
        return gymSections;
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getAddress() {
        return address.getValue();
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
    public String getVariables(boolean set) {
        List<DBValue>vars = Arrays.asList(title, address);
        return vars.stream().map((val)->set ? val.forSet() : val.inQuotes()).collect(Collectors.joining(", "));
    }

    @Override
    public String getColumns() {
        return Arrays.asList(
                entityID.build(),
                title.build(),
                address.build()
        ).stream().collect(Collectors.joining(", "));
    }
}
