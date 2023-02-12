package InWork.DataBase.DataStructure;



import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataPPList {

    private static DataPPList Instance;
    public static DataPPList getInstance() {
        if (Instance == null) { Instance = new DataPPList();}
        return Instance;
    }

    private ArrayList<DataPP> dataList;
    private DataPPList()
    {

    }

    public ArrayList<DataPP> getDataList() {
        return dataList;
    }

    public void ImportPP()
    {
        dataList = ExcelAPI.ImportPP(null);
    }
}
