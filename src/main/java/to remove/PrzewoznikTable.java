import InWork.DataStructure.Collection.DataPrzewoznikList;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PrzewoznikTable extends AbstractTableModel {
    private ArrayList<String> columnNames;

    public PrzewoznikTable() {

        this.columnNames = new ArrayList<>();
        this.columnNames.add("ID");
        this.columnNames.add("Przewoznik");
        this.columnNames.add("Mail");

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
        return DataPrzewoznikList.getInstance().getData().size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col)
        {
            case 0: return DataPrzewoznikList.getInstance().getData().get(row).getPrzewoznikID();
            case 1: return DataPrzewoznikList.getInstance().getData().get(row).getPrzewoznik();
            case 2: return DataPrzewoznikList.getInstance().getData().get(row).getMail();

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
