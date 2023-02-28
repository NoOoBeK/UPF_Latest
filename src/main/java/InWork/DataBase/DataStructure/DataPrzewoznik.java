package InWork.DataBase.DataStructure;

public class DataPrzewoznik {

    String Przewoznik;
    String Mail;

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
        setPrzewoznik(data.getPrzewoznik());
        setMail(data.Mail);
    }

    public void setAll (String przewoznik, String mail) {
        setPrzewoznik(przewoznik);
        setMail(mail);

    }
    public boolean compare(DataPrzewoznik comparData) {
        return getPrzewoznik() == comparData.getPrzewoznik() && getMail() == comparData.getMail();
    }

}
