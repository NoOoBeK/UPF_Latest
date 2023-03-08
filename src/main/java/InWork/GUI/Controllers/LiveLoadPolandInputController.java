package InWork.GUI.Controllers;

import InWork.GUI.GUIController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LiveLoadPolandInputController implements Initializable {
    @FXML
    private TreeView<String> Tree;
    @FXML
    private TextField PaletCount;
    @FXML
    private DatePicker SelectedDate;
    @FXML
    private TextField SelectedTime;

    @FXML
    void AddPallet(ActionEvent event) {
        boolean added = false;

        String newDate = SelectedDate.getValue().toString();
        String newTime = SelectedTime.getText();
        String newPalletCount = PaletCount.getText();
        if (newTime.equals("") || newPalletCount.equals("") || newDate.equals(""))
        {
            //TO DO Message
            return;
        }

        boolean CheckDate = false;
        boolean CheckTime;
        for (TreeItem<String> nodeDate : Tree.getRoot().getChildren())
        {
            if (nodeDate.getValue().equals(newDate))
            {
                CheckDate = true;
                CheckTime = false;
                for (TreeItem<String> nodeTime : nodeDate.getChildren())
                {

                    if (nodeTime.getValue().equals(newTime)) {
                        Integer PalletCount = Integer.parseInt(nodeTime.getChildren().get(0).getValue());
                        PalletCount += Integer.parseInt(newPalletCount);
                        nodeTime.getChildren().get(0).setValue(PalletCount.toString());
                        CheckTime = true;
                        break;
                    }
                }
                if (!CheckTime)
                {
                    TreeItem newNodeTime = new TreeItem<>(newTime);
                    newNodeTime.getChildren().add(new TreeItem<>(newPalletCount));
                    nodeDate.getChildren().add(newNodeTime);
                }
                break;
            }
        }
        if (!CheckDate)
        {
            TreeItem newNodeDate = new TreeItem<>(newDate);
            TreeItem newNodeTime = new TreeItem<>(newTime);
            //newNodeTime.getChildren().add(new TreeItem<>(newPalletCount));
            //newNodeDate.getChildren().add(newNodeDate);
            Tree.getRoot().getChildren().add(newNodeDate);
        }
    }

    @FXML
    void RemoveSelected(ActionEvent event) {
        ArrayList<TreeItem<String>> toRemove = new ArrayList<>();
        for (TreeItem<String> node : Tree.selectionModelProperty().get().getSelectedItems())
        {
            if (node != Tree.getRoot()) {
                toRemove.add(node);
            }
        }
        if (toRemove.size() < 1) GUIController.showMsgWarrning(Alert.AlertType.INFORMATION, "Select in Tree View to Remove");

        for (TreeItem<String> node : toRemove)
        {
            if (node.getParent() != null) node.getParent().getChildren().remove(node);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<>("Pallet To Add");
        Tree.setRoot(root);
        SelectedDate.setShowWeekNumbers(true);
        PaletCount.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        SelectedTime.setTextFormatter(new TextFormatter<>(new LocalTimeStringConverter()));
    }

    public ArrayList<Pair<LocalDateTime, Integer>> getReturnValue() {
        ArrayList<Pair<LocalDateTime, Integer>> ret = new ArrayList<>();
        LocalTime newLocalTime;
        LocalDate newLocalDate;
        int newCount;
        for (TreeItem<String> dateNode : Tree.getRoot().getChildren())
        {
            for(TreeItem<String> TimeNode : dateNode.getChildren())
            {
                for (TreeItem<String> CountNode : TimeNode.getChildren()) {
                    newLocalDate = LocalDate.parse(dateNode.getValue());
                    newLocalTime = LocalTime.parse(TimeNode.getValue());
                    newCount = Integer.parseInt(CountNode.getValue());
                    ret.add(new Pair<>(newLocalDate.atTime(newLocalTime), newCount));
                }
            }
        }
        return ret;
    }
}
