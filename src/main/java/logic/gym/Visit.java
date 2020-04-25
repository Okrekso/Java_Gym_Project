package logic.gym;

import database.*;

import java.sql.Array;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Visit extends DBEntity {
    private DBValue<Date> visitDate;
    private DBValue<Float> price;
    private DBValue<Integer> gymID;

    public Visit(int visitID, Date visitDate, float price, int gymID) {
        super("Visits", new DBValue("visitID", visitID, JDBCType.INTEGER), new GymDB());
        this.visitDate = new DBValue<>("visitDate", visitDate, JDBCType.DATE);
        this.price = new DBValue<>("price", price, JDBCType.FLOAT);
        this.gymID = new DBValue<>("gymID", gymID, JDBCType.INTEGER).addForeignKey(new GymFactory());

        this.makeAddable();
        this.makeDeletable();
    }

    public Date getVisitDate() {
        return visitDate.getValue();
    }

    public double getPrice() {
        return price.getValue();
    }

    @Override
    public IDBEntityFactory getFactory() {
        return new VisitFactory();
    }

    @Override
    public List<DBValue> getVariables() {
        return Arrays.asList(visitDate, price, gymID);
    }
}
