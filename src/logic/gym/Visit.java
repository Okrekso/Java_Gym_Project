package logic.gym;

import database.DBValue;
import database.DBEntity;
import database.GymDB;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
        this.gymID = new DBValue<>("gymID", gymID, JDBCType.INTEGER);
    }

    public Date getVisitDate() {
        return visitDate.getValue();
    }

    public double getPrice() {
        return price.getValue();
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }


    @Override
    public String getVariables(boolean set) {
        List<DBValue>vars = Arrays.asList(visitDate, price, gymID);
        return vars.stream().map((val)->set ? val.forSet() : val.inQuotes()).collect(Collectors.joining(", "));
    }

    @Override
    public String getColumns() {
        return Arrays.asList(
                entityID.build(),
                visitDate.build(),
                price.build(),
                gymID.build()
        ).stream().collect(Collectors.joining(", "));
    }
}
