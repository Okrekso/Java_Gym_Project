package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DBValueTest {
    private Map<String, JDBCType> TYPES = new HashMap<String, JDBCType>() {
        {
            put("DateValue", JDBCType.DATE);
            put("DoubleValue", JDBCType.DOUBLE);
            put("FloatValue", JDBCType.FLOAT);
            put("IntegerValue", JDBCType.INTEGER);
            put("NvarChar", JDBCType.NVARCHAR);
        }
    };
    private class ExampleDB extends Database {

        private List<IDBEntityFactory> entityFactories = Arrays.asList(createNewFactory(new ExampleEntity(-1).fill()));

        public ExampleDB() {
            super("jdbc:h2:mem:example_db", "root", "");
        }

        public ExampleDB(List<IDBEntityFactory> factories) {
            super("jdbc:h2:mem:example_db", "root", "");
            this.entityFactories = factories;
        }

        @Override
        public boolean isDBcreated() {
            try {
                return getFromEntityTable(new ExampleEntity(-1).getFactory()) != null;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public List<IDBEntityFactory> getDBEntityFactories() {
            return entityFactories;
        }
    }

    private IDBEntityFactory createNewFactory(ExampleEntity entity) {
        return new IDBEntityFactory() {
            @Override
            public DBEntity create() {
                return entity;
            }

            @Override
            public DBEntity create(Map<String, String> parameters) throws ParseException {
                ExampleEntity entity = new ExampleEntity(Integer.parseInt(parameters.get("entityID")));
                entity.fill(parameters);
                return entity;
            }
        };
    }

    private class ExampleEntity extends DBEntity {
        List<DBValue> values = new ArrayList<>();

        public ExampleEntity(Integer entityID) {
            super("Examples", new DBValue<>("entityID", entityID, JDBCType.INTEGER), new GymDB());
        }

        public ExampleEntity fill() {
            values.add(new DBValue<>("DateValue", new Date(), JDBCType.DATE));
            values.add(new DBValue<>("DoubleValue", 4.45654, JDBCType.DOUBLE));
            values.add(new DBValue<>("FloatValue", (float)32.12321, JDBCType.FLOAT));
            values.add(new DBValue<>("IntegerValue", 1000, JDBCType.INTEGER));
            values.add(new DBValue<>("NvarChar", "Hello world! Привіт світ!", JDBCType.NVARCHAR)
                    .addSize(255));
            return this;
        }

        public ExampleEntity fill(Map<String, String> parameters) throws ParseException {
            parameters.forEach((key, value) -> values.add(new DBValue<>(key, value, TYPES.get(key))));
            return this;
        }

        public void addValue(DBValue value) {
            values.add(value);
        }

        @Override
        public IDBEntityFactory getFactory() {
            return createNewFactory(this);
        }

        @Override
        public List<DBValue> getVariables() {
            return values;
        }
    }

    private ExampleDB db;

    private static final Logger log = LogManager.getLogger(GymDBTest.class);

    @BeforeEach
    void setUp() {
        db = new ExampleDB();
        if(db.isDBcreated())
            db.dropCurrentDB();
        db.build();
    }

    @Test
    void addDefaultValue() {
        ExampleEntity entity = new ExampleEntity(-1);
        entity.addValue(new DBValue<>("StringDefaultValue", null, JDBCType.NVARCHAR)
                .addSize(255).addDefaultValue("Default value applied").addNotNull());

        db.dropCurrentDB();
        db = new ExampleDB(Arrays.asList(createNewFactory(entity)));
        db.build();

        DBEntity returnEntity = db.addToTable(entity);
        try {
            Assert.assertTrue(returnEntity != null);
            log.info("added item successfully: " + returnEntity);
        } catch (AssertionError e) {
            log.error("adding item failure!");
        }
    }

    @Test
    void build() {
        ExampleEntity entity = (ExampleEntity) createNewFactory(new ExampleEntity(-1).fill()).create();
        List<String> results = entity.getVariables().stream()
                .map(dbValue -> {
                    log.info(String.format("built value of type %s: %s", dbValue.getType(),
                        dbValue.build()));
                    return dbValue.build();
                }).collect(Collectors.toList());
        try {
            DBEntity resultEntity = db.addToTable(entity);
            Assert.assertTrue(resultEntity != null);
            log.info("added entity successfully!");
            log.info(resultEntity);
        } catch (AssertionError e) {
            log.error("adding to table was failure");
        }
    }
}