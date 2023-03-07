package InWork;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.*;
import java.util.Date;

public class Settings implements java.io.Serializable {
    private static Settings Instance = null;
    private Date LastimportKTW;
    private StringProperty FileChoserPath;
    private BooleanProperty DarkMode;

    private StringProperty LiveLoadKTWIrelandSavePath;
    private StringProperty LiveLoadKTWPlanSavePath;
    private StringProperty LiveLoadKTWPolandSavePath;

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
        String DefaultPath = System.getProperty("user.dir");
        FileChoserPath.set(DefaultPath);
        LiveLoadKTWIrelandSavePath.set(DefaultPath);
        LiveLoadKTWPlanSavePath.set(DefaultPath);
        LiveLoadKTWPolandSavePath.set(DefaultPath);
        DarkMode.set(false);
        DarkMode.addListener((o, oldVal, newVal) -> {
            Platform.runLater(() -> {
                for (Window window : Window.getWindows())
                {
                    if (newVal) ((Stage)window).getScene().getStylesheets().add(getClass().getResource("Dark.css").toExternalForm());
                    else        ((Stage)window).getScene().getStylesheets().remove(getClass().getResource("Dark.css").toExternalForm());
                }
            });
        });
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

    public boolean isDarkMode() {
        return DarkMode.get();
    }

    public BooleanProperty darkModeProperty() {
        return DarkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.DarkMode.set(darkMode);
    }

    private SimpleIntegerProperty LiveLoadKTWPolandStep;
    private SimpleIntegerProperty LiveLoadKTWStepToSkipTruck;
    private SimpleIntegerProperty LiveLoadKTWMinPaletValueNoSkip;



    public String getLiveLoadKTWIrelandSavePath() {
        return LiveLoadKTWIrelandSavePath.get();
    }

    public int getLiveLoadKTWPolandStep() {
        return LiveLoadKTWPolandStep.get();
    }

    public SimpleIntegerProperty liveLoadKTWPolandStepProperty() {
        return LiveLoadKTWPolandStep;
    }

    public void setLiveLoadKTWPolandStep(int liveLoadKTWPolandStep) {
        this.LiveLoadKTWPolandStep.set(liveLoadKTWPolandStep);
    }

    public int getLiveLoadKTWStepToSkipTruck() {
        return LiveLoadKTWStepToSkipTruck.get();
    }

    public SimpleIntegerProperty liveLoadKTWStepToSkipTruckProperty() {
        return LiveLoadKTWStepToSkipTruck;
    }

    public void setLiveLoadKTWStepToSkipTruck(int liveLoadKTWStepToSkipTruck) {
        this.LiveLoadKTWStepToSkipTruck.set(liveLoadKTWStepToSkipTruck);
    }

    public int getLiveLoadKTWMinPaletValueNoSkip() {
        return LiveLoadKTWMinPaletValueNoSkip.get();
    }

    public SimpleIntegerProperty liveLoadKTWMinPaletValueNoSkipProperty() {
        return LiveLoadKTWMinPaletValueNoSkip;
    }

    public void setLiveLoadKTWMinPaletValueNoSkip(int liveLoadKTWMinPaletValueNoSkip) {
        this.LiveLoadKTWMinPaletValueNoSkip.set(liveLoadKTWMinPaletValueNoSkip);
    }

    public StringProperty liveLoadKTWIrelandSavePathProperty() {
        return LiveLoadKTWIrelandSavePath;
    }

    public void setLiveLoadKTWIrelandSavePath(String liveLoadKTWIrelandSavePath) {
        this.LiveLoadKTWIrelandSavePath.set(liveLoadKTWIrelandSavePath);
    }

    public String getLiveLoadKTWPlanSavePath() {
        return LiveLoadKTWPlanSavePath.get();
    }

    public StringProperty liveLoadKTWPlanSavePathProperty() {
        return LiveLoadKTWPlanSavePath;
    }

    public void setLiveLoadKTWPlanSavePath(String liveLoadKTWPlanSavePath) {
        this.LiveLoadKTWPlanSavePath.set(liveLoadKTWPlanSavePath);
    }

    public String getLiveLoadKTWPolandSavePath() {
        return LiveLoadKTWPolandSavePath.get();
    }

    public StringProperty liveLoadKTWPolandSavePathProperty() {
        return LiveLoadKTWPolandSavePath;
    }

    public void setLiveLoadKTWPolandSavePath(String livLoadKTWPolandSavePath) {
        this.LiveLoadKTWPolandSavePath.set(livLoadKTWPolandSavePath);
    }


    public String getFileChoserPath() {
        return FileChoserPath.get();
    }

    public StringProperty fileChoserPathProperty() {
        return FileChoserPath;
    }

    public void setFileChoserPath(String fileChoserPath) {
        this.FileChoserPath.set(fileChoserPath);
    }

    public Date getLastimportKTW() {
        return LastimportKTW;
    }
    public void setLastimportKTW(Date lastimportKTW) {
        this.LastimportKTW = lastimportKTW;
    }

}
