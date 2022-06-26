package model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sam
 */
public class InvoiceHeaderTable extends AbstractTableModel {

    private final List<InvoiceHeader> invoices;
    private final String[] columnNames = new String[]{
        "No", "Date", "Customer", "Total"
    };
    private final Class[] columnTypes = new Class[]{
        Integer.class, String.class, String.class, Double.class
    };

    public InvoiceHeaderTable(List<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.columnTypes[columnIndex];
    }

    @Override
    public int getRowCount() {
        return this.invoices.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = this.invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return invoice.getInvoiceNum();
            case 1:
                return invoice.getInvoiceDate();
            case 2:
                return invoice.getCustomerName();
            case 3:
                return invoice.getInvoiceTotal();
            default:
                throw new AssertionError();
        }
    }

}
