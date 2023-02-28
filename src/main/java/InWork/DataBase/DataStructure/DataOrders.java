package InWork.DataBase.DataStructure;

public class DataOrders {
    int FO;
    int Dest;
    int Carrier;
    String Postal;
    int LDate;
    int LTime;
    String CarrierDesc;

    public int getCarrier() {
        return Carrier;
    }
    public void setCarrier(int carrier) {
        Carrier = carrier;
    }
    public String getPostal() {
        return Postal;
    }
    public void setPostal(String postal) {
        Postal = postal;
    }
    public int getLDate() {
        return LDate;
    }
    public void setLDate(int LDate) {
        this.LDate = LDate;
    }
    public int getLTime() {
        return LTime;
    }
    public void setLTime(int LTime) {
        this.LTime = LTime;
    }
    public String getCarrierDesc() {
        return CarrierDesc;
    }
    public void setCarrierDesc(String carrierDesc) {
        CarrierDesc = carrierDesc;
    }
    public int getFO() {
        return FO;
    }
    public void setFO(int FO) {
        this.FO = FO;
    }
    public int getDest() {
        return Dest;
    }
    public void setDest(int dest) {
        Dest = dest;
    }


}
