package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadDetails {

    private ArrayList<InvoiceLines> detailsArrayList = new ArrayList<>();

    public ReadDetails(File linesFile) throws Exception {
        Scanner scanner2 = new Scanner(linesFile);
        int i;
        while (scanner2.hasNext()) {
            String line = scanner2.next();
            try ( Scanner rowScanner = new Scanner(line)) {
                rowScanner.useDelimiter(",");
                i = 0;
                InvoiceLines entry2 = new InvoiceLines();
                while (rowScanner.hasNext()) {
                    switch (i) {
                        case 0:
                            entry2.setInvoice(rowScanner.nextInt());
                            break;
                        case 1:
                            entry2.setItemName(rowScanner.next());
                            break;
                        case 2:
                            entry2.setItemPrice(rowScanner.nextDouble());
                            break;
                        case 3:
                            entry2.setCount(rowScanner.nextDouble());
                            break;
                    }
                    i++;
                }
                detailsArrayList.add(entry2);
            }
        }

//        System.out.println(detailsArrayList.size() + " " + "invoice");

    }

    public ArrayList<InvoiceLines> getDetailsArrayList() {
        return detailsArrayList;
    }

    public void setDetailsArrayList(ArrayList<InvoiceLines> aDetailsArrayList) {
        detailsArrayList = aDetailsArrayList;
    }

}
