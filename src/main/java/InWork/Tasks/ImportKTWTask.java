package InWork.Tasks;

import InWork.Controllers.DataBaseController;
import InWork.Controllers.ExcelController;
import InWork.DataStructure.DataKTW;
import InWork.GUI.PopUpWindow;
import InWork.Settings;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ImportKTWTask extends Task<Boolean>{

    private final ObservableList<DataKTW> DataList;
    private final File SourceFile;
    public ImportKTWTask(File SourceFile, ObservableList<DataKTW> DataList) {
        this.DataList = DataList;
        this.SourceFile = SourceFile;
    }

    private ArrayList<DataKTW> ImportKTW() {
        updateMessage("Import excel data");
        updateProgress(-1,1);
        Workbook WoorkBook = ExcelController.ImportFile(SourceFile);
        if (WoorkBook == null)
        {
            updateProgress(0,1);
            updateMessage("Import data canceled");
            return null;
        }
        ArrayList<DataKTW> ret = new ArrayList<>();

        updateMessage("Analysis of imported excel data");
        Sheet sheet = WoorkBook.getSheetAt(1);
        DataKTW newRecord;
        int source;
        Cell cell;
        double skudouble;

        int RowNum = sheet != null ? sheet.getLastRowNum() : 0;
        int AllToHandle = 2 * (RowNum);
        int Handle = 0;
        updateProgress(Handle,AllToHandle);

        for(source = 1 ; source < (RowNum); ++source) {
            newRecord = new DataKTW();
            cell = sheet.getRow(source).getCell(0);
            skudouble = cell.getNumericCellValue();
            if ((int) skudouble < 1) {
                break;
            }
            newRecord.setSKU((int) skudouble);

            cell = sheet.getRow(source).getCell(1);
            newRecord.setName(cell.getStringCellValue());
            cell = sheet.getRow(source).getCell(52);
            newRecord.setGross(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(57);
            newRecord.setNet(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(56);
            newRecord.setCs(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(61);
            newRecord.setHeight(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(54);
            newRecord.setDest(cell.getStringCellValue());
            cell = sheet.getRow(source).getCell(58);
            String str = cell.getStringCellValue();
            str = str.replaceAll("\\s+","");
            if (str.equals("1200/1000")) {
                newRecord.setPaltype("IND");
            } else {
                newRecord.setPaltype("EUR");
            }
            int temp;
            if (newRecord.getDest().toLowerCase().contains("fresh")) {
                temp = newRecord.getDest().charAt(0);
                if (temp == 50) {
                    newRecord.setQatime(2);
                } else {
                    if (temp == 52) {
                        newRecord.setQatime(4);
                    } else {
                        if (temp == 51) {
                            newRecord.setQatime(3);
                        } else {
                            newRecord.setQatime(0);
                        }
                    }
                }
            } else {
                newRecord.setQatime(0);
            }
            ret.add(newRecord);
            Handle++;
            updateProgress(Handle,AllToHandle);
        }
        return ret;
    }

    @Override
    protected Boolean call() throws Exception {
        updateProgress(0, 1);
        try {
            DataBaseController DataBase = DataBaseController.getInstance();
            ArrayList<DataKTW> excelData = ImportKTW();
            if (excelData == null || excelData.size() < 1)
            {
                updateMessage("Error");
                return false;
            }

            int AllToHandle = DataList.size() + excelData.size();
            int Handle = 0;

            Handle += AllToHandle;
            AllToHandle += AllToHandle;

            updateProgress(Handle, AllToHandle);
            updateMessage("Comper Imported Data to Data Base");
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
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING, "DataBase Error", e.getMessage());
            updateMessage("Error");
            e.printStackTrace();
            return false;
        }
        Settings.getInstance().setLastimportKTW(new Date());
        Settings.getInstance().SaveSettings();
        updateProgress(1,1);
        updateMessage("Import Katowice Data Complet");
        return true;
    }
}
