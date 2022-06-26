package model;

public class InvoiceLines {

    private int invoice;
    private String itemName;
    private double itemPrice;
    private double count;

    public InvoiceLines() {
    }

    public InvoiceLines(int invoice, String itemName, double itemPrice, double count) {
        this.invoice = invoice;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getTotal() {
        return count * itemPrice;
    }

    @Override
    public String toString() {
        return invoice + "," + itemName + "," + itemPrice + "," + count + "," + getTotal();
    }

}
