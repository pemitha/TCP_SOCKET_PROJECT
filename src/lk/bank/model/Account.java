package lk.bank.model;

public class Account {

    private final String accno;
    private final String cvv;
    private final String expirationDate;
    private double price;

    public Account(String accno, String cvv, String expirationDate, double price) {
        this.accno = accno;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.price = price;
    }

    public String getAccno() {
        return accno;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public double getPrice() {
        Double b = price;
        b.longValue();
        return b;

    }

    public void setPrice(double price) {
        this.price = price;
    }
}
