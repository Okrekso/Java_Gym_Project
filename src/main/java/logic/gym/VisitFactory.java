package logic.gym;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class VisitFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return new Visit(-1, new Date(), 0, -1);
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return new Visit(getId(parameters, create().getEntityID().getTitle()),
                new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("visitDate")),
                Float.parseFloat(parameters.get("price")), Integer.parseInt(parameters.get("gymID")));
    }
}
