package InWork.GUI;

import InWork.DataStructure.LiveLoadPOL;
import InWork.Controllers.ExcelController;
import InWork.Operations.Calculations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LiveLoadKTW extends JFrame {

    private JButton irlandiaButton;
    private JButton planButton;
    private JButton polskaButton;
    private JPanel MainPanel;
    private LiveLoadKTW thisFrame;
    private ArrayList<ArrayList<InWork.DataStructure.LiveLoadKTW>> List;

    public LiveLoadKTW(ArrayList<ArrayList<InWork.DataStructure.LiveLoadKTW>> list) {
        setContentPane(MainPanel);
        setTitle("UPF");

        thisFrame = this;
        List = list;

        irlandiaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcelController.IrelandSplit(List.get(7));
                JOptionPane.showMessageDialog(thisFrame, "Irlandia w Pliku");
            }
        });
        planButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<InWork.DataStructure.LiveLoadKTW> przetworzone = Calculations.ProductionPlan(List);
                ExcelController.ProductionPlan(przetworzone);
                JOptionPane.showMessageDialog(thisFrame, "Plan w Pliku");
            }
        });
        polskaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<LiveLoadPOL> polska = Calculations.PolandLiveLoad(List.get(0));
                ExcelController.Poland(polska);
                JOptionPane.showMessageDialog(thisFrame, "Polska w Pliku");
            }
        });
    }
}
