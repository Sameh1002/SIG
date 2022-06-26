package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadHeader {

    private ArrayList<InvoiceHeader> headerArrayList = new ArrayList<>();

    public ReadHeader(File headerFile) throws Exception {

        Scanner scanner = new Scanner(headerFile);

        int i = 0;
        while (scanner.hasNext()) {
            String line = scanner.next();
            try ( Scanner rowScanner = new Scanner(line)) {
                rowScanner.useDelimiter(",");
                i = 0;
                InvoiceHeader entry = new InvoiceHeader();
                while (rowScanner.hasNext()) {
                    switch (i) {
                        case 0:
                            entry.setInvoiceNum(rowScanner.nextInt());
                            break;
                        case 1:
                            entry.setInvoiceDate(rowScanner.next());
                            break;
                        case 2:
                            entry.setCustomerName(rowScanner.next());
                            break;
                    }
                    i++;
                }
                headerArrayList.add(entry);
            }

        }
//        System.out.println(headerArrayList.size() + " " + "Headers");
    }

    public ArrayList<InvoiceHeader> getHeaderArrayList() {
        return headerArrayList;
    }

    public void setHeaderArrayList(ArrayList<InvoiceHeader> headerArrayList) {
        this.headerArrayList = headerArrayList;
    }

}
