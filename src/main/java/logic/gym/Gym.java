package logic.gym;

import database.*;

import java.sql.Array;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Gym extends DBEntity implements IDBContainRelative {
    private List<GymSection> gymSections;
    private List<Subscription> subscriptions;

    private DBValue<String> title;
    private DBValue<String> address;

    public Gym(int gymID, String title, String address) {
        super("Gyms", new DBValue("gymID", gymID, JDBCType.INTEGER), new GymDB());
        this.makeAddable();
        this.makeDeletable();
        this.makeEditable();

        try {
//            getting gymSections & subscriptions from it's table
            this.gymSections = getRelativeEntityList(this.getEntityID(), new GymSectionFactory());
            this.subscriptions = getRelativeEntityList(this.getEntityID(), new SubscriptionFactory());
        } catch (Exception e) {
            log.info("no relative entities found for " + toString() + ": " + e.getMessage());
        }

        this.title = new DBValue<>("title", title, JDBCType.NVARCHAR).addSize(255).addNotNull();
        this.address = new DBValue<>("address", address, JDBCType.NVARCHAR).addSize(255).addNotNull();
    }


    public List<GymSection> getGymSections() {
        return gymSections;
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    @Override
    public IDBEntityFactory getFactory() {
        return new GymFactory();
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(title, address);
    }

    @Override
    public Map<String, List<? extends DBEntity>> getRelativeValues() {
        Map<String, List<? extends DBEntity>> relativeMap = new HashMap<>();
        relativeMap.put(new GymSectionFactory().create().getTableID(), getGymSections());
        relativeMap.put(new SubscriptionFactory().create().getTableID(), getSubscriptions());
        return relativeMap;
    }
}
