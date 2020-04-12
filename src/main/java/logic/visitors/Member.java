package logic.visitors;

import database.*;
import logic.gym.GymSectionFactory;
import logic.gym.Subscription;
import logic.gym.SubscriptionFactory;
import logic.gym.Visit;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class Member extends DBEntity implements IVisitor, ISubscriptable, IDBContainRelative {
    private DBValue<String> name;
    private DBValue<String> surname;
    private DBValue<Date> birthday;
    private Subscription subscription;
    private List<Visit> visits;

    public Member(int memberID, String name, String surname, Date birthday) {
        super("Members", new DBValue("memberID", memberID, JDBCType.INTEGER), new GymDB());
        this.name = new DBValue<>("name", name, JDBCType.NVARCHAR).addSize(255);
        this.surname = new DBValue<>("surname", surname, JDBCType.NVARCHAR).addSize(255);
        this.birthday = new DBValue<>("birthday", birthday, JDBCType.DATE);
        this.makeAddable();
        this.makeDeletable();
        this.makeEditable();

    }

    @Override
    public String getInfo() {
        return "default gym member " + this.name;
    }

    @Override
    public void subscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void unsubscribe() {
        this.subscription = null;
    }

    @Override
    public String getSubscriptionInfo() {
        return this.subscription.getTitle();
    }

    @Override
    public void registerEnter() {
        throw new RuntimeException("unimplemented function called");
    }

    @Override
    public void registerExit() {
        throw new RuntimeException("unimplemented function called");
    }

    @Override
    public List<Visit> getVisits() {
        return visits;
    }


    @Override
    public IDBEntityFactory getFactory() {
        return new MemberFactory();
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(name, surname, birthday);
    }

    @Override
    public Map<String, List<? extends DBEntity>> getRelativeValues() {
        Map<String, List<? extends DBEntity>> relativeMap = new HashMap<>();
        relativeMap.put(new GymSectionFactory().create().getTableID(), getVisits());
        return relativeMap;
    }
}
