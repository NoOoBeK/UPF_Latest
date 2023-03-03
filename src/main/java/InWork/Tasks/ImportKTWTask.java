package InWork.Tasks;

import InWork.Controllers.DataBaseController;
import InWork.Controllers.ExcelController;
import InWork.DataStructure.DataKTW;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;

public class ImportKTWTask extends Task<Boolean>{

    //ImpotrKTW(ObservableList<DataKTW> DataList, SimpleDoubleProperty ProgresValue){
    static private boolean runImportKTW;
    ObservableList<DataKTW> DataList;
    public ImportKTWTask(ObservableList<DataKTW> DataList) {
        this.DataList = DataList;
    }
    @Override
    protected Boolean call() throws Exception {
        if (!runImportKTW) {
            runImportKTW = true;
            updateProgress(0, 1);
            try {
                DataBaseController DataBase = DataBaseController.getInstance();
                ArrayList<DataKTW> excelData = ExcelController.ImportKTW(null);
                if (excelData.size() < 1) return false;

                int AllToHandle = DataList.size() + excelData.size();
                int Handle = 0;
                boolean find;
                DataKTW Record;
                for (int i = (DataList.size() - 1); i >= 0; i++) {
                    Record = DataList.get(i);
                    find = false;
                    for (DataKTW checkedRecord : excelData) {
                        if (Record.getSKU() == checkedRecord.getSKU()) {
                            find = true;
                            if (!Record.compare(checkedRecord)) {
                                Record.clone(checkedRecord);
                                DataBase.updateKTW(checkedRecord);
                            }
                            break;
                        }
                    }
                    if (!find) {
                        DataBase.delateKTW(Record);
                        DataList.remove(i);
                    }
                    Handle++;
                    updateProgress(Handle, AllToHandle);
                }

                for (DataKTW record : excelData) {
                    find = false;
                    for (DataKTW checkedRecord : DataList) {
                        if (record.getSKU() == checkedRecord.getSKU()) {
                            find = true;
                            break;
                        }
                    }
                    if (!find) DataBase.addKTW(record);
                    DataList.add(record);
                    Handle++;
                    updateProgress(Handle, AllToHandle);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                runImportKTW = false;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An exception occurred!");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Import KTW In Progress")));
            alert.showAndWait();
        }
        return true;
    }
}
