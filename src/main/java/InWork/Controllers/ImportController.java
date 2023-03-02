package InWork.Controllers;

import InWork.DataStructure.DataKTW;
import InWork.DataStructure.Collection.DataKTWList;

import java.sql.SQLException;
import java.util.ArrayList;

public class ImportController {

    static public void ImpotrKTW(){
        ArrayList<DataKTW> excelData = ExcelController.ImportKTW(null);

        DataKTWList KWList = DataKTWList.getInstance();
        DataBaseController DataBase = DataBaseController.getInstance();

        DataKTW checkedSKU;
        ArrayList<DataKTW> DataList;
        try {
            DataList = DataBase.getKTW();
            KWList.setData(DataList);
            for (DataKTW record : DataList) {
                checkedSKU = KWList.getKTW(record.getSKU());
                if (checkedSKU != null) {
                    DataBase.delateKTW(record);
                }
            }
            for (DataKTW record : excelData) {
                checkedSKU = DataBase.getKTW(record.getSKU());
                if (checkedSKU != null) {
                    if (!record.compare(checkedSKU)) {
                        DataBase.updateKTW(record);
                    }
                } else {
                    DataBase.addKTW(record);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
