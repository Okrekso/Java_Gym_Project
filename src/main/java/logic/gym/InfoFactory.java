package logic.gym;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class InfoFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return new Info(-1, new Date(), "");
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return new Info(getId(parameters, create().getEntityID().getTitle()),
                new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("date")),
                parameters.get("event"));
    }
}