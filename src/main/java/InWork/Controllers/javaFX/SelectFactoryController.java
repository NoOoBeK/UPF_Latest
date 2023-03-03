package InWork.Controllers.javaFX;

import InWork.Controllers.DataBaseController;
import InWork.DataStructure.Collection.DataKTWList;
import InWork.Settings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class SelectFactoryController implements Initializable {

    @FXML
    Label DBCount;
    @FXML
    Label LastImport;

    private SimpleStringProperty DBCountText = new SimpleStringProperty();
    private SimpleStringProperty LastImportText = new SimpleStringProperty();
    private Stage StageTableKTW;
    private TableKTWController ControlletTableKTW;

    private void CreatKTWDBTable()
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
        DBCountText.set("DB Count: " + String.valueOf(DataKTWList.getInstance().getData().size()));
        Settings.getInstance().setLastimportKTW(new Date());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Date LastDate = Settings.getInstance().getLastimportKTW();
        String DateText = "";
        if (LastDate != null) DateText = LastDate.toString();
        LastImportText.set("Import: " + DateText);
    }

    public void ViewDataKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DBCountText = new SimpleStringProperty("DB Count: " + String.valueOf(DataBaseController.getInstance().getKtwCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Date LastDate = Settings.getInstance().getLastimportKTW();
        String DateText = "";
        if (LastDate != null) DateText = LastDate.toString();
        LastImportText.set("Import: " + DateText);
        DBCount.textProperty().bindBidirectional(DBCountText);
        LastImport.textProperty().bindBidirectional(LastImportText);

    }
}
