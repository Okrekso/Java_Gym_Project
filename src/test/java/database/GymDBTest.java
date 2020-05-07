package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GymDBTest {

    private GymDB db;
    private static final Logger log = LogManager.getLogger(GymDBTest.class);

    @BeforeEach
    void setUp() {
        db = new GymDB();
        db.dropCurrentDB();
        db.build();
    }

    @Test
    void addToTable() {
        List<Boolean> noForeignResults = db.getDBEntityFactories().stream()
                .filter(factory -> !factory.create().hasForeignKeys())
                .map(
                factory -> {
                    boolean result = db.addToTable(factory.create()) != null;
                    if(result) log.info("test on " + factory.create().getTableID() + " successfully done!");
                    else log.error("test on "+factory.create().getTableID()+" failure!");
                    return result;
                }
        ).collect(Collectors.toList());

        log.info("========= foreign keys tests starts =========");

        Map<String, Boolean> foreignResults = db.getDBEntityFactories()
                .stream()
                .filter(factory -> factory.create().hasForeignKeys())
                .collect(Collectors.toMap(
                        factory -> factory.create().getTableID(),
                        factory -> {
                            List<DBValue> fValues = factory.create().getVariables().stream()
                                    .filter(dbValue -> dbValue.isForeignKey())
                                    .map(dbValue -> {
                                        IDBEntityFactory foreignFactory = dbValue.getForeignKeyFactory();
                                        DBEntity entity = db.addToTable(foreignFactory.create());
                                        log.info(String.format("created entity of table %s for %s successfully!",
                                                                entity.getTableID(), factory.create().getTableID()));
                                        return entity.entityID;
                                    }).collect(Collectors.toList());
                            if(fValues.size() == factory.create().getVariables().stream()
                                    .filter(dbValue -> dbValue.isForeignKey()).count()) {
                                Map<String, String> values = fValues.stream()
                                        .collect(Collectors.toMap(
                                                fValue->fValue.getTitle(),
                                                fValue->fValue.getValue().toString()
                                                )
                                        );
                                try {
                                    DBEntity finalEntity = factory.createNotFull(values);
                                    log.info("entity with relative values created:");
                                    log.info(finalEntity);
                                    if(db.addToTable(finalEntity)!=null) {
                                        log.info("added entity to table successfully!");
                                        return true;
                                    } else {
                                        log.error("adding entity to table failed!");
                                        return false;
                                    }
                                } catch (ParseException e) {
                                    log.error("creation failed for entity: "+ e.getMessage());
                                    return false;
                                }
                            }
                            return false;
                        })
                );
        Map<String, Boolean> expectedForeignResults = db.getDBEntityFactories()
                .stream()
                .filter(factory -> factory.create().hasForeignKeys())
                .collect(Collectors.toMap(
                        factory -> factory.create().getTableID(),
                        factory -> true)
                );
        Assert.assertArrayEquals(
                noForeignResults.toArray(),
                noForeignResults.stream().map(res->true).collect(Collectors.toList()).toArray()
        );
        Assert.assertEquals(
                foreignResults,
                expectedForeignResults
        );
    }

    @Test
    void getTableCreationQuery() {
        List<Boolean> results = db.getDBEntityFactories().stream()
                .sorted(
                        (f1, f2) -> Boolean.compare(f1.create().hasForeignKeys(), f2.create().hasForeignKeys())
                )
                .map(
                        factory -> {
                            boolean result = true;
                            if(!factory.create().hasForeignKeys())
                                result = db.addToTable(factory.create()) != null;
                            else
                                log.warn("entity "+factory.create().getTableID() + " have foreign keys, so test on it's table creation'll be done in addToTable test");
                            if(result) log.info("Get table creation query test on "+factory.create().getTableID()+" successfully done!");
                            else log.error("Get table creation query test on "+factory.create().getTableID()+" failure!");
                            return result;
                        }
                ).collect(Collectors.toList());
        Boolean[] expectedResults = new Boolean[results.size()];
        Arrays.fill(expectedResults, true);
        Assert.assertArrayEquals(results.toArray(), expectedResults);
    }

    @Test
    void isDBcreated() {
        Assert.assertTrue(db.isDBcreated());
    }

    @Test
    void build() {
        if(db.isDBcreated())
            db.dropCurrentDB();
        Assert.assertTrue(db.build());
    }

    @Test
    void dropCurrentDB() {
        if(!db.isDBcreated())
            db.build();
        Assert.assertTrue(db.dropCurrentDB());
    }
}