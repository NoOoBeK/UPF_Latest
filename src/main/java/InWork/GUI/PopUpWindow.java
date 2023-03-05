package InWork.GUI;

import InWork.Settings;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class PopUpWindow {

    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder, String Description)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType);
            String style = Settings.getInstance().getStyle();
            if (!style.equals("")) alert.getDialogPane().getStylesheets().add(style);
            alert.setHeaderText(Heder);
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(Description)));
            alert.showAndWait();
        });
    }

    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType);
            String style = Settings.getInstance().getStyle();
            if (!style.equals("")) alert.getDialogPane().getStylesheets().add(style);
            alert.setHeaderText(Heder);
            alert.showAndWait();
        });
    }

    static public File ChoseFile (String Title, Stage stage)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Settings.getInstance().getFileChoserPath()));
        fileChooser.setTitle(Title);
        while (true) {
            File chosedFile = fileChooser.showOpenDialog(stage);
            if (chosedFile == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                String style = Settings.getInstance().getStyle();
                alert.setHeaderText("No file selected, are you sure you want to cancel?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) break;
            } else return chosedFile;
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
            if (chosedFile == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                String style = Settings.getInstance().getStyle();
                alert.setHeaderText("No file selected, are you sure you want to cancel?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) break;
            } else return chosedFile;
        }
        return null;
    }
}
