package InWork.DataBase;

import InWork.DataBase.DataStructure.DataKTW;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class DataBaseAPI {

    static private DataBaseAPI Instance = null;
    private Connection conn;
    private final String DatabasePath = "C:\\demo";

    static public DataBaseAPI getInstance()
    {
        if (Instance == null)
        {
            Instance = new DataBaseAPI();
        }
        return Instance;
    }

    private DataBaseAPI()
    {
        Instance = this;
        this.conn = null;
        Connect();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet Result = metaData.getTables(null, null, null, new String[] {"TABLE"});
            boolean find = false;
            String TableName = "KTW";
            while (Result.next()) {
                if (TableName.equals(Result.getString("TABLE_NAME")))
                {
                    find = true;
                    break;
                }
            }
            if (!find)
            {
                CreatTable();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd Sprawdzania Istniejących Tabel w Bazie Danych");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void Connect()
    {
        try {
            String url = "jdbc:sqlite:" + DatabasePath + "\\UPF_DB.db";
            System.out.println(url);
            //Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd połączenia z Bazą Danych");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void CreatTable() {

        String TableKTW  = "CREATE TABLE IF NOT EXISTS KTW(\n"
                + "sku INTEGER NOT NULL,\n"
                + "name TEXT NOT NULL,\n"
                + "gross REAL,\n"
                + "net REAL,\n"
                + "cs REAL,\n"
                + "dest TEXT,\n"
                + "paltype TEXT,\n"
                + "qatime INTEGER,\n"
                + "height REAL,\n"
                + "InsertTime TEXT NOT NULL\n"
                + ");";
        try {
            if (!conn.isValid(5)) {Connect();}
            Statement stmt = conn.createStatement();
            stmt.execute(TableKTW);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd Tworzenia Tablicy");
            System.out.println(e.getMessage());
        }
    }

    private void insertKTW(DataKTW data) {

        try {
            if (!conn.isValid(5)) {
                Connect();
            }
            PreparedStatement pst;
            pst = conn.prepareStatement("INSERT INTO KTW(sku,name,gross,net,cs,dest,paltype,qatime,height,InsertTime)VALUES(?,?,?,?,?,?,?,?,?,?)");
            pst.setInt   (1, data.getSKU());
            pst.setString(2, data.getName());
            pst.setDouble(3, data.getGross());
            pst.setDouble(4, data.getNet());
            pst.setDouble(5, data.getCs());
            pst.setString(6, data.getDest());
            pst.setString(7, data.getPaltype());
            pst.setInt   (8, data.getQatime());
            pst.setDouble(9, data.getHeight());
            pst.setString(10,LocalDate.now().toString());
            pst.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void cleanDB()
    {
        String sql1 = "DELETE FROM KTW;";
        try{
            if (!conn.isValid(5)) {Connect();}
            Statement stmt = this.conn.createStatement();
            stmt.execute(sql1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd czyszczenia Bazy Danych");
            System.out.println(e.getMessage());
        }
    }

    public int getKtwCount()
    {
        String querry = "SELECT InsertTime FROM KTW;";
        int count = -1;
        try {
            if (!conn.isValid(5)) {Connect();}
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querry);
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    public void addKTW(DataKTW newRecord) {
        insertKTW(newRecord);
    }

    public ArrayList<DataKTW> getKTW()
    {
        String querry = "SELECT * FROM KTW;";
        ArrayList<DataKTW> ret = new ArrayList<>();
        try {
            if (!conn.isValid(5)) {Connect();}
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querry);
            while (rs.next()) {
                DataKTW newRecord = new DataKTW();
                newRecord.setSKU(rs.getInt(1));
                newRecord.setName(rs.getString(2));
                newRecord.setGross(rs.getDouble(3));
                newRecord.setNet(rs.getDouble(4));
                newRecord.setCs(rs.getDouble(5));
                newRecord.setDest(rs.getString(6));
                newRecord.setPaltype(rs.getString(7));
                newRecord.setQatime(rs.getInt(8));
                newRecord.setHeight(rs.getDouble(9));
                ret.add(newRecord);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd zapytania");
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public LocalDate getKTWLastInsert() {
        String querry = "SELECT InsertTime FROM KTW;";
        LocalDate newDate = LocalDate.now();
        LocalDate latestDate = LocalDate.MIN;
        try {
            if (!conn.isValid(5)) {Connect();}
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querry);
            while (rs.next()) {
                newDate = LocalDate.parse(rs.getString(1));
                if (newDate.isAfter(latestDate)) {latestDate = LocalDate.from(newDate);}
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return latestDate;
    }
}
