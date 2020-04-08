package logic.visitors;

import database.DBEntity;
import database.IDBEntityFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MemberFactory implements IDBEntityFactory {
    @Override
    public DBEntity create() {
        return new Member(-1, "", "", new Date());
    }

    @Override
    public DBEntity create(Map<String, String> parameters) throws ParseException {
        return new Member(-1, parameters.get("name"), parameters.get("surname"),
                new SimpleDateFormat("YYYY-MM-DD").parse(parameters.get("birthday")));
    }
}
