package database;

import logic.gym.*;
import logic.visitors.MemberFactory;

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
    }

    public String getTableCreationQuery(DBEntity entity) {
        return "CREATE TABLE "+entity.getTableID()+"("+entity.getColumns(true, true)+")";
    }

    @Override
    public boolean isDBcreated() {
        return executeQuery("SELECT * FROM "+new Info(0, new Date(), "").getTableID()) != null;
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

    public IDBEntityFactory getFactoryByTableName(String tableID) {
        List<IDBEntityFactory> factories = getDBEntityFactories().stream()
                .filter(factory -> factory.create().getTableID().toLowerCase().equals(tableID.toLowerCase()))
                .collect(Collectors.toList());
        if(factories.size()>0)
            return factories.get(0);
        else
            return null;
    }

    public boolean build() {
        List<String> queries = getDBEntities().stream()
                .map(dbEntity -> getTableCreationQuery(dbEntity))
                .collect(Collectors.toList());
        for(String query : queries) {
            executeUpdate(query);
        }
        return addToTable(new Info(0, new Date(), "DB creation"));
    }

    public boolean dropCurrentDB() {
        GymDB db = new GymDB();
        List<String> tableIDs = getDBEntities().stream().map(dbEntity -> dbEntity.tableID).collect(Collectors.toList());
        for(String tableID : tableIDs) {
            if(!db.executeUpdate("DROP TABLE " + tableID))
                return false;
        }
        return true;
    }
}
