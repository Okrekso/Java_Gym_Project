package database;

import logic.gym.Info;
import logic.gym.Gym;
import logic.gym.GymSection;
import logic.gym.Subscription;
import logic.gym.Visit;
import logic.visitors.Member;

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
        return this.insertIntoTable(entity.getTableID(), entity.getColumns(false, false), entity.getVariables(false));
//        return this.execute("INSERT INTO " + entity.tableID + "(" + entity.getVariables(false) + ")");
    }

    public String getTableCreationQuery(DBEntity entity) {
        return "CREATE TABLE "+entity.getTableID()+"("+entity.getColumns(true, true)+")";
    }

    @Override
    public boolean isDBcreated() {
        return executeQuery("SELECT * FROM "+new Info(0, new Date(), "").getTableID()) != null;
    }

    public boolean buildNewGymDB() {
        GymDB db = new GymDB();
        List<String> queries = getDBentities().stream().map(dbEntity -> db.getTableCreationQuery(dbEntity)).collect(Collectors.toList());
        for(String query : queries) {
            db.executeUpdate(query);
        }
        return addToTable(new Info(0, new Date(), "DB creation"));
    }

    /**
     * @return all possible entities exists in database
     */
    @Override
    public List<DBEntity> getDBentities() {
        return Arrays.asList(
                new Info(-1, new Date(), "DB Creation"),
                new Gym(-1,"", ""),
                new GymSection(-1, 0, "", "", 0),
                new Subscription(-1, 0, 1, "", ""),
                new Member(-1, "", "", new Date()),
                new Visit(-1, new Date(), 0, 0)
        );
    }

    public boolean dropCurrentDB() {
        GymDB db = new GymDB();
        List<String> tableIDs = getDBentities().stream().map(dbEntity -> dbEntity.tableID).collect(Collectors.toList());
        for(String tableID : tableIDs) {
            if(!db.executeUpdate("DROP TABLE " + tableID))
                return false;
        }
        return true;
    }
}
