package logic.visitors;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.util.Map;

public class TrainerFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return null;
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return null;
    }
}
