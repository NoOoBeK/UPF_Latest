package InWork;

import SelectFactory;

import javax.swing.*;
import java.awt.*;

public class MainWorking {
    public MainWorking() {
    }

    public static void main(String[] args) {
        SelectFactory Window = new SelectFactory();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Window.setSize(850, 700);
        Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Window.setVisible(true);

    }
}