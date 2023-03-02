package InWork.Controllers;

import InWork.DataStructure.Collection.DataKTWList;
import InWork.Settings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class SelectFactoryController implements Initializable {

    private SimpleStringProperty DBCountText;
    private SimpleStringProperty LastImportText;
    @FXML
    Label DBCount;
    @FXML
    Label LastImport;

    public void ImportKTW(ActionEvent actionEvent) {
        ImportController.ImpotrKTW();
        DBCountText.set("DB Count: " +String.valueOf(DataKTWList.getInstance().getData().size()));
        Settings.getInstance().setLastimportKTW(new Date());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LastImportText.set("Import: " +Settings.getInstance().getLastimportKTW().toString());
    }

    public void ViewDataKTW(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DBCountText = new SimpleStringProperty("DB Count: " + String.valueOf(DataBaseController.getInstance().getKtwCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        LastImportText = new SimpleStringProperty("Import: " + Settings.getInstance().getLastimportKTW().toString());
        DBCount.textProperty().bindBidirectional(DBCountText);
        LastImport.textProperty().bindBidirectional(LastImportText);
    }
}
