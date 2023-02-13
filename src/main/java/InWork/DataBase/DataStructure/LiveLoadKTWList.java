package InWork.DataBase.DataStructure;

import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveLoadKTWList {
    private static ArrayList<ArrayList<Integer>> Clusters;

    private static LiveLoadKTWList Instance;
    public static LiveLoadKTWList getInstance()
    {
        if (Instance == null) { Instance = new LiveLoadKTWList();}
        return Instance;
    }

    public LiveLoadKTWList() {
        Clusters = new ArrayList<>();
        Clusters.add(new ArrayList(Arrays.asList(2621))); // PL
        Clusters.add(new ArrayList(Arrays.asList(1021, 1022, 3921, 2221))); // DE
        Clusters.add(new ArrayList(Arrays.asList(1921))); // SE
        Clusters.add(new ArrayList(Arrays.asList(1221))); // FE
        Clusters.add(new ArrayList(Arrays.asList(3421))); // GR
        Clusters.add(new ArrayList(Arrays.asList(2425, 3522))); // ES/PT
        Clusters.add(new ArrayList(Arrays.asList(3722))); // NL
        Clusters.add(new ArrayList(Arrays.asList(1121))); // UK
        Clusters.add(new ArrayList(Arrays.asList(2022, 2121))); // CEE
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
            if (newRecord.getDest().toLowerCase().contains("fresh")) { m.find();}
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
            if (index == -1)
            {
                System.out.println(DestID);
                System.out.println(newRecord.getDest());
                dane.get(Clusters.size()).add(newRecord);
            }
            else             {dane.get(index).add(newRecord);}
        }
        for (ArrayList<LiveLoadKTW> test: dane) {
            System.out.println(test);
        }
        return dane;
    }
}
