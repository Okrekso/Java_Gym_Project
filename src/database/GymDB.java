package database;

public class GymDB extends Database {
    public GymDB(String user, String password) {
        super("jdbc:h2:mem:gymdb", user, password);
    }
    public GymDB() {
        super("jdbc:h2:mem:gymdb", "root", "");
    }
    @Override
    public boolean addToTable(DBEntity entity) {
        this.execute("INSERT INTO TABLE "+entity.tableID+"("+entity.getVariables()+")");
        return true;
    }

    @Override
    public boolean createTableFromEntity(DBEntity entity) {
        this.execute("CREATE TABLE "+entity.tableID+"("+entity.getVariables()+")");
        return true;
    }
}
