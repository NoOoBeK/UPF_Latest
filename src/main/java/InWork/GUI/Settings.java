package InWork.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Settings extends JFrame {
    private JPanel MainPanel;
    private JTextField pathText;
    private JButton chosePathButton;
    private JButton saveSettingsButton;
    private Settings thisframe;
public Settings() {
    thisframe = this;
    pathText.setText(InWork.Settings.getInstance().getFileChoserPath());

    setContentPane(MainPanel);
    setTitle("Settings");
    chosePathButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(pathText.getText());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(thisframe);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                pathText.setText(fileChooser.getSelectedFile().getPath());
            }
        }
    });
    saveSettingsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                InWork.Settings.getInstance().setFileChoserPath(pathText.getText());
                InWork.Settings.getInstance().SaveSettings();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(thisframe,"Error with Saving Settings");
                throw new RuntimeException(ex);
            }
        }
    });
}
}
