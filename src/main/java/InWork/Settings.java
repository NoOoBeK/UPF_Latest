package InWork;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.*;
import java.time.LocalDate;
import java.util.Date;

public class Settings implements java.io.Serializable {
    private static Settings Instance = null;
    private StringProperty LastimportKTW;
    private StringProperty FileChoserPath;
    private StringProperty Style;

    public static Settings getInstance() {
        if (Instance == null)
        {
            //LoadSettings();
            if (Instance == null) Instance = new Settings();
        }
        return Instance;
    }
    private Settings() {
        LastimportKTW = new SimpleStringProperty("");
        FileChoserPath = new SimpleStringProperty("user.dir");
        Style = new SimpleStringProperty("");
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

    public String getLastimportKTW() {
        return LastimportKTW.get();
    }

    public StringProperty lastimportKTWProperty() {
        return LastimportKTW;
    }

    public void setLastimportKTW(Date lastimportKTW) {
        this.LastimportKTW.set(lastimportKTW.toString());
    }
    public void setLastimportKTW(String lastimportKTW) {
        this.LastimportKTW.set(lastimportKTW);
    }

    public String getFileChoserPath() {
        return FileChoserPath.get();
    }
    public StringProperty fileChoserPathProperty() {
        return FileChoserPath;
    }
    public void setFileChoserPath(String fileChoserPathProper) {
        this.FileChoserPath.set(fileChoserPathProper);
    }
    public String getStyle() {
        return Style.get();
    }
    public StringProperty styleProperty() {
        return Style;
    }
    public void setStyle(String style) {
        this.Style.set(style);
    }
}
