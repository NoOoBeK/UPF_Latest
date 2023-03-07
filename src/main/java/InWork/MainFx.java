package InWork;

import InWork.GUI.GUIController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) {
        GUIController.StartGUI(stage);
    }
}
