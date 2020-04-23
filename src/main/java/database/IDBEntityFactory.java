package database;

import logic.gym.Info;

import java.sql.JDBCType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface IDBEntityFactory {
    static class DEFAULTS {
        static Map<JDBCType, String> defaultValues = new HashMap<>();
        static {
            defaultValues.put(JDBCType.NVARCHAR, "");
            defaultValues.put(JDBCType.VARCHAR, "");
            defaultValues.put(JDBCType.DATE, new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
            defaultValues.put(JDBCType.INTEGER, "0");
            defaultValues.put(JDBCType.FLOAT, "0.0");
            defaultValues.put(JDBCType.DOUBLE, "0.0");
        }
        public static final String getDefault(JDBCType type) {
            return defaultValues.get(type);
        }
    }

    default Integer getId(Map<String, String> parameters, String idTitle) {
        return Integer.parseInt(parameters.get(idTitle)==null ? "-1" : parameters.get(idTitle));
    }

    DBEntity create();
    DBEntity create(Map<String, String> parameters) throws ParseException;
    default DBEntity createNotFull(Map<String, String> parameters) throws ParseException {
        return create(create().getVariables().stream().collect(Collectors.toMap(
                variable -> variable.getTitle(),
                variable -> parameters.get(
                        variable.getTitle()) == null
                        ? DEFAULTS.getDefault(variable.getType())
                        : parameters.get(variable.getTitle()
                )
        )));
    }
}
