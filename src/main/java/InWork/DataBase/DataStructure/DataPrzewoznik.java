package InWork.DataBase.DataStructure;

public class DataPrzewoznik {

    String Przewoznik;
    String Mail;
    int PrzewoznikID;

    public int getPrzewoznikID() {
        return PrzewoznikID;
    }
    public void setPrzewoznikID(int przewoznikID) {
        PrzewoznikID = przewoznikID;
    }
    public String getPrzewoznik() {
        return Przewoznik;
    }
    public void setPrzewoznik(String przewoznik) {
        Przewoznik = przewoznik;
    }
    public String getMail() {
        return Mail;
    }
    public void setMail(String mail) {
        Mail = mail;
    }
    public void setAll (DataPrzewoznik data) {
        setPrzewoznikID(data.getPrzewoznikID());
        setPrzewoznik(data.getPrzewoznik());
        setMail(data.getMail());
    }
    public void setAll (int przewoznikID,String przewoznik, String mail) {
        setPrzewoznikID(przewoznikID);
        setPrzewoznik(przewoznik);
        setMail(mail);

    }
    public boolean compare(DataPrzewoznik comparData) {
        return getPrzewoznikID() == comparData.getPrzewoznikID() && getMail() == comparData.getMail();
    }

}
