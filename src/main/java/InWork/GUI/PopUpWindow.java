package InWork.GUI;

import InWork.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class PopUpWindow {

    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder, String Description)
    {
        Alert alert = new Alert(AlertType);
        String style = Settings.getInstance().getStyle();
        if (!style.equals("")) alert.getDialogPane().getStylesheets().add(style);
        alert.setHeaderText(Heder);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(Description)));
        alert.showAndWait();
    }

    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder)
    {
        Alert alert = new Alert(AlertType);
        String style = Settings.getInstance().getStyle();
        if (!style.equals("")) alert.getDialogPane().getStylesheets().add(style);
        alert.setHeaderText(Heder);
        alert.showAndWait();
    }
}
