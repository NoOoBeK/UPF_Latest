package InWork.Operations;

import InWork.DataBase.DataStructure.LiveLoadKTW;
import InWork.DataBase.DataStructure.LiveLoadPOL;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class Calculations {

    static public ArrayList<LiveLoadKTW> ProductionPlan(ArrayList<ArrayList<LiveLoadKTW>> all){
      ArrayList<LiveLoadKTW> Plan = new ArrayList<>();
      for(int i = 1; i < all.size(); i++){
          for (LiveLoadKTW dane : all.get(i)){
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
                      newRecord.setEDate(Math.floor(StartTime));
                      newRecord.setETime(StartTime - Math.floor(StartTime));
                  } else
                  {
                      newRecord.setPalletCount(dane.getPalletCount() - handlePallet);
                      handlePallet += dane.getPalletCount() - handlePallet;
                      StartTime += (dane.getPalletCount() - handlePallet) * dane.getProductionTime();
                      newRecord.setEDate(Math.floor(StartTime));
                      newRecord.setETime(StartTime - Math.floor(StartTime));
                  }
                  Plan.add(newRecord);
              }
          }
      }
        return Plan;
    }

    static public ArrayList<LiveLoadPOL> PolandLiveLoad(ArrayList<LiveLoadKTW> list)
    {
        int StartPalletCount = 0;
        ArrayList<LiveLoadPOL> ret = new ArrayList<>();
        Date Now = new Date();
        double Start = Math.abs((((Now.getTime() / 100) / 60) / 60) / 24) + (Now.getHours() / 24);
        if (Now.getMinutes() >= 30) Start += 1/48;
        double End = 0.0;
        double checkenEnd = 0.0;
        for (LiveLoadKTW dane : list)
        {
            checkenEnd = dane.getEDate() + dane.getETime();
            if (End < checkenEnd) End = checkenEnd;
        }
        while (Start < End)
        {
            LiveLoadPOL newRecord = new LiveLoadPOL();
            newRecord.setDate(Math.floor(Start));
            newRecord.setTime(Start - Math.floor(Start));
            int PalletCount = StartPalletCount;
            for (LiveLoadKTW dane : list)
            {
                if (Start < (dane.getSDate() + dane.getSTime()) && Start > (dane.getEDate() + dane.getETime()))
                {
                    double checkTime = (Start - (dane.getEDate() + dane.getETime()));
                    if (checkTime > (1 / 48)) checkTime = 1 / 48;
                    PalletCount += Math.abs(checkTime * dane.getProductionTime());
                }
            }
            Start += 1 / 48;
        }
        return ret;
    }
}
