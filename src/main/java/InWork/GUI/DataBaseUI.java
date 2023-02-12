package InWork.GUI;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowEvent;

public class DataBaseUI extends JFrame {

    private JPanel MainPanel;
    private JTable KTWJTable;

    public DataBaseUI()
    {
        setContentPane(MainPanel);
        setTitle("UPF");
    }

    private void createUIComponents() {
        KTWTable model = new KTWTable();
        KTWJTable = new JTable(model);
        KTWJTable.getTableHeader().setReorderingAllowed(false);
        TableRowSorter<KTWTable> sorter = new TableRowSorter<KTWTable>(model);
        KTWJTable.setRowSorter(sorter);
    }
};
