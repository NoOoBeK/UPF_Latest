package InWork.DataBase.DataStructure;

import InWork.DataBase.DataBaseAPI;

import java.util.ArrayList;

public class DataKTWList {

    static public DataKTWList Instance;

    static public DataKTWList getInstance()
    {
        if (Instance == null) {Instance = new DataKTWList();}
        return Instance;
    }

    private ArrayList<DataKTW> DataList;

    private DataKTWList()
    {
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
}
