package InWork.GUI.Controllers;

import InWork.GUI.PopUpWindow;
import InWork.Settings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Label DefaultChoiserPath;
    @FXML
    private Label LiveLoadKTWPolandSavePath;
    @FXML
    private Label LiveLoadKTWIrelandSavePath;
    @FXML
    private Label LiveLoadKTWPlanSavePath;
    @FXML
    private TextField LiveLoadKTWMinPaletValueNoSkip;
    @FXML
    private TextField LiveLoadKTWPolandStep;
    @FXML
    private TextField LiveLoadKTWStepToSkipTruck;
    @FXML
    private CheckBox DarkMode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Settings settings = Settings.getInstance();
        DefaultChoiserPath.textProperty().bind(settings.fileChoserPathProperty());
        LiveLoadKTWIrelandSavePath.textProperty().bind(settings.liveLoadKTWIrelandSavePathProperty());
        LiveLoadKTWPlanSavePath.textProperty().bind(settings.liveLoadKTWPlanSavePathProperty());
        LiveLoadKTWPolandSavePath.textProperty().bind(settings.liveLoadKTWPolandSavePathProperty());
        LiveLoadKTWPolandStep.textProperty().bind(settings.liveLoadKTWPolandStepProperty().asString());
        LiveLoadKTWPolandStep.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        LiveLoadKTWMinPaletValueNoSkip.textProperty().bind(settings.liveLoadKTWMinPaletValueNoSkipProperty().asString());
        LiveLoadKTWMinPaletValueNoSkip.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        LiveLoadKTWStepToSkipTruck.textProperty().bind(settings.liveLoadKTWStepToSkipTruckProperty().asString());
        LiveLoadKTWStepToSkipTruck.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        DarkMode.selectedProperty().bind(settings.darkModeProperty());
    }

    @FXML
    void ChangeFileChoiserPath(ActionEvent event) {
        File file = PopUpWindow.DirectoryChoiser("Set Start Choser Path",(Stage)DarkMode.getScene().getWindow());
        Settings.getInstance().setFileChoserPath(file.getPath());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING,"Save Settings Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void ChangeLiveLoadKTWIrelandFile(ActionEvent event) {
        File file = PopUpWindow.SaveExcelFile("Set Ireland Save File",(Stage)DarkMode.getScene().getWindow());
        Settings.getInstance().setLiveLoadKTWIrelandSavePath(file.getPath());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING,"Save Settings Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void ChangeLiveLoadKTWPlanFile(ActionEvent event) {
        File file = PopUpWindow.SaveExcelFile("Set Plan Save File",(Stage)DarkMode.getScene().getWindow());
        Settings.getInstance().setLiveLoadKTWPlanSavePath(file.getPath());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING,"Save Settings Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void ChangeLiveLoadKTWPolandFile(ActionEvent event) {
        File file = PopUpWindow.SaveExcelFile("Set Poland Save File",(Stage)DarkMode.getScene().getWindow());
        Settings.getInstance().setLiveLoadKTWPolandSavePath(file.getPath());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING,"Save Settings Error", e.getMessage());
            e.printStackTrace();
        }
    }
}
