package InWork.DataStructure;

public class DataKTW {

    private int SKU;
    private String Name;
    private double Gross;
    private double Net;
    private double cs;
    private String dest;
    private String paltype;
    private int qatime;
    private double height;

    public DataKTW() {

    }
    public int getSKU() {
        return SKU;
    }
    public void setSKU(int SKU) {
        this.SKU = SKU;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public double getGross() {
        return Gross;
    }
    public void setGross(double gross) {
        Gross = gross;
    }
    public double getNet() {
        return Net;
    }
    public void setNet(double net) {
        Net = net;
    }
    public double getCs() {
        return cs;
    }
    public void setCs(double cs) {
        this.cs = cs;
    }
    public String getDest() {
        return dest;
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
    public String getPaltype() {
        return paltype;
    }
    public void setPaltype(String paltype) {
        this.paltype = paltype;
    }
    public int getQatime() {
        return qatime;
    }
    public void setQatime(int qatime) {
        this.qatime = qatime;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void clone(DataKTW source)
    {
        setSKU(source.getSKU());
        setQatime(source.getQatime());
        setPaltype(source.getPaltype());
        setCs(source.getCs());
        setDest(source.getDest());
        setHeight(source.getHeight());
        setName(source.getName());
        setGross(source.getGross());
        setNet(source.getNet());
    }
    public boolean compare(DataKTW comparData)
    {
        return getSKU() == comparData.getSKU() && getName().equals(comparData.getName()) && getGross() == comparData.getGross()
                && getNet() == comparData.getNet() && getCs() == comparData.getCs() && getDest().equals(comparData.getDest())
                && getPaltype().equals(comparData.getPaltype()) && getQatime() == comparData.getQatime() && getHeight() == comparData.getHeight();
    }
}
