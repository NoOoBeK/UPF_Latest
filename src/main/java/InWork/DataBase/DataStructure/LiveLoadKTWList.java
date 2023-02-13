package InWork.DataBase.DataStructure;

import InWork.DataBase.ExcelAPI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveLoadKTWList {

    private static LiveLoadKTWList Instance;
    public static LiveLoadKTWList getInstance()
    {
        if (Instance == null) { Instance = new LiveLoadKTWList();}
        return Instance;
    }

    public LiveLoadKTWList() {

    }

    public boolean CreatData()
    {
        System.out.println("Start");
        DataKTW corrData;
        ArrayList<LiveLoadKTW> dane = new ArrayList<>();

        for (DataPP danePP : ExcelAPI.ImportPP(null)) {
            corrData = DataKTWList.getInstance().getKTW(danePP.getSKU());
            if (corrData == null) {
                JOptionPane.showMessageDialog(null, "Brak SKU w bazie danych Zaktualizuj Baze Danych");
                return false;
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


            Pattern p = Pattern.compile("is (-?\\d+(\\.\\d+)?)");
            Matcher m = p.matcher(newRecord.getDest());
            String test = "";
            while( m.find() ) {
                test = " " + m.group();
            }
            System.out.println(newRecord.getDest());
            System.out.println(test);
            /*if (newRecord.getDest().toLowerCase().contains("fresh"))
            {
                System.out.println(newRecord.getDest() + "   test " + newRecord.getDest().indexOf("->"));
            } else
            {

            }*/
            dane.add(newRecord);
        }
        System.out.println("End");
        return true;
    }
}
