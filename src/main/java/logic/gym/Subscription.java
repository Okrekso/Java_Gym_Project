package logic.gym;

import database.DBValue;
import database.DBEntity;
import database.GymDB;
import database.IDBEntity;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Subscription extends DBEntity {
    private DBValue<Float> price;
    private DBValue<Integer> duration;
    private DBValue<String> title;
    private DBValue<String> description;
    private List<GymSection> accessSections;

    public Subscription(int subscriptionID, float price, Integer duration, String title, String description) {
        super("subscriptions", new DBValue("subscriptionID", subscriptionID, JDBCType.INTEGER), new GymDB());
        this.price = new DBValue<>("price", price, JDBCType.FLOAT).addNotNull().addDefaultValue(1);
        this.duration = new DBValue<>("duration", duration, JDBCType.INTEGER).addNotNull().addDefaultValue(7);
        this.title = new DBValue<>("title", title, JDBCType.NVARCHAR).addSize(255).addNotNull();
        this.description = new DBValue<>("description", description, JDBCType.NVARCHAR).addSize(255);
    }


    public String getDescription() {
        return description.getValue();
    }

    public String getTitle() {
        return title.getValue();
    }

    public double getPrice() {
        return price.getValue();
    }

    public Integer getDuration() {
        return duration.getValue();
    }

    public List<GymSection> getAccessSections() {
        return accessSections;
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
        List<DBValue>vars = Arrays.asList(title, description, price, duration);
        return vars.stream().map((val)->set ? val.forSet() : val.inQuotes()).collect(Collectors.joining(", "));
    }

    @Override
    public String getColumns(boolean initialization, boolean withID) {

        return  super.getColumns(Arrays.asList(entityID,
                title,
                description,
                price,
                duration), initialization, withID);
    }

    @Override
    public List<DBValue> getVariables() {
        return null;
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

