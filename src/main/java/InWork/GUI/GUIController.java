package InWork.GUI;

import InWork.Controllers.DataBaseController;
import InWork.GUI.Controllers.TableKTWController;
import InWork.Settings;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

public class GUIController {

    static private GUIController Instance;
    static public GUIController getInstance()
    {
        if (Instance == null) showMsgWarrning(Alert.AlertType.ERROR, "GUI not Started!!!!");
        return Instance;
    }
    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder, String Description)    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType);
            if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() {
            }.getClass().getResource("Dark.css").toExternalForm());
            alert.setHeaderText(Heder);
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(Description)));
            alert.showAndWait();
        });
    }
    static public void showMsgWarrning(Alert.AlertType AlertType, String Heder)    {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType);
            if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object(){}.getClass().getResource("/Dark.css").toExternalForm());
            alert.setHeaderText(Heder);
            alert.showAndWait();
        });
    }
    static public File DirectoryChoiser (String Title, Stage stage)    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(Settings.getInstance().getFileChoserPath()));
        directoryChooser.setTitle(Title);
        while (true) {
            File chosedFile = directoryChooser.showDialog(stage);
            if (chosedFile == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("/Dark.css").toExternalForm());
                alert.setHeaderText("No directory selected, are you sure you want to cancel?");
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
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("/Dark.css").toExternalForm());
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
                if (Settings.getInstance().isDarkMode()) alert.getDialogPane().getStylesheets().add(new Object() { }.getClass().getResource("/Dark.css").toExternalForm());
                alert.setHeaderText("No file selected, are you sure you want to cancel?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) break;
            } else return chosedFile;
        }
        return null;
    }
    static public void StartGUI(Stage MainStage)
    {
        try {
            Parent root = FXMLLoader.load(new Object(){}.getClass().getResource("/fxml/SelectFactory.fxml"));
            Scene scene = new Scene(root);
            if (Settings.getInstance().isDarkMode()) scene.getStylesheets().add(new Object(){}.getClass().getResource("/Dark.css").toExternalForm());
            MainStage.setScene(scene);
            setDefaultStageProperty(MainStage);
            MainStage.show();
            Instance = new GUIController(MainStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public void setDefaultStageProperty(Stage stage)
    {
        Platform.runLater(() -> {
            stage.setTitle("Upfield");
            stage.getIcons().add(new Image("/logo.png"));
        });
    }
    private GUIController(Stage MainStage)
    {
        this.MainStage = MainStage;
        Settings.getInstance().darkModeProperty().addListener( (observable, oldValue, newValue) ->
        {
            Platform.runLater(() -> {
                if (newValue) {
                    MainStage.getScene().getStylesheets().add(getClass().getResource("/Dark.css").toExternalForm());
                    if (SettingsStage != null) SettingsStage.getScene().getStylesheets().add(getClass().getResource("/Dark.css").toExternalForm());
                    if (TableKTWStage != null) TableKTWStage.getScene().getStylesheets().add(getClass().getResource("/Dark.css").toExternalForm());
                } else {
                    MainStage.getScene().getStylesheets().remove(getClass().getResource("/Dark.css").toExternalForm());
                    if (SettingsStage != null) SettingsStage.getScene().getStylesheets().remove(getClass().getResource("/Dark.css").toExternalForm());
                    if (TableKTWStage != null) TableKTWStage.getScene().getStylesheets().remove(getClass().getResource("/Dark.css").toExternalForm());
                }
            });
        });
    }

    private final Stage MainStage;
    private Stage SettingsStage;
    private Stage TableKTWStage;
    private TableKTWController tableKTWController;

    public void CreatSetting()
    {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
            Scene scene = new Scene(root);
            if (Settings.getInstance().isDarkMode()) scene.getStylesheets().add(getClass().getResource("/Dark.css").toExternalForm());
            SettingsStage = new Stage();
            SettingsStage.setScene(scene);
            setDefaultStageProperty(SettingsStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void HandleSettings()
    {
        if (SettingsStage == null) {
            CreatSetting();
            SettingsStage.show();
        } else {
            if (SettingsStage.isIconified()) SettingsStage.setIconified(false);
            if (!SettingsStage.isShowing()) SettingsStage.show();
            SettingsStage.toFront();
        }
    }
    public void showSettings()    {
        if (Platform.isFxApplicationThread())
        {
            HandleSettings();
        }else {
            Platform.runLater(() -> {HandleSettings();});
        }
    }
    private void CreatTableKTW()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TableKTW.fxml"));
            Parent root = fxmlLoader.load();
            tableKTWController = (TableKTWController) fxmlLoader.getController();
            Scene scene = new Scene(root);
            if (Settings.getInstance().isDarkMode()) scene.getStylesheets().add(getClass().getResource("/Dark.css").toExternalForm());
            TableKTWStage = new Stage();
            TableKTWStage.setScene(scene);
            setDefaultStageProperty(TableKTWStage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void HandleTableKTW()
    {
        if (TableKTWStage == null)
        {
            CreatTableKTW();
            TableKTWStage.show();
            Date LastDate = Settings.getInstance().getLastimportKTW();
            String LastDateText = LastDate != null ? LastDate.toString() : "Never";
            try {
                showMsgWarrning(Alert.AlertType.INFORMATION, "Record In Databas: " + DataBaseController.getInstance().getKtwCount() + "\n" +
                        "Last Import KTW: " + LastDateText);
            } catch (SQLException e) {
                showMsgWarrning(Alert.AlertType.INFORMATION, "Cannot Get Info From Data Base"+ "\n" +
                        "Last Import KTW: " + LastDateText);
                throw new RuntimeException(e);
            }
        }
        else {
            if (TableKTWStage.isIconified()) TableKTWStage.setIconified(false);
            if (!TableKTWStage.isShowing()) TableKTWStage.show();
            TableKTWStage.toFront();
        }
    }
    public void ShowTableKTW()    {
        if (Platform.isFxApplicationThread())
        {
            HandleTableKTW();
        }else {
            Platform.runLater(() -> {HandleTableKTW();});
        }
    }

    public void CallImportKTW() {
        if (tableKTWController == null) HandleTableKTW();
        while(tableKTWController == null)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        tableKTWController.ImportKTW(null);
    }
}
