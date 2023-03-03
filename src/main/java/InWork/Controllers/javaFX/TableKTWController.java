package InWork.Controllers.javaFX;

import InWork.Tasks.ImportKTWTask;
import InWork.DataStructure.Collection.DataKTWList;
import InWork.DataStructure.DataKTW;
import InWork.Settings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class TableKTWController implements Initializable {

    @FXML
    TableView Table;
    @FXML
    private TableColumn<DataKTW, Integer> SKU;
    @FXML
    private TableColumn<DataKTW, String> Name;
    @FXML
    private TableColumn<DataKTW, Double> Gross;
    @FXML
    private TableColumn<DataKTW, Double> Net;
    @FXML
    private TableColumn<DataKTW, Double> cs;
    @FXML
    private TableColumn<DataKTW, Double> Height;
    @FXML
    private TableColumn<DataKTW, String> PalletType;
    @FXML
    private TableColumn<DataKTW, Integer> QATime;
    @FXML
    private TableColumn<DataKTW, String> Destination;

    @FXML
    private Button ImportData;
    @FXML
    private Label LastImport;
    @FXML
    private ProgressBar ProgresBar;

    private SimpleStringProperty LastImportText = new SimpleStringProperty();
    private ObservableList<DataKTW> TableList;
    private SimpleDoubleProperty ProgresValue;
    public void ImportKTW(ActionEvent actionEvent) {

        ImportKTWTask task = new ImportKTWTask(TableList);
        Thread BackGroundTask= new Thread();
        BackGroundTask.setDaemon(true);
        BackGroundTask.start();

        Date LastDate = Settings.getInstance().getLastimportKTW();
        String DateText = "";
        if (LastDate != null) DateText = LastDate.toString();
        LastImportText.set("Import: " + DateText);
        Settings.getInstance().setLastimportKTW(new Date());
        try {
            Settings.getInstance().SaveSettings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LastImportText.set("Import: " + Settings.getInstance().getLastimportKTW().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SKU.setCellValueFactory(new PropertyValueFactory<DataKTW, Integer>("SKU"));
        Name.setCellValueFactory(new PropertyValueFactory<DataKTW, String>("Name"));
        Gross.setCellValueFactory(new PropertyValueFactory<DataKTW, Double>("Gross"));
        Net.setCellValueFactory(new PropertyValueFactory<DataKTW, Double>("Net"));
        cs.setCellValueFactory(new PropertyValueFactory<DataKTW, Double>("cs"));
        Destination.setCellValueFactory(new PropertyValueFactory<DataKTW, String>("dest"));
        PalletType.setCellValueFactory(new PropertyValueFactory<DataKTW, String>("paltype"));
        QATime.setCellValueFactory(new PropertyValueFactory<DataKTW, Integer>("qatime"));
        Height.setCellValueFactory(new PropertyValueFactory<DataKTW, Double>("height"));

        TableList = DataKTWList.getInstance().getData();
        Table.setItems(TableList);

        ProgresValue = new SimpleDoubleProperty(0.0);
        ProgresBar.progressProperty().bind(ProgresValue);

        LastImport.textProperty().bind(LastImportText);
        Date LastDate = Settings.getInstance().getLastimportKTW();
        String DateText = "";
        if (LastDate != null) DateText = LastDate.toString();
        LastImportText.set("Import: " + DateText);
    }
}
