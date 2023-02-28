package InWork.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

public class PrzewoznikDataBaseUI extends JFrame{

    private JPanel MainPanel;
    private JTable PrzewoznikJTable;
    private JTextField textField1;
    private TableRowSorter<PrzewoznikTable> PrzewoznikRowSorter;

    public PrzewoznikDataBaseUI()
    {
        setContentPane(MainPanel);
        setTitle("UPF");
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                FilterUpdate();
            }
            public void removeUpdate(DocumentEvent e) {
                FilterUpdate();
            }
            public void insertUpdate(DocumentEvent e) {
                FilterUpdate();
            }

            public void FilterUpdate() {
                String text = textField1.getText();
                if (text.trim().length() == 0) {
                    PrzewoznikRowSorter.setRowFilter(null);
                } else {
                    PrzewoznikRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void createUIComponents() {
        PrzewoznikTable model = new PrzewoznikTable();
        PrzewoznikJTable = new JTable(model);
        PrzewoznikJTable.getTableHeader().setReorderingAllowed(false);
        PrzewoznikRowSorter = new TableRowSorter<PrzewoznikTable>(model);
        PrzewoznikJTable.setRowSorter(PrzewoznikRowSorter);
    }
    @Override
    public void setVisible(boolean Visable)
    {
        super.setVisible(Visable);
        if (Visable) ((PrzewoznikTable)PrzewoznikJTable.getModel()).fireTableDataChanged();
    }

}
