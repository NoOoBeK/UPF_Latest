package InWork.GUI.Controllers;

import InWork.Controllers.DataBaseController;
import InWork.GUI.PopUpWindow;
import InWork.Settings;
import InWork.Tasks.LiveLoadKTWTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class SelectFactoryController implements Initializable {

    @FXML
    private CheckBox LiveLoadIreland;
    @FXML
    private CheckBox LiveLoadPlan;
    @FXML
    private CheckBox LiveLoadPoland;
    @FXML
    Label LiveLoadProgressText;
    @FXML
    ProgressBar LiveLoadProgressBar;
    private SimpleBooleanProperty LiveLoadRunning;


    private Stage StageTableKTW;
    private TableKTWController ControlletTableKTW;
    private Stage StageSettings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LiveLoadRunning = new SimpleBooleanProperty(false);
    }

    private void CreatKTWDBTable ()
    {
        try {
            if (StageTableKTW == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TableKTW.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                if (Settings.getInstance().isDarkMode()) scene.getStylesheets().add(getClass().getResource("Dark.css").toExternalForm());
                StageTableKTW = new Stage();
                StageTableKTW.getIcons().add(new Image("/logo.png"));
                StageTableKTW.setScene(scene);
                StageTableKTW.setTitle("Upfield");
                StageTableKTW.show();
                ControlletTableKTW = fxmlLoader.getController();
                Date LastDate = Settings.getInstance().getLastimportKTW();
                String LastDateText = "Never";
                if (LastDate != null) LastDateText = LastDate.toString();
                PopUpWindow.showMsgWarrning(Alert.AlertType.INFORMATION, "Record In Databas: " + DataBaseController.getInstance().getKtwCount() + "\n" +
                        "Last Import KTW: " + LastDateText);
            } else
            {
                if (StageTableKTW.isIconified()) StageTableKTW.setIconified(false);
                if (!StageTableKTW.isShowing())StageTableKTW.show();
                StageTableKTW.toFront();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void ImportKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
        ControlletTableKTW.ImportKTW(null);
    }
    public void ViewDataKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
    }
    public void LiveLoadKTW(ActionEvent actionEvent) {
        if (!LiveLoadRunning.getValue())
        {
            LiveLoadRunning.unbind();
            LiveLoadRunning.setValue(true);

            File SouceFile = PopUpWindow.ChoseExcelFile("Select Katowice Data Excel", (Stage)LiveLoadIreland.getScene().getWindow());
            LiveLoadKTWTask task = new LiveLoadKTWTask(SouceFile, LiveLoadIreland.isSelected(), LiveLoadPlan.isSelected(), LiveLoadPoland.isSelected());
            LiveLoadProgressBar.progressProperty().unbind();
            LiveLoadProgressBar.progressProperty().bind(task.progressProperty());
            LiveLoadRunning.unbind();
            LiveLoadRunning.bind(task.runningProperty());
            LiveLoadProgressText.textProperty().unbind();
            LiveLoadProgressText.textProperty().bind(task.messageProperty());
            Thread BackGroundTask = new Thread(task);
            BackGroundTask.setDaemon(true);
            BackGroundTask.start();
        } else PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING, "LiveLoadKTW in Progress");
    }
    @FXML
    void OpenSettings(ActionEvent event) {
        try {
            if (StageSettings == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                if (Settings.getInstance().isDarkMode()) scene.getStylesheets().add(getClass().getResource("Dark.css").toExternalForm());
                StageSettings = new Stage();
                StageSettings.getIcons().add(new Image("/logo.png"));
                StageSettings.setScene(scene);
                StageSettings.setTitle("Upfield");
                StageSettings.show();
                Date LastDate = Settings.getInstance().getLastimportKTW();
                String LastDateText = "Never";
                if (LastDate != null) LastDateText = LastDate.toString();
                PopUpWindow.showMsgWarrning(Alert.AlertType.INFORMATION, "Record In Databas: " + DataBaseController.getInstance().getKtwCount() + "\n" +
                        "Last Import KTW: " + LastDateText);
            } else
            {
                if (StageSettings.isIconified()) StageSettings.setIconified(false);
                if (!StageSettings.isShowing())StageSettings.show();
                StageSettings.toFront();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
