package InWork.GUI;

import InWork.DataStructure.Collection.DataKTWList;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class KTWTable extends AbstractTableModel {
    private final ArrayList<String> columnNames;

    public KTWTable() {

        this.columnNames = new ArrayList<>();
        this.columnNames.add("SKU");
        this.columnNames.add("Name");
        this.columnNames.add("Gross");
        this.columnNames.add("Net");
        this.columnNames.add("cs");
        this.columnNames.add("Height");
        this.columnNames.add("Paltype");
        this.columnNames.add("Qatime");
        this.columnNames.add("Dest");
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    @Override
    public int getRowCount() {
        return DataKTWList.getInstance().getData().size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col)
        {
            case 0: return DataKTWList.getInstance().getData().get(row).getSKU();
            case 1: return DataKTWList.getInstance().getData().get(row).getName();
            case 2: return DataKTWList.getInstance().getData().get(row).getGross();
            case 3: return DataKTWList.getInstance().getData().get(row).getNet();
            case 4: return DataKTWList.getInstance().getData().get(row).getCs();
            case 5: return DataKTWList.getInstance().getData().get(row).getHeight();
            case 6: return DataKTWList.getInstance().getData().get(row).getPaltype();
            case 7: return DataKTWList.getInstance().getData().get(row).getQatime();
            case 8: return DataKTWList.getInstance().getData().get(row).getDest();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Class getColumnClass(int col) {

        switch (col)
        {
            case 0:
            case 7:
                return Integer.class;
            case 2:
            case 3:
            case 4:
            case 5:
                return double.class;
        }
        return String.class;
    }

}
