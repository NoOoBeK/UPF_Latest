package InWork.GUI;

import InWork.Controllers.DataBaseController;
import InWork.Tasks.ImportKTWTask;
import InWork.DataStructure.Collection.DataKTWList;
import InWork.DataStructure.Collection.DataPrzewoznikList;
import InWork.DataStructure.LiveLoadKTW;
import InWork.DataStructure.Collection.LiveLoadKTWList;
import InWork.Settings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private JButton exitButton3;
    private JButton ImportPrzewoznik;
    private JButton ImportOrders;
    private JButton BazaDanychPrzewoznik;
    private JButton BazaDanychOrders;
    private JButton ImportUpdate;
    private JButton BazaDanychUpdate;
    private JButton Gliwice;

    private InWork.GUI.Settings SettingsUI;
    private KTWDataBaseUI DataBaseTable;
    private PrzewoznikDataBaseUI PrzewoznikDataBaseTable;
    private final SelectFactory thisframe;

    public SelectFactory() {

        thisframe = this;
        DataBaseTable = null;
        PrzewoznikDataBaseTable = null;
        SettingsUI = null;

        setContentPane(MainPanel);
        setTitle("UPF");

        exitButton.addActionListener(e -> System.exit(0));
        exitButton1.addActionListener(e -> System.exit(0));
        exitButton2.addActionListener(e -> System.exit(0));
        exitButton3.addActionListener(e -> System.exit(0));

        DataBaseView.addActionListener(e -> {
            if (thisframe.DataBaseTable == null) {
                DataBaseTable = new KTWDataBaseUI();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                DataBaseTable.setSize(screenSize.width, screenSize.height);
                DataBaseTable.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                DataBaseTable.setVisible(true);
            } else {
                DataBaseTable.setVisible(true);
            }
        });
        ImportKTW.addActionListener(e -> {
            Thread BackGroundTask= new Thread(new ImportKTWTask(FXCollections.observableArrayList()));
            BackGroundTask.setDaemon(true);
            BackGroundTask.start();
        });

        LiveLoadPlan.addActionListener(e -> {
            ArrayList<ArrayList<LiveLoadKTW>> data = LiveLoadKTWList.getInstance().CreateData();
            int count = 0;
            for (ArrayList<LiveLoadKTW> list : data)
            {
                count += list.size();
            }
            if (count > 0) {
                InWork.GUI.LiveLoadKTW form = new InWork.GUI.LiveLoadKTW(data);
                form.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                form.pack();
                form.setVisible(true);
            } else
            {
                JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
            }
        });
        cleanDBButton.addActionListener(e -> {
            try {
                DataBaseController.getInstance().cleanDBKTW();
                DataKTWList.getInstance().cleanList();
                Settings.getInstance().setLastimportKTW(null);
                Settings.getInstance().SaveSettings();
                LastInsert.setText("NULL");
                recordCount.setText(Integer.toString(DataBaseController.getInstance().getKtwCount()));
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(thisframe, "błąd czyszczenia bazy Katowic");
                throwables.printStackTrace();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(thisframe, "Błąd z zapisaniem czasu ostatniego Importu");
                throw new RuntimeException(ex);
            }
        });

        ImportPrzewoznik.addActionListener(e -> {
            try {
                if (DataPrzewoznikList.getInstance().ImpotrPrzewoznik())
                {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony pomyślnie");
                } else {
                    JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
                }
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(thisframe, "Import Zkończony niepowodzeniem");
                throwables.printStackTrace();
            }
        });
        BazaDanychPrzewoznik.addActionListener(e -> {
            if (thisframe.PrzewoznikDataBaseTable == null) {
                PrzewoznikDataBaseTable = new PrzewoznikDataBaseUI();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                PrzewoznikDataBaseTable.setSize(screenSize.width, screenSize.height);
                PrzewoznikDataBaseTable.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                PrzewoznikDataBaseTable.setVisible(true);
            } else {
                PrzewoznikDataBaseTable.setVisible(true);
            }
        });



        settingsButton.addActionListener(e -> {
            if (thisframe.SettingsUI == null) {
                SettingsUI = new InWork.GUI.Settings();
                SettingsUI.pack();
                SettingsUI.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                SettingsUI.setVisible(true);
            } else {
                SettingsUI.setVisible(true);
            }
        });
    }

    public void setDataBaseCount(String text)
    {
        recordCount.setText(text);
    }
    public void setLastImport(String text)
    {
        LastInsert.setText(text);
    }
}
