package InWork;

import java.io.*;
import java.util.Date;

public class Settings implements java.io.Serializable {
    private static Settings Instance = null;
    private Date LastimportKTW;
    private String FileChoserPath;
    private String Style;

    public static Settings getInstance() {
        if (Instance == null)
        {
            //LoadSettings();
            if (Instance == null) Instance = new Settings();
        }
        return Instance;
    }
    private Settings() {
        setLastimportKTW(null);
        setFileChoserPath(System.getProperty("user.dir"));
        setStyle("");
    }
    private static void LoadSettings () {
        try {
            FileInputStream fis = new FileInputStream("Settings.ini");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Instance = (Settings) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SaveSettings () throws IOException {
        FileOutputStream fos
                = new FileOutputStream("Settings.ini");
        ObjectOutputStream oos
                = new ObjectOutputStream(fos);
        oos.writeObject(Instance);
    }

    public String getStyle() {
        return Style;
    }
    public void setStyle(String style) {
        Style = style;
    }
    public String getFileChoserPath() {
        return FileChoserPath;
    }
    public void setFileChoserPath(String fileChoserPath) {
        FileChoserPath = fileChoserPath;
    }
    public Date getLastimportKTW() {
        return LastimportKTW;
    }
    public void setLastimportKTW(Date lastimportKTW) {
        LastimportKTW = lastimportKTW;
    }

}
