package logic.gym;

import database.*;

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
    private DBValue<Integer> gymID;
    private List<GymSection> accessSections;

    public Subscription(int subscriptionID, float price, Integer duration, String title, String description, Integer gymID) {
        super("Subscriptions", new DBValue("subscriptionID", subscriptionID, JDBCType.INTEGER), new GymDB());
        this.price = new DBValue<>("price", price, JDBCType.FLOAT).addNotNull().addDefaultValue(1);
        this.duration = new DBValue<>("duration", duration, JDBCType.INTEGER).addNotNull().addDefaultValue(7);
        this.title = new DBValue<>("title", title, JDBCType.NVARCHAR).addSize(255).addNotNull();
        this.description = new DBValue<>("description", description, JDBCType.NVARCHAR).addSize(255);
        this.gymID = new DBValue<>("gymID", gymID, JDBCType.INTEGER).addForeignKey(new GymFactory());

        this.makeAddable();
        this.makeEditable();
        this.makeDeletable();
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
    public IDBEntityFactory getFactory() {
        return new SubscriptionFactory();
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(price, duration, title, description, gymID);
    }
}

