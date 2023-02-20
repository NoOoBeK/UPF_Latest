package InWork;

import java.io.*;
import java.util.Date;

public class Settings implements java.io.Serializable {
    private static Settings Instance = null;
    private Date LastimportKTW;
    private String FileChoserPath;

    private static void LoadSettings () throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Settings.ini");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Instance = (Settings) ois.readObject();
    }
    public static Settings getInstance() {
        if (Instance == null)
        {
            try {
                LoadSettings();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                if (Instance == null) Instance = new Settings();
                return Instance;
            }
        }
        return Instance;
    }
    private Settings()
    {
        setLastimportKTW(null);
        setFileChoserPath(System.getProperty("user.dir"));
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


    public void SaveSettings () throws IOException {
        FileOutputStream fos
                = new FileOutputStream("Settings.ini");
        ObjectOutputStream oos
                = new ObjectOutputStream(fos);
        oos.writeObject(Instance);
    }
}
