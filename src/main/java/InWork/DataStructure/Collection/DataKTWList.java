package InWork.DataStructure.Collection;

import InWork.Controllers.DataBaseController;
import InWork.DataStructure.DataKTW;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class DataKTWList {

    static private DataKTWList Instance;
    private final ObservableList<DataKTW> DataList;

    static public DataKTWList getInstance()  {
        if (Instance == null) {
            Instance = new DataKTWList();
        }
        return Instance;
    }
    private DataKTWList() {
        Instance = this;
        DataList = FXCollections.observableArrayList();
        try {
            DataBaseController.getInstance().getKTW(DataList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<DataKTW>  getData()
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
}
