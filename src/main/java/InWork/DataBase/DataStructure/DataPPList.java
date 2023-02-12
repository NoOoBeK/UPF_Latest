package InWork.DataBase.DataStructure;

import InWork.DataBase.DataBaseAPI;

import java.util.ArrayList;

public class DataPPList {

    static public DataPPList Instance;

    static public DataPPList getInstance(){
        if (Instance==null){Instance=new DataPPList();}
        return Instance;
    }

    private ArrayList<DataPP> Datalist;

    private DataPPList()
    {
        Instance = this;
        Datalist = new ArrayList<>();
    }
    public void add(DataPP data)
    {
        DataList.add(data);
    }

    public void remove (int index)
    {
        DataList.remove(index);
    }

    public void remove (DataPP data)
    {
        DataList.remove(data);
    }

    public void replace (DataPP data, int index)
    {
        DataList.get(index).setAll(data);
    }

    public ArrayList<DataPP> getData()
    {
        return DataList;
    }
}
