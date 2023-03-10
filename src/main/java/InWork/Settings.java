package InWork;

import InWork.GUI.GUIController;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Settings {
    private static Settings Instance = null;
    private ObjectProperty<LocalDateTime> LastimportKTW;
    private StringProperty FileChoserPath;
    private BooleanProperty DarkMode;

    private StringProperty LiveLoadKTWIrelandSavePath;
    private StringProperty LiveLoadKTWPlanSavePath;
    private StringProperty LiveLoadKTWPolandSavePath;
    private ObjectProperty<LocalTime>  LiveLoadKTWPolandStep;
    private IntegerProperty LiveLoadKTWStepToSkipTruck;
    private IntegerProperty LiveLoadKTWMinPaletValueNoSkip;

    public static Settings getInstance() {
        if (Instance == null) LoadSettings(true);
        return Instance;
    }
    private Settings() {
        LastimportKTW = new SimpleObjectProperty<>(null);
        String DefaultPath = System.getProperty("user.dir");
        FileChoserPath = new SimpleStringProperty(DefaultPath);
        LiveLoadKTWIrelandSavePath = new SimpleStringProperty(DefaultPath);
        LiveLoadKTWPlanSavePath = new SimpleStringProperty(DefaultPath);
        LiveLoadKTWPolandSavePath = new SimpleStringProperty(DefaultPath);
        DarkMode = new SimpleBooleanProperty(false);
        LiveLoadKTWPolandStep = new SimpleObjectProperty<>(LocalTime.of(1,0));
        LiveLoadKTWStepToSkipTruck = new SimpleIntegerProperty(4);
        LiveLoadKTWMinPaletValueNoSkip = new SimpleIntegerProperty(25);
    }
    private static void LoadSettings (boolean ShowInfo) {
        File file = new File(System.getProperty("user.dir") + "/Settings.cfg");
        Instance = new Settings();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            String Setting[];
            try {
                while ((line = fileReader.readLine()) != null) {

                    Setting = line.split("=");
                    if (Setting.length == 2) {
                        if (Setting[0].equals("LastImportKTW"))
                        {
                            if (Setting[1].equals("")) Instance.LastimportKTW.set(null);
                            else Instance.LastimportKTW.set(LocalDateTime.parse(Setting[1]));
                        }
                        else if (Setting[0].equals("FileChoserPath")) Instance.FileChoserPath.set(Setting[1]);
                        else if (Setting[0].equals("DarkMode")) Instance.DarkMode.set(Boolean.valueOf(Setting[1]));
                        else if (Setting[0].equals("LiveLoadKTWIrelandSavePath")) Instance.LiveLoadKTWIrelandSavePath.set(Setting[1]);
                        else if (Setting[0].equals("LiveLoadKTWPlanSavePath")) Instance.LiveLoadKTWPlanSavePath.set(Setting[1]);
                        else if (Setting[0].equals("LiveLoadKTWPolandSavePath")) Instance.LiveLoadKTWPolandSavePath.set(Setting[1]);
                        else if (Setting[0].equals("LiveLoadKTWPolandStep")) Instance.LiveLoadKTWPolandStep.set(LocalTime.parse(Setting[1]));
                        else if (Setting[0].equals("LiveLoadKTWStepToSkipTruck")) Instance.LiveLoadKTWStepToSkipTruck.set(Integer.valueOf(Setting[1]));
                        else if (Setting[0].equals("LiveLoadKTWMinPaletValueNoSkip")) Instance.LiveLoadKTWMinPaletValueNoSkip.set(Integer.valueOf(Setting[1]));
                    }
                }
            } finally {
                fileReader.close();
            }
        } catch (FileNotFoundException e) {
            if (ShowInfo) GUIController.showInformDialog("", "", "Can't find Settings, load default settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SaveSettings () {
        try {
            File file = new File(System.getProperty("user.dir") + "/Settings.cfg");
            file.createNewFile();
            Writer fileWriter = new FileWriter(file, false);
            String LastimportKTWTxt = "";
            if (LastimportKTW.getValue() != null) LastimportKTWTxt = LastimportKTW.getValue().toString();
            fileWriter.write("LastImportKTW=" + LastimportKTWTxt + "\n");
            fileWriter.write("DarkMode=" + DarkMode.getValue() + "\n");
            fileWriter.write( "FileChoserPath=" + FileChoserPath.getValue() + "\n");
            fileWriter.write( "LiveLoadKTWIrelandSavePath=" + liveLoadKTWIrelandSavePathProperty().getValue() + "\n");
            fileWriter.write( "LiveLoadKTWPlanSavePath=" + liveLoadKTWPlanSavePathProperty().getValue() + "\n");
            fileWriter.write( "LiveLoadKTWPolandSavePath=" + liveLoadKTWPolandSavePathProperty().getValue() + "\n");
            fileWriter.write("LiveLoadKTWPolandStep=" + LiveLoadKTWPolandStep.getValue().toString() + "\n");
            fileWriter.write("LiveLoadKTWStepToSkipTruck=" + LiveLoadKTWStepToSkipTruck.getValue() + "\n");
            fileWriter.write("LiveLoadKTWMinPaletValueNoSkip=" + LiveLoadKTWMinPaletValueNoSkip.getValue() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            GUIController.showWarrningDialog("","Save Settings Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void DefaultSetting()
    {
        LastimportKTW.set(null);
        String DefaultPath = System.getProperty("user.dir");
        FileChoserPath.set(DefaultPath);
        LiveLoadKTWIrelandSavePath.set(DefaultPath);
        LiveLoadKTWPlanSavePath.set(DefaultPath);
        LiveLoadKTWPolandSavePath.set(DefaultPath);
        DarkMode.set(false);
        LiveLoadKTWPolandStep.set(LocalTime.of(1,0));
        LiveLoadKTWStepToSkipTruck.set(4);
        LiveLoadKTWMinPaletValueNoSkip.set(25);
        SaveSettings();
    }
    public void RevertSetting()
    {
        File file = new File(System.getProperty("user.dir") + "/Settings.cfg");
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            String Setting[];
            try {
                while ((line = fileReader.readLine()) != null) {

                    Setting = line.split("=");
                    if (Setting.length == 2) {
                        if (Setting[0].equals("LastImportKTW"))
                        {
                            if (Setting[1].equals("")) Instance.LastimportKTW.set(null);
                            else Instance.LastimportKTW.set(LocalDateTime.parse(Setting[1]));
                        }
                        else if (Setting[0].equals("FileChoserPath")) Instance.FileChoserPath.set(Setting[1]);
                        else if (Setting[0].equals("DarkMode")) Instance.DarkMode.set(Boolean.valueOf(Setting[1]));
                        else if (Setting[0].equals("LiveLoadKTWIrelandSavePath")) Instance.LiveLoadKTWIrelandSavePath.set(Setting[1]);
                        else if (Setting[0].equals("LiveLoadKTWPlanSavePath")) Instance.LiveLoadKTWPlanSavePath.set(Setting[1]);
                        else if (Setting[0].equals("LiveLoadKTWPolandSavePath")) Instance.LiveLoadKTWPolandSavePath.set(Setting[1]);
                        else if (Setting[0].equals("LiveLoadKTWPolandStep")) Instance.LiveLoadKTWPolandStep.set(LocalTime.parse(Setting[1]));
                        else if (Setting[0].equals("LiveLoadKTWStepToSkipTruck")) Instance.LiveLoadKTWStepToSkipTruck.set(Integer.valueOf(Setting[1]));
                        else if (Setting[0].equals("LiveLoadKTWMinPaletValueNoSkip")) Instance.LiveLoadKTWMinPaletValueNoSkip.set(Integer.valueOf(Setting[1]));
                    }
                }
            } finally {
                fileReader.close();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public String getLiveLoadKTWIrelandSavePath() {
        return LiveLoadKTWIrelandSavePath.get();
    }
    public LocalTime getLiveLoadKTWPolandStep() {
        return LiveLoadKTWPolandStep.getValue();
    }
    public ObjectProperty<LocalTime> liveLoadKTWPolandStepProperty() {
        return LiveLoadKTWPolandStep;
    }
    public void setLiveLoadKTWPolandStep(LocalTime liveLoadKTWPolandStep) {
        this.LiveLoadKTWPolandStep.set(liveLoadKTWPolandStep);
    }
    public int getLiveLoadKTWStepToSkipTruck() {
        return LiveLoadKTWStepToSkipTruck.get();
    }
    public IntegerProperty liveLoadKTWStepToSkipTruckProperty() {
        return LiveLoadKTWStepToSkipTruck;
    }
    public void setLiveLoadKTWStepToSkipTruck(int liveLoadKTWStepToSkipTruck) {
        this.LiveLoadKTWStepToSkipTruck.set(liveLoadKTWStepToSkipTruck);
    }
    public int getLiveLoadKTWMinPaletValueNoSkip() {
        return LiveLoadKTWMinPaletValueNoSkip.get();
    }
    public IntegerProperty liveLoadKTWMinPaletValueNoSkipProperty() {
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
    public ObjectProperty<LocalDateTime> getLastimportKTWProperty()
    {
        return LastimportKTW;
    }
    public LocalDateTime getLastimportKTW() {
        return LastimportKTW.getValue();
    }
    public void setLastimportKTW(LocalDateTime lastimportKTW) {
        this.LastimportKTW.set(lastimportKTW);
    }
}
