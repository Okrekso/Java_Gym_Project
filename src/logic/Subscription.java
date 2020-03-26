package logic;

import logic.gym.GymSection;

import java.time.Duration;
import java.util.List;

public class Subscription {
    private double price;
    private Duration duration;
    private String title;
    private String description;
    private List<GymSection> accessSections;

    public Subscription(double price, Duration duration, String title, String description, List<GymSection> accessSections) {
        this.price = price;
        this.duration = duration;
        this.title = title;
        this.description = description;
        this.accessSections = accessSections;
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
}

