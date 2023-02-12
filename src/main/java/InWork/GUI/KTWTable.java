package InWork.GUI;

import InWork.DataBase.DataBaseAPI;
import InWork.DataBase.DataStructure.DataKTW;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class KTWTable extends AbstractTableModel {
    private ArrayList<String> columnNames;
    private ArrayList<DataKTW> dane;

    public KTWTable() {

        this.columnNames = new ArrayList<String>();
        this.columnNames.add("SKU");
        this.columnNames.add("Name");
        this.columnNames.add("Gross");
        this.columnNames.add("Net");
        this.columnNames.add("cs");
        this.columnNames.add("Height");
        this.columnNames.add("Paltype");
        this.columnNames.add("Qatime");
        this.columnNames.add("Dest");
        dane = DataBaseAPI.getInstance().getKTW();
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
        return this.dane.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col)
        {
            case 0: return dane.get(row).getSKU();
            case 1: return dane.get(row).getName();
            case 2: return dane.get(row).getGross();
            case 3: return dane.get(row).getNet();
            case 4: return dane.get(row).getCs();
            case 5: return dane.get(row).getHeight();
            case 6: return dane.get(row).getPaltype();
            case 7: return dane.get(row).getQatime();
            case 8: return dane.get(row).getDest();
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
