package InWork.DataBase.DataStructure;

public class LiveLoadKTW {

    private int Sku;
    private String name;
    private int PalletCount;
    private int MaxPallet;
    private double ProductionTime;
    private double SDate;
    private double STime;

    public double getEDate() {
        return EDate;
    }

    public void setEDate(double EDate) {
        this.EDate = EDate;
    }

    public double getETime() {
        return ETime;
    }

    public void setETime(double ETime) {
        this.ETime = ETime;
    }

    private double EDate;
    private double ETime;
    private String Dest;
    private String Line;

    public LiveLoadKTW()
    {

    }
    public LiveLoadKTW(LiveLoadKTW source)
    {
        setSku(source.getSku());
        setName(source.getName());
        setPalletCount(source.getPalletCount());
        setMaxPallet(source.getMaxPallet());
        setSDate(source.getSDate());
        setSTime(source.STime);
        setDest(source.getDest());
        setLine(source.getLine());
    }

    public String getDest() {
        return Dest;
    }
    public void setDest(String dest) {
        Dest = dest;
    }

    public int getSku() {
        return Sku;
    }
    public void setSku(int sku) {
        Sku = sku;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPalletCount() {
        return PalletCount;
    }
    public void setPalletCount(int palletCount) {
        PalletCount = palletCount;
    }

    public int getMaxPallet() {
        return MaxPallet;
    }
    public void setMaxPallet(int maxPallet) {
        MaxPallet = maxPallet;
    }

    public double getProductionTime() {
        return (((getEDate() + getETime()) - (getSDate() + getSTime())) / getPalletCount());
    }

    public double getSDate() {
        return SDate;
    }
    public void setSDate(double SDate) {
        this.SDate = SDate;
    }

    public double getSTime() {
        return STime;
    }
    public void setSTime(double STime) {
        this.STime = STime;
    }

    public String getLine() {
        return Line;
    }
    public void setLine(String line) {
        Line = line;
    }

    @Override
    public String toString()
    {
        return  getDest();
    }
}
