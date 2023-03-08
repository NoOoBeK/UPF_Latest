package InWork.GUI.Controllers;

import InWork.GUI.GUIController;
import InWork.Tasks.LiveLoadKTWTask;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LiveLoadRunning = new SimpleBooleanProperty(false);
    }
    @FXML
    public void ImportKTW(ActionEvent actionEvent) {
        GUIController.getInstance().CallImportKTW();
    }
    @FXML
    public void ViewDataKTW(ActionEvent actionEvent) {
        GUIController.getInstance().ShowTableKTW();
    }
    @FXML
    public void LiveLoadKTW(ActionEvent actionEvent) {
        if (!LiveLoadRunning.getValue())
        {
            File SouceFile = GUIController.ChoseExcelFile("Select Katowice Data Excel", (Stage)LiveLoadIreland.getScene().getWindow());
            if (!SouceFile.exists())
            {
                GUIController.showMsgWarrning(Alert.AlertType.WARNING, "File " + SouceFile.toString() + " not exist");
                return;
            } if (!SouceFile.canRead())
        {
            GUIController.showMsgWarrning(Alert.AlertType.WARNING, "File " + SouceFile.toString() + " can't be Read");
            return;
        }
            ArrayList<Pair<LocalDateTime, Integer>> PalletToAdd = new ArrayList<>();
            if(LiveLoadPoland.isSelected()) PalletToAdd = GUIController.getInstance().ShowLiveLoadPolandInput((Stage)LiveLoadProgressBar.getScene().getWindow());

            LiveLoadRunning.unbind();
            LiveLoadRunning.setValue(true);


            LiveLoadKTWTask task = new LiveLoadKTWTask(SouceFile, LiveLoadIreland.isSelected(), LiveLoadPlan.isSelected(), LiveLoadPoland.isSelected(), PalletToAdd);
            LiveLoadProgressBar.progressProperty().unbind();
            LiveLoadProgressBar.progressProperty().bind(task.progressProperty());
            LiveLoadRunning.unbind();
            LiveLoadRunning.bind(task.runningProperty());
            LiveLoadProgressText.textProperty().unbind();
            LiveLoadProgressText.textProperty().bind(task.messageProperty());
            Thread BackGroundTask = new Thread(task);
            BackGroundTask.setDaemon(true);
            BackGroundTask.start();
        } else GUIController.showMsgWarrning(Alert.AlertType.WARNING, "LiveLoadKTW in Progress");
    }
    @FXML
    void OpenSettings(ActionEvent event) {
        GUIController.getInstance().showSettings();
    }
}
