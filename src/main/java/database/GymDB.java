package database;

import logic.gym.*;
import logic.visitors.MemberFactory;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GymDB extends Database {
    public GymDB(String user, String password) {
        super("jdbc:h2:mem:gymdb", user, password);
    }
    public GymDB() {
        super("jdbc:h2:mem:gymdb", "PUBLIC", "");
    }


    @Override
    public boolean isDBcreated() {
        try {
            return executeQuery("SELECT * FROM "+ new InfoFactory().create().getTableID()) != null;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<IDBEntityFactory> getDBEntityFactories() {
        return Arrays.asList(
                new InfoFactory(),
                new GymFactory(),
                new GymSectionFactory(),
                new SubscriptionFactory(),
                new MemberFactory(),
                new VisitFactory()
        );
    }
}
