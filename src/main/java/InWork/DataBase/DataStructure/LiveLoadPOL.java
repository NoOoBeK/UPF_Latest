package InWork.DataBase.DataStructure;

public class LiveLoadPOL {

    private double date;
    private double Time;
    private int PaletCoun;

    public LiveLoadPOL()
    {

    }
    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public double getTime() {
        return Time;
    }

    public void setTime(double time) {
        Time = time;
    }

    public int getPaletCoun() {
        return PaletCoun;
    }

    public void setPaletCoun(int paletCoun) {
        PaletCoun = paletCoun;
    }

    public int getNeededTruck() {
        return NeededTruck;
    }

    public void setNeededTruck(int neededTruck) {
        NeededTruck = neededTruck;
    }

    private int NeededTruck;
}
