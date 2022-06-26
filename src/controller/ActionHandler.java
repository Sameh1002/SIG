package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.*;
import view.*;


public class ActionHandler implements ActionListener {

    private final GUI view;

    public ActionHandler(GUI view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open Files":
                openFiles();
                break;
            case "Save Files":
                saveFiles();
                break;
            case "Exit":
                exit();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Save":
                createNewLine();
                break;
            case "Delete":
                deleteLine();
        }
    }

    private void exit() {
        System.exit(0);
    }

    private void openFiles() {
        ArrayList<InvoiceHeader> headers = null;
        ArrayList<InvoiceLines> lines = null;

        // header file block
        JFileChooser headerChooser = new JFileChooser();
        headerChooser.setMultiSelectionEnabled(false);
        int headerChoice = headerChooser.showOpenDialog(view);
        if (headerChoice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File headerFile = headerChooser.getSelectedFile();

        if (!headerFile.getName().substring(headerFile.getName().lastIndexOf(".") + 1).equals("csv")) {
            JOptionPane.showMessageDialog(view, "Please select a csv file.");
            return;
        }

        try {
            ReadHeader headerReader = new ReadHeader(headerFile);
            headers = headerReader.getHeaderArrayList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Please select a valid header file.");
        }

        // lines file block
        JFileChooser linesChooser = new JFileChooser();
        linesChooser.setMultiSelectionEnabled(false);
        int linesChoice = linesChooser.showOpenDialog(view);
        if (linesChoice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File linesFile = linesChooser.getSelectedFile();

        if (!linesFile.getName().substring(linesFile.getName().lastIndexOf(".") + 1).equals("csv")) {
            JOptionPane.showMessageDialog(view, "Please select a csv file.");
            return;
        }

        try {
            ReadDetails linesReader = new ReadDetails(linesFile);
            lines = linesReader.getDetailsArrayList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Please select a valid lines file.");
        }

        for (InvoiceHeader header : headers) {
            for (InvoiceLines line : lines) {
                if (line.getInvoice() == header.getInvoiceNum()) {
                    header.getLines().add(line);
                }
            }
        }
        System.out.println(headers);
        view.setTables(headers);
    }

    private void saveFiles() {
        // headers file block
        JFileChooser headerChooser = new JFileChooser();
        headerChooser.setMultiSelectionEnabled(false);
        int headerChoice = headerChooser.showSaveDialog(view);
        if (headerChoice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File headerFile = headerChooser.getSelectedFile();
        saveCSV(view.getInvoices(), headerFile);

        // lines file block
        JFileChooser linesChooser = new JFileChooser();
        linesChooser.setMultiSelectionEnabled(false);
        int linesChoice = linesChooser.showSaveDialog(view);
        if (linesChoice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File linesFile = linesChooser.getSelectedFile();
        saveCSV(linesFile, view.getLines());
    }

    private String toCSV(InvoiceHeader invoice) {
        String data
                = invoice.getInvoiceNum()
                + ","
                + invoice.getInvoiceDate()
                + ","
                + invoice.getCustomerName();
        return data;
    }

    private String toCSV(InvoiceLines line) {
        String data
                = line.getInvoice()
                + ","
                + line.getItemName()
                + ","
                + line.getItemPrice()
                + ","
                + line.getCount();
        return data;
    }

    private void saveCSV(ArrayList<InvoiceHeader> invoices, File file) {
        try ( PrintWriter printWriter = new PrintWriter(file)) {
            invoices.stream()
                    .map(this::toCSV)
                    .forEach(printWriter::println);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Please select a different file to save the header file.");
        }
    }

    private void saveCSV(File file, ArrayList<InvoiceLines> lines) {
        try ( PrintWriter printWriter = new PrintWriter(file)) {
            lines.stream()
                    .map(this::toCSV)
                    .forEach(printWriter::println);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Please select a different file to save the lines file.");
        }
    }

    private void createNewInvoice() {
        JTextField customerNameValue = new JTextField();
        JTextField invoiceDateValue = new JTextField();
        JComponent[] fields = new JComponent[]{
            new JLabel("Customer Name"),
            customerNameValue,
            new JLabel("Invoice Date"),
            invoiceDateValue,};
        int cnir = JOptionPane.showConfirmDialog(view, fields, "Add new Invoice", JOptionPane.PLAIN_MESSAGE);
        if (cnir != JOptionPane.OK_OPTION) {
            return;
        }
        Date date;
        try {
            SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
            date = parser.parse(invoiceDateValue.getText());
            InvoiceHeader newInvoice = new InvoiceHeader(view.getNextInvoiceNumber(), parser.format(date), customerNameValue.getText());
            view.addInvoice(newInvoice);
        } catch (ParseException ex) {
            //System.err.println("Please enter a valid date format 'dd-mm-yyyy'.");
            JOptionPane.showMessageDialog(view, "Please enter a valid date format 'dd-mm-yyyy'.");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid date format 'dd-mm-yyyy'.");
            //System.err.println("Please enter a valid date format 'dd-mm-yyyy'.");
        } catch (Exception haha) {
            JOptionPane.showMessageDialog(view, "Please enter a valid date format 'dd-mm-yyyy'.");
            //System.err.println("Please enter a valid date format 'dd-mm-yyyy'.");
        }
    }

    private void deleteInvoice() {
        view.deleteInvoice(view.getSelectedHeaderIndex());
    }

    private void createNewLine() {
        int index = view.getSelectedHeaderIndex();
        JTextField itemNameValue = new JTextField();
        JTextField itemCountValue = new JTextField();
        JTextField itemPriceValue = new JTextField();

        JComponent[] fields = new JComponent[]{
            new JLabel("Item Name"),
            itemNameValue,
            new JLabel("Item Count"),
            itemCountValue,
            new JLabel("Item Price"),
            itemPriceValue,};

        int cnlr = JOptionPane.showConfirmDialog(view, fields, "Add new Line", JOptionPane.PLAIN_MESSAGE);
        if (cnlr != JOptionPane.OK_OPTION) {
            return;
        }
        Double price;
        Double count;
        try {
            price = Double.parseDouble(itemPriceValue.getText());
            count = Double.parseDouble(itemCountValue.getText());
            InvoiceLines line = new InvoiceLines(
                    view.getCurrentInvoice().getInvoiceNum(),
                    itemNameValue.getText(),
                    price,
                    count
            );
            view.addLine(line, index);
        } catch (NumberFormatException | NullPointerException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid number.");
        }
    }

    private void deleteLine() {
        view.deleteLine(view.getSelectedLineIndex());
    }

}
