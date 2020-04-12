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
        return new Member(getId(parameters, create().getEntityID().getTitle()),
                parameters.get("name"), parameters.get("surname"),
                new SimpleDateFormat("yyyy-MM-dd").parse(parameters.get("birthday")));
    }
}
