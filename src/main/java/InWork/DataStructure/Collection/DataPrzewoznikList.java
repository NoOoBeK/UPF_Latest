package InWork.DataStructure.Collection;

import InWork.Controllers.DataBaseController;
import InWork.Controllers.ExcelController;
import InWork.DataStructure.DataPrzewoznik;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataPrzewoznikList {

    static public DataPrzewoznikList Instance;
    private ArrayList<DataPrzewoznik> DataList;
    static public DataPrzewoznikList getInstance()  {
        if (Instance == null) {
            try {
                Instance = new DataPrzewoznikList();
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Błąd Pobrania danych");
                throwables.printStackTrace();
            }
        }
        return Instance;
    }
    private DataPrzewoznikList() throws SQLException {
        Instance = this;
        loadDataDB();
    }
    private void loadDataDB() throws SQLException {
        DataList = DataBaseController.getInstance().getPrzewoznik();
    }
    public ArrayList<DataPrzewoznik> getData()
    {
        return DataList;
    }
    public DataPrzewoznik getPrzewoznik(int carrier){
        for (DataPrzewoznik dane : DataList)
        {
            if (dane.getPrzewoznikID() == carrier) return dane;
        }
        return null;
    }
    public boolean ImpotrPrzewoznik() throws SQLException {
        ArrayList<DataPrzewoznik> excelData = ExcelController.ImportPrzewoznik(null);
        if (excelData.size() < 1) {
            return false;
        }
        DataPrzewoznik checkedPrzewoznik;

        for (DataPrzewoznik record : DataList) {
            checkedPrzewoznik = DataBaseController.getInstance().getPrzewoznik(record.getPrzewoznikID());
            if (checkedPrzewoznik != null) {
                DataBaseController.getInstance().delatePrzewoznik(record);
            }
        }
        for (DataPrzewoznik record : excelData) {
            checkedPrzewoznik = DataBaseController.getInstance().getPrzewoznik(record.getPrzewoznikID());
            if (checkedPrzewoznik != null) {
                if (!record.compare(checkedPrzewoznik)) {
                    DataBaseController.getInstance().updatePrzewoznik(record);
                }
            } else {
                DataBaseController.getInstance().addPrzewoznik(record);
            }
        }
        loadDataDB();
        return true;
    }
    public void cleanList()
    {
        Instance = null;
    }

    public void add(DataPrzewoznik data)
    {
        DataList.add(data);
    }
    public void remove (int index)
    {
        DataList.remove(index);
    }
    public void remove (DataPrzewoznik data)
    {
        DataList.remove(data);
    }
    public void replace (DataPrzewoznik data, int index)
    {
        DataList.get(index).setAll(data);
    }
}
