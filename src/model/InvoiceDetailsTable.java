package model;

import javax.swing.table.AbstractTableModel;

public class InvoiceDetailsTable extends AbstractTableModel {

    private final InvoiceHeader invoice;
    private final String[] columnNames = new String[]{
        "No", "Name", "Price", "Count", "Total"
    };
    private final Class[] columnTypes = new Class[]{
        Integer.class, String.class, Double.class, Double.class, Double.class
    };

    public InvoiceDetailsTable(InvoiceHeader invoice) {
        this.invoice = invoice;
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
        return this.invoice.getLines().size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLines line = this.invoice.getLines().get(rowIndex);
        // "No", "Name", "Price", "Count", "Total"
        switch (columnIndex) {
            case 0:
                return line.getInvoice();
            case 1:
                return line.getItemName();
            case 2:
                return line.getItemPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotal();
            default:
                throw new AssertionError();
        }
    }

}
