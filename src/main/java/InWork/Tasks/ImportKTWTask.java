package InWork.Tasks;

import InWork.Controllers.DataBaseController;
import InWork.Controllers.ExcelController;
import InWork.DataStructure.DataKTW;
import InWork.Settings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ImportKTWTask extends Task<Boolean>{

    private ObservableList<DataKTW> DataList;
    public ImportKTWTask(ObservableList<DataKTW> DataList) {
        this.DataList = DataList;
    }

    @Override
    protected Boolean call() throws Exception {
        updateProgress(0, 1);
        try {
            DataBaseController DataBase = DataBaseController.getInstance();
            updateMessage("Lading ExcelFile");
            ArrayList<DataKTW> excelData = ExcelController.ImportKTW(null);
            updateProgress(0, 1);
            updateMessage("Comper Imported Data to Data Base");
            if (excelData.size() < 1)
            {
                updateMessage("");
                return false;
            }

            int AllToHandle = DataList.size() + excelData.size();
            int Handle = 0;
            boolean find;
            DataKTW Record;
            for (int i = (DataList.size() - 1); i >= 0; i--) {
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
            updateProgress(0, 1);
            updateMessage("Error");
            throw new RuntimeException(e);
        }
        Settings.getInstance().setLastimportKTW(new Date());
        Settings.getInstance().SaveSettings();
        updateMessage("");
        return true;
    }
}
