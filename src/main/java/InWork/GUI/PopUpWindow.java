package InWork.GUI;

import InWork.Settings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class PopUpWindow {

    static public Alert showMsgWarrning(Alert.AlertType AlertType, String Heder, String Description)
    {
        Alert alert = new Alert(AlertType);
        String style = Settings.getInstance().getStyle();
        if (!style.equals("")) alert.getDialogPane().getStylesheets().add(style);
        alert.setHeaderText(Heder);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(Description)));
        alert.showAndWait();
        return alert;
    }

    static public Alert showMsgWarrning(Alert.AlertType AlertType, String Heder)
    {
        Alert alert = new Alert(AlertType);
        String style = Settings.getInstance().getStyle();
        if (!style.equals("")) alert.getDialogPane().getStylesheets().add(style);
        alert.setHeaderText(Heder);
        alert.showAndWait();
        return alert;
    }

    static public File ChoseFile (String Title, Stage stage)
    {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File(Settings.getInstance().getFileChoserPath()));
        //fileChooser.setTitle(Title);
        while (true) {
            File chosedFile = fileChooser.showOpenDialog(stage);
            if (chosedFile == null)
            {
                Alert alert = PopUpWindow.showMsgWarrning(Alert.AlertType.CONFIRMATION, "No file selected, are you sure you want to cancel?");
                if (alert.getResult() == ButtonType.OK) break;
            }
            else return chosedFile;
        }
        return null;
    }

    static public File ChoseExcelFile(String Title, Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Settings.getInstance().getFileChoserPath()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx", "*.xls", "*.xlt"));
        fileChooser.setTitle(Title);
        while (true) {
            File chosedFile = fileChooser.showOpenDialog(stage);
            if (chosedFile == null)
            {
                Alert alert = PopUpWindow.showMsgWarrning(Alert.AlertType.CONFIRMATION, "No file selected, are you sure you want to cancel?");
                if (alert.getResult() == ButtonType.OK) break;
            }
            else return chosedFile;
        }
        return null;
    }
}
