package InWork.Controllers;

import InWork.DataStructure.DataKTW;
import InWork.DataStructure.DataPrzewoznik;
import InWork.Settings;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class DataBaseController {

    static private DataBaseController Instance = null;
    private Connection conn;

    static public DataBaseController getInstance() {
        if (Instance == null)
        {
            Instance = new DataBaseController();
        }
        return Instance;
    }
    private void Connect() {
        String databasePath = "C:\\demo";
        String url = "jdbc:sqlite:" + databasePath + "\\UPF_DB.db";
        //String url = DatabasePath + "\\UPF_DB.db";
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection to SQLite has been established.");
    }

    private DataBaseController() {
        Instance = this;
        this.conn = null;
        Connect();
        try {
            CreatTable();
            CreatTable2();
            CreatTable3();
            CreatTable4();
            CreatTable5();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            + "height REAL\n"
            + ");";
        if (!conn.isValid(5)) {
            Connect();
        }
        Statement stmt = conn.createStatement();
        stmt.execute(TableKTW);
    }
    private void insertKTW(DataKTW data) throws SQLException {

        if (!conn.isValid(5)) {
            Connect();
        }
        PreparedStatement pst;
        pst = conn.prepareStatement("INSERT INTO KTW(sku,name,gross,net,cs,dest,paltype,qatime,height)VALUES(?,?,?,?,?,?,?,?,?)");
        pst.setInt   (1, data.getSKU());
        pst.setString(2, data.getName());
        pst.setDouble(3, data.getGross());
        pst.setDouble(4, data.getNet());
        pst.setDouble(5, data.getCs());
        pst.setString(6, data.getDest());
        pst.setString(7, data.getPaltype());
        pst.setInt   (8, data.getQatime());
        pst.setDouble(9, data.getHeight());
        pst.executeUpdate();
    }
    public void cleanDBKTW() throws SQLException {
        String sql1 = "DROP TABLE KTW;";
        conn.close();
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = this.conn.createStatement();
        stmt.execute(sql1);
        CreatTable();
    }
    public void updateKTW(DataKTW data) throws SQLException {
        if (!conn.isValid(5)) {
            Connect();
        }
        PreparedStatement pst;
        pst = conn.prepareStatement("UPDATE KTW SET name = ?, gross = ?, net = ?, cs = ?, dest = ?, paltype = ?, qatime = ?, height = ?) WHERE sku == ?;");
        pst.setString(1, data.getName());
        pst.setDouble(2, data.getGross());
        pst.setDouble(3, data.getNet());
        pst.setDouble(4, data.getCs());
        pst.setString(5, data.getDest());
        pst.setString(6, data.getPaltype());
        pst.setInt   (7, data.getQatime());
        pst.setDouble(8, data.getHeight());

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
    public void getKTW(ObservableList<DataKTW> List) throws SQLException {
        String querry = "SELECT * FROM KTW;";
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(querry);
        List.clear();
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
            List.add(newRecord);
        }
    }
    public boolean delateKTW(DataKTW record) throws SQLException {
        String querry = "DELETE FROM KTW WHERE sku == ?;";
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setInt(1,record.getSKU());
        return Pstmt.execute();
    }
    public boolean delateKTW(int sku) throws SQLException {
        String querry = "DELETE FROM KTW WHERE sku == ?;";
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setInt(1,sku);
        return Pstmt.execute();
    }


    private void CreatTable2() throws SQLException {

               String TablePrzewzonik  = "CREATE TABLE IF NOT EXISTS PRZEWOZNIK(\n"
                       + "przewoznikid INTEGER NOT NULL,\n"
                       + "przewoznik TEXT NOT NULL,\n"
                       + "mail TEXT\n"
                       + ");";
        if (!conn.isValid(5)) {
            Connect();
        }
        Statement stmt = conn.createStatement();
        stmt.execute(TablePrzewzonik);
    }
    public DataPrzewoznik getPrzewoznik(Long carrier) throws SQLException {
        DataPrzewoznik ret = null;
        String querry = "SELECT * FROM PRZEWOZNIK WHERE przewoznikid = ?;";
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setLong(1, carrier);
        ResultSet rs = Pstmt.executeQuery();
        while (rs.next()) {
            DataPrzewoznik newRecord = new DataPrzewoznik();
            newRecord.setPrzewoznikID(rs.getInt(1));
            newRecord.setPrzewoznik(rs.getString(2));
            newRecord.setMail(rs.getString(3));
            ret = newRecord;
        }
        return ret;
    }
    public ArrayList<DataPrzewoznik> getPrzewoznik() throws SQLException {
        String querry = "SELECT * FROM PRZEWOZNIK;";
        ArrayList<DataPrzewoznik> ret = new ArrayList<>();
        if (!conn.isValid(5)) {Connect();}
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(querry);
        while (rs.next()) {
            DataPrzewoznik newRecord = new DataPrzewoznik();
            newRecord.setPrzewoznikID(rs.getInt(1));
            newRecord.setPrzewoznik(rs.getString(2));
            newRecord.setMail(rs.getString(3));
            ret.add(newRecord);
        }
        return ret;
    }
    public boolean delatePrzewoznik(DataPrzewoznik record) throws SQLException {
        String querry = "DELETE FROM PRZEWOZNIK WHERE przewoznikid == ?;";
        if (!conn.isValid(5)) {Connect();}
        PreparedStatement Pstmt = conn.prepareStatement(querry);
        Pstmt.setLong(1,record.getPrzewoznikID());
        return Pstmt.execute();
    }
    public void updatePrzewoznik(DataPrzewoznik data) throws SQLException {
        if (!conn.isValid(5)) {
            Connect();
        }
        PreparedStatement pst;
        pst = conn.prepareStatement("UPDATE PRZEWOZNIK SET mail = ?, przewoznik = ?) WHERE przewoznikid == ?;");
        pst.setString(1, data.getMail());
        pst.setString   (2, data.getPrzewoznik());
        pst.setLong(3,data.getPrzewoznikID());
        pst.executeUpdate();
    }
    public void addPrzewoznik(DataPrzewoznik newRecord) throws SQLException {
        insertPrzewoznik(newRecord);
    }
    private void insertPrzewoznik(DataPrzewoznik data) throws SQLException {

        if (!conn.isValid(5)) {
            Connect();
        }
        PreparedStatement pst;
        pst = conn.prepareStatement("INSERT INTO PRZEWOZNIK(przewoznikid,przewoznik,mail)VALUES(?,?,?)");
        pst.setLong(1,data.getPrzewoznikID());
        pst.setString   (2, data.getPrzewoznik());
        pst.setString(3, data.getMail());
        pst.executeUpdate();
    }



    private void CreatTable3() throws SQLException {

        String tableFO  = "CREATE TABLE IF NOT EXISTS FODATA(\n"
                + "fo INTEGER NOT NULL,\n"
                + "przewoznikid INTEGER NOT NULL,\n"
                + "destid INTEGER NOT NULL\n"
                + ");";
        if (!conn.isValid(5)) {
            Connect();
        }
        Statement stmt = conn.createStatement();
        stmt.execute(tableFO);
    }


    private void CreatTable4() throws SQLException {

        String tableUPD  = "CREATE TABLE IF NOT EXISTS FOUPDATE(\n"
                + "fo INTEGER NOT NULL,\n"
                + "date REAL NOT NULL,\n"
                + "upd REAL NOT NULL,\n"
                + "time REAL NOT NULL\n"
                + ");";
        if (!conn.isValid(5)) {
            Connect();
        }
        Statement stmt = conn.createStatement();
        stmt.execute(tableUPD);
    }


    private void CreatTable5() throws SQLException {

        String TableDEST  = "CREATE TABLE IF NOT EXISTS DEST(\n"
                + "destid INTEGER NOT NULL,\n"
                + "dest TEXT NOT NULL\n"
                + ");";
        if (!conn.isValid(5)) {
            Connect();
        }
        Statement stmt = conn.createStatement();
        stmt.execute(TableDEST);
    }
}
