package InWork.Operations;

import InWork.DataBase.DataStructure.LiveLoadKTW;
import InWork.DataBase.DataStructure.LiveLoadPOL;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;

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
        int PalletCount = 0;
        ArrayList<LiveLoadPOL> ret = new ArrayList<>();
        Date Now = new Date();
        if (Now.getMinutes() >= 30)
        {
            Now.setTime(Now.getTime() + (3600000));
        }
        Now.setMinutes(0);
        Now.setSeconds(0);

        double Start = DateUtil.getExcelDate(Now);
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

            for (LiveLoadKTW dane : list)
            {
                double StartProduction =dane.getSDate() + dane.getSTime();
                double EndProduction = dane.getEDate() + dane.getETime();
                if (Start < StartProduction && StartProduction < (Start + (1.0 / 48.0)) )
                {
                    double productionTime = 0.0;
                    if (EndProduction > (Start + (1.0 / 48.0))) productionTime = (Start + (1.0 / 48.0)) - StartProduction;
                    else                                        productionTime = EndProduction - StartProduction;
                    PalletCount += Math.abs(productionTime / dane.getProductionTime());
                } else if (Start > StartProduction && EndProduction > Start)
                {
                    double productionTime = 0.0;
                    if (EndProduction > (Start + (1.0 / 48.0))) productionTime = (1.0 / 48.0);
                    else                                        productionTime = EndProduction - Start;
                    PalletCount += Math.abs(productionTime / dane.getProductionTime());
                }
            }
            newRecord.setPaletCoun(PalletCount);
            StartPalletCount += PalletCount;

            int NeddedTruck = 0;
            while (PalletCount >= 30)
            {
                NeddedTruck += 1;
                PalletCount -= 30;
            }
            newRecord.setNeededTruck(NeddedTruck);
            Start += (1.0 / 48.0);
            ret.add(newRecord);
        }
        System.out.println(ret.size());
        return ret;
    }
}
