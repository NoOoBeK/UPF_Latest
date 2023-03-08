package InWork.GUI.Controllers;

import InWork.GUI.GUIController;
import InWork.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import java.io.File;
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
        DefaultChoiserPath.textProperty().bindBidirectional(settings.fileChoserPathProperty());
        LiveLoadKTWIrelandSavePath.textProperty().bindBidirectional(settings.liveLoadKTWIrelandSavePathProperty());
        LiveLoadKTWPlanSavePath.textProperty().bindBidirectional(settings.liveLoadKTWPlanSavePathProperty());
        LiveLoadKTWPolandSavePath.textProperty().bindBidirectional(settings.liveLoadKTWPolandSavePathProperty());
        LiveLoadKTWPolandStep.setTextFormatter(new TextFormatter<>(new LocalTimeStringConverter()));
        LiveLoadKTWPolandStep.textProperty().bindBidirectional(settings.liveLoadKTWPolandStepProperty(), new LocalTimeStringConverter());
        LiveLoadKTWMinPaletValueNoSkip.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        LiveLoadKTWMinPaletValueNoSkip.textProperty().bindBidirectional(settings.liveLoadKTWMinPaletValueNoSkipProperty().asObject(), new IntegerStringConverter());
        LiveLoadKTWStepToSkipTruck.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        LiveLoadKTWStepToSkipTruck.textProperty().bindBidirectional(settings.liveLoadKTWStepToSkipTruckProperty().asObject(), new IntegerStringConverter());
        DarkMode.selectedProperty().bindBidirectional(settings.darkModeProperty());
    }

    @FXML
    void ChangeFileChoiserPath(ActionEvent event) {
        File file = GUIController.DirectoryChoiser("Set Start Choser Path",(Stage)DarkMode.getScene().getWindow());
        if (file == null) return;
        Settings.getInstance().setFileChoserPath(file.getPath());
    }

    @FXML
    void ChangeLiveLoadKTWIrelandFile(ActionEvent event) {
        File file = GUIController.SaveExcelFile("Set Ireland Save File",(Stage)DarkMode.getScene().getWindow());
        if (file == null) return;
        Settings.getInstance().setLiveLoadKTWIrelandSavePath(file.getPath());
    }

    @FXML
    void ChangeLiveLoadKTWPlanFile(ActionEvent event) {
        File file = GUIController.SaveExcelFile("Set Plan Save File",(Stage)DarkMode.getScene().getWindow());
        if (file == null) return;
        Settings.getInstance().setLiveLoadKTWPlanSavePath(file.getPath());
    }

    @FXML
    void ChangeLiveLoadKTWPolandFile(ActionEvent event) {
        File file = GUIController.SaveExcelFile("Set Poland Save File",(Stage)DarkMode.getScene().getWindow());
        if (file == null) return;
        Settings.getInstance().setLiveLoadKTWPolandSavePath(file.getPath());
    }
    @FXML
    void RestoreDefaultSettings(ActionEvent event) {
        Settings.getInstance().DefaultSetting();
    }
    @FXML
    void CancelSaveSettings(ActionEvent event) {
        Settings.getInstance().RevertSetting();
    }
    @FXML
    void SaveSettings(ActionEvent event) {
        Settings.getInstance().SaveSettings();
    }
}
