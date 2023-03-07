package InWork.GUI.Controllers;

import InWork.GUI.GUIController;
import InWork.Tasks.ImportKTWTask;
import InWork.DataStructure.Collection.DataKTWList;
import InWork.DataStructure.DataKTW;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TableKTWController implements Initializable {

    @FXML
    private TableView Table;
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
    private ProgressBar ProgresBar;
    @FXML
    private Label ProgressText;

    private ObservableList<DataKTW> TableList;

    private SimpleBooleanProperty ImportKTWRunning;
    public void ImportKTW(ActionEvent actionEvent) {
        if (!ImportKTWRunning.getValue()) {
            File ImportedFile = GUIController.ChoseExcelFile("Select Katowice Excel Data", (Stage)ProgresBar.getScene().getWindow());
            ImportKTWTask task = new ImportKTWTask(ImportedFile, TableList);
            ProgresBar.progressProperty().unbind();
            ProgresBar.progressProperty().bind(task.progressProperty());
            ImportKTWRunning.unbind();
            ImportKTWRunning.bind(task.runningProperty());
            ProgressText.textProperty().unbind();
            ProgressText.textProperty().bind(task.messageProperty());
            Thread BackGroundTask = new Thread(task);
            BackGroundTask.setDaemon(true);
            BackGroundTask.start();
        } else GUIController.showMsgWarrning(Alert.AlertType.WARNING, "Import KTW In Progress");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SKU.setCellValueFactory(new PropertyValueFactory<>("SKU"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Gross.setCellValueFactory(new PropertyValueFactory<>("Gross"));
        Net.setCellValueFactory(new PropertyValueFactory<>("Net"));
        cs.setCellValueFactory(new PropertyValueFactory<>("cs"));
        Destination.setCellValueFactory(new PropertyValueFactory<>("dest"));
        PalletType.setCellValueFactory(new PropertyValueFactory<>("paltype"));
        QATime.setCellValueFactory(new PropertyValueFactory<>("qatime"));
        Height.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableList = DataKTWList.getInstance().getData();
        Table.setItems(TableList);

        ImportKTWRunning = new SimpleBooleanProperty(false);


    }
}
