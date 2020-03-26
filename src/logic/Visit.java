package logic;

import java.util.Date;

public class Visit {
    private Date visitDate;
    private double price;

    public Visit(Date visitDate, double price) {
        this.visitDate = visitDate;
        this.price = price;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public double getPrice() {
        return price;
    }
}
