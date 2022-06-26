package model;

import java.util.ArrayList;

public class InvoiceHeader {

    private int invoiceNum;
    private String invoiceDate;
    private String customerName;
    private ArrayList<InvoiceLines> lines = new ArrayList<>();

    public InvoiceHeader() {
    }

    public InvoiceHeader(int invoiceNum, String invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
        this.lines = new ArrayList<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getInvoiceTotal() {
        double sum;
        sum = 0.0;
        for (int i = 0; i < lines.size(); i++) {
            sum += lines.get(i).getTotal();
        }
        return sum;
    }

    public ArrayList<InvoiceLines> getLines() {
        return lines;
    }

    public void setLines(ArrayList<InvoiceLines> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        String header = invoiceNum + "," + invoiceDate + "," + customerName + "\r\n";
        String details = "";
        for (InvoiceLines detail : lines) {
            details += detail.toString() + "\r\n";
        }
        return header + details;
    }
}
