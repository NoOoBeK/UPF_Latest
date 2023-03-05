package InWork.Tasks;

import InWork.Controllers.ExcelController;
import InWork.DataStructure.Collection.LiveLoadKTWList;
import InWork.DataStructure.LiveLoadKTW;
import InWork.DataStructure.LiveLoadPOL;
import InWork.Operations.Calculations;
import javafx.concurrent.Task;
import java.util.ArrayList;

public class LiveLoadKTWTask extends Task {
    private boolean Ireland;
    private boolean Plan;
    private boolean Poland;

    public LiveLoadKTWTask (boolean Ireland, boolean Plan, boolean Poland)
    {
        this.Ireland = Ireland;
        this.Plan = Plan;
        this.Poland = Poland;
    }

    @Override
    protected Object call() throws Exception {
        ArrayList<ArrayList<InWork.DataStructure.LiveLoadKTW>> data;
        if (Ireland || Plan || Poland) data = LiveLoadKTWList.getInstance().CreateData();
        else return null;
        int count = 0;
        for (ArrayList<LiveLoadKTW> list : data) {
            count += list.size();
        }

        if (Ireland) ExcelController.IrelandSplit(data.get(7));
        if (Plan) {
            ArrayList<InWork.DataStructure.LiveLoadKTW> calculated = Calculations.ProductionPlan(data);
            ExcelController.ProductionPlan(calculated);
        }
        if (Poland) {
            ArrayList<LiveLoadPOL> pol = Calculations.PolandLiveLoad(data.get(0));
            ExcelController.Poland(pol);
        }
        return null;
    }
}
