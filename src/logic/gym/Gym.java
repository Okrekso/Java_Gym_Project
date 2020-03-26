package logic.gym;

import logic.Subscription;

import java.util.List;

public class Gym {
    private List<GymSection> gymSections;
    private String title;
    private String address;
    private List<Subscription> subscriptions;

    public Gym(List<GymSection> gymSections, String title, String address, List<Subscription> subscriptions) {
        this.gymSections = gymSections;
        this.title = title;
        this.address = address;
        this.subscriptions = subscriptions;
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
}
