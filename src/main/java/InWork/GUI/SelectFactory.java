package InWork.GUI;

import InWork.DataBase.DataBaseAPI;
import InWork.DataBase.DataStructure.DataKTWList;
import InWork.DataBase.DataStructure.LiveLoadKTW;
import InWork.DataBase.DataStructure.LiveLoadKTWList;
import InWork.DataBase.DataStructure.LiveLoadPOL;
import InWork.DataBase.ExcelAPI;
import InWork.Operations.Calculations;
import InWork.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
    private JLabel Logo;
    private JLabel Cred;
    private JButton LiveLoadPlan;
    private JButton cleanDBButton;
    private JButton settingsButton;

    private InWork.GUI.Settings SettingsUI;
    private DataBaseUI DataBaseTable;

    private SelectFactory thisframe;

    public SelectFactory() {

        thisframe = this;
        DataBaseTable = null;
        SettingsUI = null;

        setContentPane(MainPanel);
        setTitle("UPF");

        try {
            recordCount.setText(Integer.toString(DataBaseAPI.getInstance().getKtwCount()));
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(thisframe, "Błąd Bazy Danych");
            throwables.printStackTrace();
        }
        Date LastKTWImport = Settings.getInstance().getLastimportKTW();
        if (LastKTWImport != null) LastInsert.setText(LastKTWImport.toString());
        else                       LastInsert.setText("NULL");

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
                try {
                    if (DataKTWList.getInstance().ImpotrKTW())
                    {
                        JOptionPane.showMessageDialog(thisframe, "Import Zkończony pomyślnie");
                        Settings.getInstance().setLastimportKTW(new Date());
                        LastInsert.setText(Settings.getInstance().getLastimportKTW().toString());
                        Settings.getInstance().SaveSettings();
                        recordCount.setText(Integer.toString(DataBaseAPI.getInstance().getKtwCount()));
                    } else {
                        JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
                    }
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
                    throwables.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(thisframe, "Błąd z zapisaniem czasu ostatniego Importu");
                    throw new RuntimeException(ex);
                }
            }
        });
        LiveLoadPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ArrayList<LiveLoadKTW>> data = LiveLoadKTWList.getInstance().CreatData();
                if (data.size() > 0) {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony pomyślnie");
                    ExcelAPI.IrelandSplit(data.get(7));
                    JOptionPane.showMessageDialog(thisframe, "Irlandia w Pliku");
                    ArrayList<LiveLoadKTW> przetworzone = Calculations.ProductionPlan(data);
                    ExcelAPI.ProductionPlan(przetworzone);
                    JOptionPane.showMessageDialog(thisframe, "Plan w Pliku");
                    ArrayList<LiveLoadPOL> polska = Calculations.PolandLiveLoad(data.get(0));
                    ExcelAPI.Poland(polska);
                    JOptionPane.showMessageDialog(thisframe, "Polska w Pliku");
                } else
                {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
                }
            }
        });

        cleanDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DataBaseAPI.getInstance().cleanDBKTW();
                    DataKTWList.getInstance().cleanList();
                    Settings.getInstance().setLastimportKTW(null);
                    Settings.getInstance().SaveSettings();
                    LastInsert.setText("NULL");
                    recordCount.setText(Integer.toString(DataBaseAPI.getInstance().getKtwCount()));
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(thisframe, "błąd czyszczenia bazy Katowic");
                    throwables.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(thisframe, "Błąd z zapisaniem czasu ostatniego Importu");
                    throw new RuntimeException(ex);
                }
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (thisframe.SettingsUI == null) {
                    SettingsUI = new InWork.GUI.Settings();
                    SettingsUI.setSize(800, 600);
                    SettingsUI.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    SettingsUI.setVisible(true);
                } else {
                    SettingsUI.setVisible(true);
                }
            }
        });
    }

}
