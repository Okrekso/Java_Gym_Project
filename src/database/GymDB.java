package database;

import logic.gym.Info;
import logic.gym.Gym;
import logic.gym.GymSection;
import logic.gym.Subscription;
import logic.gym.Visit;
import logic.visitors.Member;

import java.sql.ResultSet;
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
    public boolean addToTable(DBEntity entity) {
        this.executeQuery("INSERT INTO TABLE " + entity.tableID + "(" + entity.getVariables(false) + ")");
        return true;
    }

    public String getTableCreationQuery(DBEntity entity) {
        return "CREATE TABLE "+entity.tableID+"("+entity.getColumns()+")";
    }

    public boolean isDBcreated() {
        return executeQuery("SELECT * FROM Info") != null;
    }

    public boolean buildNewGymDB() {
        GymDB db = new GymDB();
        List<String> queries = Arrays.asList(
                db.getTableCreationQuery(new Info(0, new Date(), "DB Creation")),
                db.getTableCreationQuery(new Gym(0,"", "")),
                db.getTableCreationQuery(new GymSection(0, 0, "", "", 0)),
                db.getTableCreationQuery(new Subscription(0, 0, 1, "", "")),
                db.getTableCreationQuery(new Member(0, "", "", new Date())),
                db.getTableCreationQuery(new Visit(0, new Date(), 0, 0))

        );
        for(String query : queries) {
            db.execute(query);
        }
        return true;
    }
}
