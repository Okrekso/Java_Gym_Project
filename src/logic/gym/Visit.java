package logic.gym;

import database.ColumnBuilder;
import database.DBEntity;
import database.GymDB;

import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Visit extends DBEntity {
    private Date visitDate;
    private double price;
    private int gymID;

    protected Visit(int visitID, Date visitDate, double price, int gymID) {
        super("Visits", visitID, new GymDB());
        this.visitDate = visitDate;
        this.price = price;
        this.gymID = gymID;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public double getPrice() {
        return price;
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
    public String getVariables() {
        return visitDate + ", " + price + ", " + gymID;
    }

    @Override
    public String getColumns() {
        List<String> columns = new ArrayList<String>(Arrays.asList(
                new ColumnBuilder<Integer>("visitID", JDBCType.INTEGER).addPrimaryKey().build(),
                new ColumnBuilder<Date>("visitDate", JDBCType.DATE).addNotNull().build(),
                new ColumnBuilder<Float>("price", JDBCType.FLOAT).addNotNull().addDefaultValue(0).build(),
                new ColumnBuilder<Integer>("gymID", JDBCType.FLOAT).addForeignKey("Gyms", "GymID").build()
        ));
        return String.join(", ", columns);
    }
}
