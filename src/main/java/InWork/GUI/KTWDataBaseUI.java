package InWork.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class KTWDataBaseUI extends JFrame {

    private JPanel MainPanel;
    private JTable KTWJTable;
    private TableRowSorter<KTWTable> KTWRowSorter;
    private JTextField textField1;

    public KTWDataBaseUI()
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
                    KTWRowSorter.setRowFilter(null);
                } else {
                    KTWRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void createUIComponents() {
        KTWTable model = new KTWTable();
        KTWJTable = new JTable(model);
        KTWJTable.getTableHeader().setReorderingAllowed(false);
        KTWRowSorter = new TableRowSorter<KTWTable>(model);
        KTWJTable.setRowSorter(KTWRowSorter);
    }
@Override
    public void setVisible(boolean Visable)
    {
        super.setVisible(Visable);
        if (Visable) ((KTWTable)KTWJTable.getModel()).fireTableDataChanged();
    }
}
