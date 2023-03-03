package InWork.Controllers.javaFX;

import InWork.Tasks.ImportKTWTask;
import InWork.DataStructure.Collection.DataKTWList;
import InWork.DataStructure.DataKTW;
import InWork.Settings;
import javafx.beans.property.SimpleBooleanProperty;
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

    private ObservableList<DataKTW> TableList;

    private SimpleBooleanProperty ImportKTWRunning;
    public void ImportKTW(ActionEvent actionEvent) {
        if (!ImportKTWRunning.getValue()) {
            ImportKTWTask task = new ImportKTWTask(TableList);
            ProgresBar.progressProperty().unbind();
            ProgresBar.progressProperty().bind(task.progressProperty());
            ImportKTWRunning.unbind();
            ImportKTWRunning.bind(task.runningProperty());
            Thread BackGroundTask = new Thread(task);
            BackGroundTask.setDaemon(true);
            BackGroundTask.start();
        } else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Import KTW In Progress");
            //alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Import KTW In Progress")));
            alert.showAndWait();
        }
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

        ImportKTWRunning = new SimpleBooleanProperty(false);
    }
}
