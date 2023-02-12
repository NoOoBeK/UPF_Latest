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

    static public DataBaseAPI getInstance() {
        if (Instance == null)
        {
            try {
                Instance = new DataBaseAPI();
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Błąd połączenia z bazą danych");
                throwables.printStackTrace();
            }
        }
        return Instance;
    }

    private DataBaseAPI() throws SQLException {
        Instance = this;
        this.conn = null;
        Connect();

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
    }

    private void Connect() throws SQLException {
        String url = "jdbc:sqlite:" + DatabasePath + "\\UPF_DB.db";
        System.out.println(url);
        conn = DriverManager.getConnection(url);
        System.out.println("Connection to SQLite has been established.");
    }

    private void CreatTable() throws SQLException {

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
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = conn.createStatement();stmt.execute(TableKTW);
    }

    private void insertKTW(DataKTW data) throws SQLException {

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
    }

    public void cleanDBKTW() throws SQLException {
        String sql1 = "DELETE FROM KTW;";
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = this.conn.createStatement();
        stmt.execute(sql1);
    }

    public void updateKTW(DataKTW data) throws SQLException {
        if (!conn.isValid(5)) {
            Connect();
        }
        PreparedStatement pst;
        pst = conn.prepareStatement("UPDATE KTW SET name = ?, gross = ?, net = ?, cs = ?, dest = ?, paltype = ?, qatime = ?, height = ?, InsertTime = ?) WHERE sku == ?;");
        pst.setString(1, data.getName());
        pst.setDouble(2, data.getGross());
        pst.setDouble(3, data.getNet());
        pst.setDouble(4, data.getCs());
        pst.setString(5, data.getDest());
        pst.setString(6, data.getPaltype());
        pst.setInt   (7, data.getQatime());
        pst.setDouble(8, data.getHeight());
        pst.setString(9,LocalDate.now().toString());

        pst.setInt   (10, data.getSKU());
        pst.executeUpdate();
    }

    public int getKtwCount() throws SQLException {
        String querry = "SELECT COUNT(*) FROM KTW;";
        int count = -1;
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(querry);
        while (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public void addKTW(DataKTW newRecord) throws SQLException {
        insertKTW(newRecord);
    }

    public DataKTW getKTW(int sku) throws SQLException {
        DataKTW ret = null;
        String querry = "SELECT * FROM KTW WHERE sku = ?;";
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setInt(1, sku);
        ResultSet rs = Pstmt.executeQuery();
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
            ret = newRecord;
        }
        return ret;
    }

    public ArrayList<DataKTW> getKTW() throws SQLException {
        String querry = "SELECT * FROM KTW;";
        ArrayList<DataKTW> ret = new ArrayList<>();
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
        return ret;
    }

    public LocalDate getKTWLastInsert() throws SQLException {
        String querry = "SELECT InsertTime FROM KTW;";
        LocalDate newDate = LocalDate.now();
        LocalDate latestDate = LocalDate.MIN;
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(querry);
        while (rs.next()) {
            newDate = LocalDate.parse(rs.getString(1));
            if (newDate.isAfter(latestDate)) {latestDate = LocalDate.from(newDate);}
        }
        return latestDate;
    }

    public boolean delateKTW(DataKTW record) throws SQLException {
        String querry = "DELETE FROM KTW WHERE sku == ?;";
        LocalDate newDate = LocalDate.now();
        LocalDate latestDate = LocalDate.MIN;
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setInt(1,record.getSKU());
        return Pstmt.execute();
    }
    public boolean delateKTW(int sku) throws SQLException {
        String querry = "DELETE FROM KTW WHERE sku == ?;";
        LocalDate newDate = LocalDate.now();
        LocalDate latestDate = LocalDate.MIN;
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setInt(1,sku);
        return Pstmt.execute();
    }
}
