package InWork.GUI;

import InWork.Settings;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class PopUpWindow{

    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder, String Description)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType);
            if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("Dark.css").toExternalForm());
            alert.setHeaderText(Heder);
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(Description)));
            alert.showAndWait();
        });
    }

    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder)
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType);
            if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("Dark.css").toExternalForm());
            alert.setHeaderText(Heder);
            alert.showAndWait();
        });
    }

    static public File DirectoryChoiser (String Title, Stage stage)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(Settings.getInstance().getFileChoserPath()));
        directoryChooser.setTitle(Title);
        while (true) {
            File chosedFile = directoryChooser.showDialog(stage);
            if (chosedFile == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("Dark.css").toExternalForm());
                alert.setHeaderText("No directory selected, are you sure you want to cancel?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) break;
            } else return chosedFile;
        }
        return null;
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
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("Dark.css").toExternalForm());
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
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("Dark.css").toExternalForm());
                alert.setHeaderText("No file selected, are you sure you want to cancel?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) break;
            } else return chosedFile;
        }
        return null;
    }

    static public File SaveExcelFile(String Title, Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Settings.getInstance().getFileChoserPath()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx", "*.xls", "*.xlt"));
        fileChooser.setTitle(Title);
        while (true) {
            File chosedFile = fileChooser.showSaveDialog(stage);
            if (chosedFile == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("Dark.css").toExternalForm());
                alert.setHeaderText("No file selected, are you sure you want to cancel?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) break;
            } else return chosedFile;
        }
        return null;
    }
}
