package logic.visitors;

import logic.Subscription;
import logic.Visit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trainer implements IVisitor {
    private String name;
    private String surname;
    private Date birthday;

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Subscription getSubscription() {
        return new Subscription(0, Duration.ofDays(1000), "Trainer Subscription", "infinite trainer Subscription", new ArrayList<>());
    }

    @Override
    public List<Visit> getVisits() {
        return null;
    }
}
