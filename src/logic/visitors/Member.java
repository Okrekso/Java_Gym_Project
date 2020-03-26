package logic.visitors;

import logic.Subscription;
import logic.Visit;
import logic.visitors.IVisitor;

import java.util.Date;
import java.util.List;

public class Member implements IVisitor, ISubscriptable {
    private String name;
    private String surname;
    private Date birthday;
    private Subscription subscription;
    private List<Visit> visits;

    public Member(String name, String surname, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    @Override
    public String getInfo() {
        return "default gym member " + this.name;
    }

    @Override
    public void subscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void unsubscribe() {
        this.subscription = null;
    }

    @Override
    public String getSubscriptionInfo() {
        return this.subscription.getTitle();
    }

    @Override
    public void registerEnter() {
        throw new RuntimeException("unimplemented function called");
    }

    @Override
    public void registerExit() {
        throw new RuntimeException("unimplemented function called");
    }

    @Override
    public Subscription getSubscription() {
        return null;
    }

    @Override
    public List<Visit> getVisits() {
        return visits;
    }
}
