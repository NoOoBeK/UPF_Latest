package InWork.GUI;

import InWork.DataBase.DataStructure.LiveLoadPOL;
import InWork.DataBase.ExcelAPI;
import InWork.Operations.Calculations;
import InWork.Settings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class LiveLoadKTW extends JFrame {

    private JButton irlandiaButton;
    private JButton planButton;
    private JButton polskaButton;
    private JPanel MainPanel;
    private LiveLoadKTW thisFrame;
    private ArrayList<ArrayList<InWork.DataBase.DataStructure.LiveLoadKTW>> List;

    public LiveLoadKTW(ArrayList<ArrayList<InWork.DataBase.DataStructure.LiveLoadKTW>> list) {
        setContentPane(MainPanel);
        setTitle("UPF");

        thisFrame = this;
        List = list;

        irlandiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcelAPI.IrelandSplit(List.get(7));
                JOptionPane.showMessageDialog(thisFrame, "Irlandia w Pliku");
            }
        });
        planButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<InWork.DataBase.DataStructure.LiveLoadKTW> przetworzone = Calculations.ProductionPlan(List);
                ExcelAPI.ProductionPlan(przetworzone);
                JOptionPane.showMessageDialog(thisFrame, "Plan w Pliku");
            }
        });
        polskaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<LiveLoadPOL> polska = Calculations.PolandLiveLoad(List.get(0));
                ExcelAPI.Poland(polska);
                JOptionPane.showMessageDialog(thisFrame, "Polska w Pliku");
            }
        });
    }
}
