package InWork.DataStructure;

public class DataPrzewoznik {

    String Przewoznik;
    String Mail;
    Long PrzewoznikID;

    public long getPrzewoznikID() {
        return PrzewoznikID;
    }
    public void setPrzewoznikID(long przewoznikID) {
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
    public void setAll (long przewoznikID,String przewoznik, String mail) {
        setPrzewoznikID(przewoznikID);
        setPrzewoznik(przewoznik);
        setMail(mail);

    }
    public boolean compare(DataPrzewoznik comparData) {
        return getPrzewoznikID() == comparData.getPrzewoznikID() && getMail() == comparData.getMail();
    }

}
