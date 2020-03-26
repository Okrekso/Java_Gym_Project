package logic.visitors;

import logic.Subscription;

public interface ISubscriptable {
    public void subscribe(Subscription subscription);
    public void unsubscribe();
    public String getSubscriptionInfo();
    public void registerEnter();
    public void registerExit();
}
