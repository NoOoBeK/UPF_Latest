package InWork.GUI.Controllers;

import InWork.GUI.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class SelectFactoryController {
    @FXML
    private Tab KatowiceTab;
    @FXML
    private Tab LogisticTab;

    @FXML
    void OpenSettings(ActionEvent event) {
        GUIController.getInstance().showSettings();
    }
}
