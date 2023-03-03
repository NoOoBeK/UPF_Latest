package InWork.Controllers.javaFX;

import InWork.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectFactoryController {

    private Stage StageTableKTW;
    private TableKTWController ControlletTableKTW;

    private void CreatKTWDBTable ()
    {
        try {
            if (StageTableKTW == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TableKTW.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Scene scene = new Scene(root);
                StageTableKTW = new Stage();
                StageTableKTW.setScene(scene);
                StageTableKTW.show();
                ControlletTableKTW = fxmlLoader.getController();
            } else
            {
                if (StageTableKTW.isIconified()) StageTableKTW.setIconified(false);
                if (!StageTableKTW.isShowing())StageTableKTW.show();
                StageTableKTW.toFront();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ImportKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
        ControlletTableKTW.ImportKTW(null);
    }

    public void ViewDataKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
    }


    public void switchTheme(ActionEvent actionEvent) {
        RadioMenuItem caller = (RadioMenuItem) actionEvent.getSource();

        String Was = Settings.getInstance().getStyle();
        Scene scene = caller.getParentPopup().getOwnerWindow().getScene();

        if (!Was.equals("Normal")) scene.getStylesheets().remove(getClass().getResource("/theme/"+ Was +".css").toExternalForm());
        String Is = caller.getText();
        if (!Is.equals("Normal")) scene.getStylesheets().add(getClass().getResource("/theme/"+ Is +".css").toExternalForm());
        Settings.getInstance().setStyle(caller.getText());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
