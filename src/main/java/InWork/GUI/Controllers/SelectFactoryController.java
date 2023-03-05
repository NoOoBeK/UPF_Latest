package InWork.GUI.Controllers;

import InWork.Controllers.DataBaseController;
import InWork.GUI.PopUpWindow;
import InWork.Settings;
import InWork.Tasks.LiveLoadKTWTask;
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



    @FXML
    private RadioMenuItem ThemeDefault;
    @FXML
    private RadioMenuItem ThemeDark;

    private Stage StageTableKTW;
    private TableKTWController ControlletTableKTW;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup ThemeGroup = new ToggleGroup();
        ThemeDefault.setToggleGroup(ThemeGroup);
        ThemeDark.setUserData(this.getClass().getResource("/theme/Dark.css").toExternalForm());
        ThemeDark.setToggleGroup(ThemeGroup);
        ThemeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable);

            if (oldValue.getUserData() != null)
            {
                LiveLoadIreland.getScene().getStylesheets().remove(oldValue.getUserData());
                if (StageTableKTW != null) StageTableKTW.getScene().getStylesheets().remove(oldValue.getUserData());
            }
            String Style = "";
            if (newValue.getUserData() != null)
            {
                Style = (String) newValue.getUserData();
                LiveLoadIreland.getScene().getStylesheets().add(Style);
                if (StageTableKTW != null) StageTableKTW.getScene().getStylesheets().add(Style);
            }
            Settings.getInstance().setStyle(Style);
            try {
                Settings.getInstance().SaveSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        LiveLoadRunning = new SimpleBooleanProperty(false);
    }

    private void CreatKTWDBTable ()
    {
        try {
            if (StageTableKTW == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TableKTW.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                for (String style : LiveLoadIreland.getScene().getStylesheets())
                {
                    scene.getStylesheets().add(style);
                }
                StageTableKTW = new Stage();
                StageTableKTW.getIcons().add(new Image("/logo.png"));
                StageTableKTW.setScene(scene);
                StageTableKTW.setTitle("Upfield");
                StageTableKTW.show();
                ControlletTableKTW = fxmlLoader.getController();
                Date LastDate = Settings.getInstance().getLastimportKTW();
                String LastDateText = "Never";
                if (LastDate != null) LastDateText = LastDate.toString();
                PopUpWindow.showMsgWarrning(Alert.AlertType.INFORMATION, "Record In Databas: " + String.valueOf(DataBaseController.getInstance().getKtwCount()) + "\n" +
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
            LiveLoadRunning.setValue(true);


            LiveLoadKTWTask task = new LiveLoadKTWTask(LiveLoadIreland.isSelected(), LiveLoadPlan.isSelected(), LiveLoadPoland.isSelected());
            LiveLoadProgressBar.progressProperty().unbind();
            LiveLoadProgressBar.progressProperty().bind(task.progressProperty());
            LiveLoadRunning.unbind();
            LiveLoadRunning.bind(task.runningProperty());
            Thread BackGroundTask = new Thread(task);
            BackGroundTask.setDaemon(true);
            BackGroundTask.start();
        } else PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING, "LiveLoadKTW in Progress");
    }
}
