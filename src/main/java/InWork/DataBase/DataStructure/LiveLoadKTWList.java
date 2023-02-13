package InWork.DataBase.DataStructure;

import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveLoadKTWList {

    private static final int[] PL = {2621};
    private static final int[] DE = {1021,1022,3921,2221};
    private static final int[] SE = {1921};
    private static final int[] FR = {1221};
    private static final int[] GR = {3421};
    private static final int[] ES_PT = {2425,3522};
    private static final int[] NL = {3722};
    private static final int[] UK = {1121};
    private static final int[] CEE = {2022/2121};
    private static ArrayList<int[]> Clusters;

    private static LiveLoadKTWList Instance;
    public static LiveLoadKTWList getInstance()
    {
        if (Instance == null) { Instance = new LiveLoadKTWList();}
        return Instance;
    }

    public LiveLoadKTWList() {
        Clusters.add(PL);
        Clusters.add(DE);
        Clusters.add(SE);
        Clusters.add(FR);
        Clusters.add(GR);
        Clusters.add(ES_PT);
        Clusters.add(NL);
        Clusters.add(UK);
        Clusters.add(CEE);
    }

    public ArrayList<ArrayList<LiveLoadKTW>> CreatData()
    {
        DataKTW corrData;
        ArrayList<ArrayList<LiveLoadKTW>> dane = new ArrayList<>();
        for (int i = 0; i <= Clusters.size(); i++) {
            dane.add(new ArrayList<>());
        }
        for (DataPP danePP : ExcelAPI.ImportPP(null)) {
            corrData = DataKTWList.getInstance().getKTW(danePP.getSKU());
            if (corrData == null) {
                JOptionPane.showMessageDialog(null, "Brak SKU w bazie danych Zaktualizuj Baze Danych");
                return null;
            }
            LiveLoadKTW newRecord = new LiveLoadKTW();
            newRecord.setSku(danePP.getSKU());
            newRecord.setName(corrData.getName());
            Double temp = Math.ceil(danePP.getQNT() / corrData.getNet());
            newRecord.setPalletCount(temp.intValue());
            temp = Math.floor(22500 / corrData.getGross());
            newRecord.setMaxPallet(temp.intValue());
            temp = (danePP.getSDate() + danePP.getSTime()) - (danePP.getEDate() + danePP.getETime());
            newRecord.setProductionTime(temp);
            newRecord.setSDate(danePP.getSDate());
            newRecord.setSTime(danePP.getSTime());
            newRecord.setDest(corrData.getDest());
            newRecord.setLine(danePP.getLane());


            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(newRecord.getDest());
            int DestID = -1;
            if( m.find() ) {
                DestID = Integer.valueOf(m.group());
            }
            int index = -1;
            for (int i =0; i < Clusters.size(); i++) {
                for (int CheckID : Clusters.get(i))
                {
                    if (DestID == CheckID)
                    {
                        index = i;
                        break;
                    }
                }
                if (index > -1) {break;}
            }
            if (index == -1) {dane.get(Clusters.size()).add(newRecord);}
            else             {dane.get(index).add(newRecord);}
        }
        System.out.println(dane);
        return dane;
    }
}
