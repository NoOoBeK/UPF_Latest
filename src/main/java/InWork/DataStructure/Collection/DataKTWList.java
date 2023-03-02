package InWork.DataStructure.Collection;

import InWork.DataStructure.DataKTW;

import java.util.ArrayList;

public class DataKTWList {

    static public DataKTWList Instance;
    private ArrayList<DataKTW> DataList;

    static public DataKTWList getInstance()  {
        if (Instance == null) {
            Instance = new DataKTWList();
        }
        return Instance;
    }
    private DataKTWList() {
        Instance = this;
    }
    public ArrayList<DataKTW> getData()
    {
        return DataList;
    }
    public void setData(ArrayList<DataKTW> data)
    {
        DataList = data;
    }
    public DataKTW getKTW(int sku){
        for (DataKTW dane : DataList)
        {
            if (dane.getSKU() == sku) return dane;
        }
        return null;
    }
    public void cleanList()
    {
        Instance = null;
    }
}
