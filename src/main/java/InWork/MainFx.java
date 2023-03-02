package InWork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage Stage) throws Exception {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectFactory.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/css/application.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage.setScene(scene);
        Stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
