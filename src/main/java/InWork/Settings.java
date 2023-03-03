package InWork;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.poi.sl.usermodel.ObjectMetaData;

import java.io.*;
import java.time.LocalDate;
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
        LastimportKTW = null;
        FileChoserPath = "user.dir";
        Style = "Normal";
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

    public Date getLastimportKTW() {
        return LastimportKTW;
    }
    public void setLastimportKTW(Date lastimportKTW) {
        this.LastimportKTW = lastimportKTW;
    }

    public String getFileChoserPath() {
        return FileChoserPath;
    }

    public void setFileChoserPath(String fileChoserPathProper) {
        this.FileChoserPath =fileChoserPathProper;
    }
    public String getStyle() {
        return Style;
    }
    public void setStyle(String style) {
        this.Style = style;
    }
}
