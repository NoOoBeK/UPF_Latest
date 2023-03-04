package InWork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage Stage) throws Exception {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectFactory.fxml"));
        Scene scene = new Scene(root);
        Stage.setTitle("Upfield");
        Stage.setScene(scene);
        Stage.getIcons().add(new Image("/logo.png"));
        Stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
