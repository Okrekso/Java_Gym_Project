package logic.gym;

import database.DBEntity;
import database.GymDB;

import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subscription extends DBEntity {
    private float price;
    private Duration duration;
    private String title;
    private String description;
    private List<GymSection> accessSections;

    protected Subscription(int subscriptionID, float price, Duration duration, String title, String description) {
        super("subscriptions", subscriptionID, new GymDB());
        this.price = price;
        
        this.duration = duration;
        this.title = title;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Duration getDuration() {
        return duration;
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
    public String getVariables() {
        List<String> vars = new ArrayList<>(Arrays.asList(title, description, duration.toString()));
        return String.join(", ", vars);
    }

    @Override
    public String getColumns() {
        return null;
    }
}

