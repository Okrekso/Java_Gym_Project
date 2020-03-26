package logic.visitors;

import logic.Subscription;
import logic.Visit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Interface that implement any user card that visit gym
 */
public interface IVisitor {
    public String getInfo();
    default public Subscription getSubscription() {
        return new Subscription(0, Duration.ofDays(360*10), "Unsubscribed",
                "you doesn't selected any subscription plan", new ArrayList<>());
    }
    public List<Visit> getVisits();
}
