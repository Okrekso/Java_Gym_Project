package database;

import java.text.ParseException;
import java.util.Map;

public interface IDBEntityFactory {
    default DBEntity getEmptyEntity() { return this.create(); }

    DBEntity create();
    DBEntity create(Map<String, String> parameters) throws ParseException;
}
