package InWork.Controllers.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SelectFactoryController {

    private Stage StageTableKTW;
    private TableKTWController ControlletTableKTW;

    private void CreatKTWDBTable ()
    {
        try {
            if (StageTableKTW == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TableKTW.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Scene scene = new Scene(root);
                StageTableKTW = new Stage();
                StageTableKTW.setScene(scene);
                StageTableKTW.show();
                ControlletTableKTW = fxmlLoader.getController();
            } else
            {
                if (StageTableKTW.isIconified()) StageTableKTW.setIconified(false);
                if (!StageTableKTW.isShowing())StageTableKTW.show();
                StageTableKTW.toFront();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ImportKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
        ControlletTableKTW.ImportKTW(null);
    }

    public void ViewDataKTW(ActionEvent actionEvent) {
        CreatKTWDBTable();
    }
}
