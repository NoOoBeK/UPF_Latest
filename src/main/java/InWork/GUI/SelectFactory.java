package InWork.GUI;

import InWork.DataBase.DataBaseAPI;
import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;

public class SelectFactory extends JFrame{
    private JButton exitButton;
    private JPanel MainPanel;
    private JTabbedPane tabbedPane1;
    private JButton exitButton2;
    private JButton exitButton1;
    private JButton DataBaseView;
    private JLabel recordCount;
    private JLabel LastInsert;
    private JButton ImportKTW;

    public void setDataBaseTable(DataBaseUI dataBaseTable) {
        DataBaseTable = dataBaseTable;
    }

    private DataBaseUI DataBaseTable;

    private SelectFactory thisframe;

    public SelectFactory() {

        thisframe = this;
        DataBaseTable = null;

        setContentPane(MainPanel);
        setTitle("UPF");

        recordCount.setText(Integer.toString(DataBaseAPI.getInstance().getKtwCount()));
        LastInsert.setText(DataBaseAPI.getInstance().getKTWLastInsert().toString());
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        DataBaseView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (thisframe.DataBaseTable == null) {
                    DataBaseTable = new DataBaseUI();
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    DataBaseTable.setSize(screenSize.width, screenSize.height);
                    DataBaseTable.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    DataBaseTable.setVisible(true);
                } else {
                    DataBaseTable.setVisible(true);
                }
            }
        });
        ImportKTW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ExcelAPI.ImportKTW(thisframe))
                {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony pomyślnie");
                } else {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
                }
            }
        });
    }


}
