package InWork;

import InWork.GUI.SelectFactory;

import javax.swing.*;
import java.awt.*;

public class MainWorking {
    public MainWorking() {
    }

    public static void main(String[] args) {
        SelectFactory Window = new SelectFactory();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Window.setSize(screenSize.width, screenSize.height);
        Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Window.setVisible(true);

    }
}