package InWork.DataBase.DataStructure;

import InWork.DataBase.DataBaseAPI;
import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import java.io.Console;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataKTWList {

    static public DataKTWList Instance;

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

    private ArrayList<DataKTW> DataList;

    private DataKTWList() throws SQLException {
        Instance = this;
        DataList = DataBaseAPI.getInstance().getKTW();
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

    public boolean ImpotrKTW() throws SQLException {
        ArrayList<DataKTW> excelData = ExcelAPI.ImportKTW(null);
        if (excelData.size() < 1) {return false;}
        DataKTW checkedSKU;

        for (DataKTW record : DataList)
        {
            checkedSKU = DataBaseAPI.getInstance().getKTW(record.getSKU());
            if (checkedSKU != null)
            {
                DataBaseAPI.getInstance().delateKTW(record);
            }
        }
        for(DataKTW record : excelData)
        {
            checkedSKU = DataBaseAPI.getInstance().getKTW(record.getSKU());
            if (checkedSKU != null)
            {
                if (!record.compare(checkedSKU))
                {
                    DataBaseAPI.getInstance().updateKTW(record);
                }
            } else {
                DataBaseAPI.getInstance().addKTW(record);
            }
        }

        return true;
    }
}
