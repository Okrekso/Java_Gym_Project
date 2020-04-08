package logic.gym;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.util.Map;

public class SubscriptionFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return new Subscription(-1, 0, 1, "", "");
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return new Subscription(-1, Integer.parseInt(parameters.get("price")),
                Integer.parseInt(parameters.get("duration")),
                parameters.get("title"), parameters.get("description"));
    }
}
