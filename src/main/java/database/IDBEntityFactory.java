package database;

import logic.gym.Info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface IDBEntityFactory {
    default DBEntity getEmptyEntity() { return this.create(); }

    default Integer getId(Map<String, String> parameters, String idTitle) {
        return Integer.parseInt(parameters.get(idTitle)==null ? "-1" : parameters.get(idTitle));
    }

    DBEntity create();
    DBEntity create(Map<String, String> parameters) throws ParseException;
//    DBEntity create(DBEntity entity);
}
