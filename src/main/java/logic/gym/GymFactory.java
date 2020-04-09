package logic.gym;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.util.Map;

public class GymFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return new Gym(-1,"", "");
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return new Gym(getId(parameters, create().getEntityID().getTitle()),
                parameters.get("title"), parameters.get("address"));
    }
}
