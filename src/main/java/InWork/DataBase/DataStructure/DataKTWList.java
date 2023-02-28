package InWork.DataBase.DataStructure;

import InWork.DataBase.DataBaseAPI;
import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataKTWList {

    static public DataKTWList Instance;
    private ArrayList<DataKTW> DataList;

    static public DataKTWList getInstance()  {
        if (Instance == null) {
            try {
                Instance = new DataKTWList();
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Błąd Pobrania danych");
                throwables.printStackTrace();
            }
        }
        return Instance;
    }
    private DataKTWList() throws SQLException {
        Instance = this;
        loadDataDB();
    }
    public void add(DataKTW data)
    {
        DataList.add(data);
    }
    public void remove (int index)
    {
        DataList.remove(index);
    }
    public void remove (DataKTW data)
    {
        DataList.remove(data);
    }
    public void replace (DataKTW data, int index)
    {
        DataList.get(index).setAll(data);
    }
    public ArrayList<DataKTW> getData()
    {
        return DataList;
    }
    public DataKTW getKTW(int sku){
        for (DataKTW dane : DataList)
        {
            if (dane.getSKU() == sku) return dane;
        }
        return null;
    }
    public boolean ImpotrKTW() throws SQLException {
        ArrayList<DataKTW> excelData = ExcelAPI.ImportKTW(null);
        if (excelData.size() < 1) {
            return false;
        }
        DataKTW checkedSKU;

        for (DataKTW record : DataList) {
            checkedSKU = DataBaseAPI.getInstance().getKTW(record.getSKU());
            if (checkedSKU != null) {
                DataBaseAPI.getInstance().delateKTW(record);
            }
        }
        for (DataKTW record : excelData) {
            checkedSKU = DataBaseAPI.getInstance().getKTW(record.getSKU());
            if (checkedSKU != null) {
                if (!record.compare(checkedSKU)) {
                    DataBaseAPI.getInstance().updateKTW(record);
                }
            } else {
                DataBaseAPI.getInstance().addKTW(record);
            }
        }
        loadDataDB();
        return true;
    }
    public void cleanList()
    {
        Instance = null;
    }
    private void loadDataDB() throws SQLException {
        DataList = DataBaseAPI.getInstance().getKTW();
    }
}
