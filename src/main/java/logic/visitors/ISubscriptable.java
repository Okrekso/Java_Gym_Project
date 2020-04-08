package logic.visitors;

import database.IDBEntity;
import logic.gym.Subscription;

public interface ISubscriptable {
    public void subscribe(Subscription subscription);
    public void unsubscribe();
    public String getSubscriptionInfo();
    public void registerEnter();
    public void registerExit();
}
