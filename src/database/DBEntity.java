package database;

public abstract class DBEntity implements IDBEntity {
    protected String tableID;
    protected int entityID;
    protected Database db;

    protected DBEntity(String tableID, int entityID, Database db) {
        this.tableID = tableID;
        this.entityID = entityID;
        this.db = db;
    }
}
