package logic.gym;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.util.Map;

public class GymSectionFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return new GymSection(-1, -1, "", "", 0);
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return new GymSection(getId(parameters, create().getEntityID().getTitle()),
                Integer.parseInt(parameters.get("gymID")),
                parameters.get("title"), parameters.get("description"),
                Integer.parseInt(parameters.get("maxPeopleCapacity")));
    }
}
