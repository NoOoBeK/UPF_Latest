package InWork.Operations;

import InWork.DataBase.DataStructure.LiveLoadKTW;

import java.util.ArrayList;

public class Calculations {

    static public ArrayList<LiveLoadKTW> ProductionPlan(ArrayList<ArrayList<LiveLoadKTW>> all){
      ArrayList<LiveLoadKTW> Plan = new ArrayList<>();
      for(ArrayList<LiveLoadKTW> list : all){
          for (LiveLoadKTW dane : list){
              int handlePallet = 0;
              double StartTime = dane.getSDate() + dane.getSTime();
              while (handlePallet < dane.getPalletCount())
              {
                  LiveLoadKTW newRecord = new LiveLoadKTW(dane);
                  newRecord.setSDate(Math.floor(StartTime));
                  newRecord.setSTime(StartTime - Math.floor(StartTime));
                  if ((dane.getPalletCount() - handlePallet) > dane.getMaxPallet())
                  {
                      handlePallet += dane.getMaxPallet();
                      newRecord.setPalletCount(dane.getMaxPallet());
                      StartTime += dane.getMaxPallet() * dane.getProductionTime();
                  } else
                  {
                      handlePallet += dane.getPalletCount() - handlePallet;
                      newRecord.setPalletCount(dane.getPalletCount() - handlePallet);
                      StartTime += (dane.getPalletCount() - handlePallet) * dane.getProductionTime();
                  }
                  Plan.add(newRecord);
              }
          }
      }
        return Plan;
    }
}
