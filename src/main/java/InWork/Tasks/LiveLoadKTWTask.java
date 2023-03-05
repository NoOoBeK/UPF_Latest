package InWork.Tasks;

import InWork.Controllers.ExcelController;
import InWork.DataStructure.Collection.DataKTWList;
import InWork.DataStructure.DataKTW;
import InWork.DataStructure.DataPP;
import InWork.DataStructure.LiveLoadKTW;
import InWork.DataStructure.LiveLoadPOL;
import InWork.GUI.PopUpWindow;
import InWork.Operations.CheckType;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiveLoadKTWTask extends Task {
    private final boolean Ireland;
    private final boolean Plan;
    private final boolean Poland;
    private final File SourceFile;
    private int AllToHandle;
    private int Handle;

    public LiveLoadKTWTask (File SourceFile, boolean Ireland, boolean Plan, boolean Poland)
    {
        this.Ireland = Ireland;
        this.Plan = Plan;
        this.Poland = Poland;
        this.SourceFile = SourceFile;
        AllToHandle = 0;
        Handle = 0;
    }

    private ArrayList<DataPP> ImportExcelFile()
    {
        updateMessage("Import excel data");
        updateProgress(-1,1);
        ArrayList<DataPP> ret = new ArrayList<>();

        Workbook WorkBook = ExcelController.ImportFile(SourceFile);
        Sheet sheet = WorkBook != null ? WorkBook.getSheetAt(0) : null;
        DataPP newRecord;
        int source;
        Cell cell;
        double skudouble;
        updateMessage("Analysis of imported excel data");
        int RowNum = sheet != null ? sheet.getLastRowNum() : 0;
        AllToHandle = (RowNum * 2) * 3;

        for(source = 1 ; source < RowNum +1; ++source) {
            newRecord = new DataPP();
            cell = sheet.getRow(source).getCell(7);
            skudouble = cell.getNumericCellValue();
            if ((int) skudouble < 1) {
                break;}
            newRecord.setSKU((int) skudouble);
            cell = sheet.getRow(source).getCell(0);
            newRecord.setSDate(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(1);
            newRecord.setSTime(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(2);
            newRecord.setEDate(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(3);
            newRecord.setETime(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(6);
            String temp = cell.toString();
            temp = temp.replaceAll("\\s", "");
            if(temp.contains(","))
            {

                temp = temp.replace(".","");
                temp = temp.replace(",",".");
            }
            if(temp.indexOf(".") < 4)
            {
                temp = temp.replace(".","");
            }
            newRecord.setQNT(Double.parseDouble(temp));
            cell = sheet.getRow(source).getCell(10);
            newRecord.setLane(cell.getStringCellValue());
            ret.add(newRecord);
            Handle++;
            updateProgress(Handle, AllToHandle);
        }
        return ret;
    }
    private ArrayList<ArrayList<LiveLoadKTW>> ConvertPPdata(ArrayList<DataPP> data)
    {
        updateMessage("Calcualtion");
        ArrayList<ArrayList<Integer>> Cluster = new ArrayList<>();
        Cluster.add(new ArrayList<>(List.of(2621))); // PL
        Cluster.add(new ArrayList<>(Arrays.asList(1021, 1022, 3921, 2221))); // DE
        Cluster.add(new ArrayList<>(List.of(1921))); // SE
        Cluster.add(new ArrayList<>(List.of(1221))); // FE
        Cluster.add(new ArrayList<>(List.of(3421))); // GR
        Cluster.add(new ArrayList<>(Arrays.asList(2425, 3522))); // ES/PT
        Cluster.add(new ArrayList<>(List.of(3722))); // NL
        Cluster.add(new ArrayList<>(List.of(1121))); // UK
        Cluster.add(new ArrayList<>(Arrays.asList(2022, 2121))); // CEE


        DataKTW corrData;
        ArrayList<ArrayList<LiveLoadKTW>> ret = new ArrayList<>();
        for (int i = 0; i <= Cluster.size(); i++) {
            ret.add(new ArrayList<>());
        }
        for (DataPP danePP : data) {
            corrData = DataKTWList.getInstance().getKTW(danePP.getSKU());
            if (corrData == null) {
                PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING, "SKU " + danePP.getSKU() + " not find in Data Base");
                return ret;
            }
            LiveLoadKTW newRecord = new LiveLoadKTW();
            newRecord.setSku(danePP.getSKU());
            newRecord.setName(corrData.getName());
            double temp = Math.ceil(danePP.getQNT() / corrData.getNet());
            newRecord.setPalletCount((int) temp);
            temp = Math.floor(22500 / corrData.getGross());
            if (temp > 26 && corrData.getPaltype().equals("IND")) temp = 26.0;
            else if (temp > 33 && corrData.getHeight() >= 1250) temp = 33.0;
            newRecord.setMaxPallet((int) temp);
            newRecord.setEDate(danePP.getEDate());
            newRecord.setETime(danePP.getETime());
            newRecord.setSDate(danePP.getSDate());
            newRecord.setSTime(danePP.getSTime());
            newRecord.setDest(corrData.getDest());
            newRecord.setLine(danePP.getLane());


            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(newRecord.getDest());
            int DestID = -1;
            if (newRecord.getDest().toLowerCase().contains("fresh")) { m.find();}
            if( m.find() ) {
                DestID = Integer.parseInt(m.group());
            }
            int index = -1;
            for (int i =0; i < Cluster.size(); i++) {
                for (int CheckID : Cluster.get(i))
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
                ret.get(Cluster.size()).add(newRecord);
            }
            else             {ret.get(index).add(newRecord);}
            Handle++;
            updateProgress(Handle, AllToHandle);
        }
        return ret;
    }

    private void IrelandSplit(ArrayList<LiveLoadKTW> Ireland){
        updateMessage("Exporting Ireland");
        String name = "Ireland";
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("List");
        int source;
        int rowNum =1;
        XSSFCellStyle data = ExcelController.date(book);

        XSSFRow rowt = sheet.createRow(0);
        for (int celnum = 0; celnum < 4; celnum++) {
            rowt.createCell(celnum);
        }
        sheet.getRow(0).getCell(0).setCellValue("SKU");
        sheet.getRow(0).getCell(1).setCellValue("Desc");
        sheet.getRow(0).getCell(2).setCellValue("Palets");
        sheet.getRow(0).getCell(3).setCellValue("Production Date");

        for (source = 0; source<Ireland.size();source++){
            XSSFRow row = sheet.createRow(rowNum);
            for (int celnum = 0; celnum < 4; celnum++) {
                row.createCell(celnum);
            }
            sheet.getRow(rowNum).getCell(0).setCellValue(Ireland.get(source).getSku());
            sheet.getRow(rowNum).getCell(1).setCellValue(Ireland.get(source).getName());
            sheet.getRow(rowNum).getCell(2).setCellValue(Ireland.get(source).getPalletCount());
            sheet.getRow(rowNum).getCell(3).setCellValue(Ireland.get(source).getSDate());
            sheet.getRow(rowNum).getCell(3).setCellStyle(data);
            rowNum++;
            Handle++;
            updateProgress(Handle, AllToHandle);
        }
        ExcelController.FileOut(book,name);
    }
    private void Poland(ArrayList<LiveLoadPOL> list){
        updateMessage("Exporting Poland");
        String name = "Poland";
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Polska");
        XSSFCellStyle data = ExcelController.date(book);
        XSSFCellStyle time = ExcelController.time(book);
        int rowNum = 1;

        XSSFRow row = sheet.createRow(0);
        for (int celnum = 0; celnum < 6; celnum++) {
            row.createCell(celnum);
        }
        sheet.getRow(0).getCell(0).setCellValue("Data");
        sheet.getRow(0).getCell(1).setCellValue("Godzina");
        sheet.getRow(0).getCell(2).setCellValue("ilosc do zaladunku");
        sheet.getRow(0).getCell(3).setCellValue("Ilosc aut na godzine");
        sheet.getRow(0).getCell(4).setCellValue("FO");
        sheet.getRow(0).getCell(5).setCellValue("Carrier");

        for (LiveLoadPOL liveLoadPOL : list) {

            XSSFRow row1 = sheet.createRow(rowNum);
            for (int celnum = 0; celnum < 4; celnum++) {
                row1.createCell(celnum);
            }
            sheet.getRow(rowNum).getCell(0).setCellValue(liveLoadPOL.getDate());
            sheet.getRow(rowNum).getCell(1).setCellValue(liveLoadPOL.getTime());
            sheet.getRow(rowNum).getCell(2).setCellValue(liveLoadPOL.getPaletCoun());
            sheet.getRow(rowNum).getCell(3).setCellValue(liveLoadPOL.getNeededTruck());
            sheet.getRow(rowNum).getCell(0).setCellStyle(data);
            sheet.getRow(rowNum).getCell(1).setCellStyle(time);
            rowNum++;
            Handle++;
            updateProgress(Handle, AllToHandle);
        }
        ExcelController.FileOut(book,name);
    }
    private void ProductionPlan(ArrayList<LiveLoadKTW> Plan){
        updateMessage("Exporting Plan");
        String name = "Plan";
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Plan");
        XSSFCellStyle nieplanowany = ExcelController.style(book,255,153,204,128,0,128,true);
        XSSFCellStyle FRESH= ExcelController.style(book,0,112,192,255,255,0,false);
        XSSFCellStyle order= ExcelController.style(book,223,159,225,0,0,0,false);
        XSSFCellStyle data = ExcelController.date(book);
        XSSFCellStyle time = ExcelController.time(book);

        int rowNum =0;
        int source;
        int ordernum=1;

        for (source = 0; source<Plan.size();source++){
            rowNum = rowNum +1;
            if ((source > 0) && (Plan.get(source).getSku()!=Plan.get(source-1).getSku())){rowNum = rowNum +1;}
            XSSFRow row = sheet.createRow(rowNum);
            for (int celnum=0;celnum<15;celnum++)
            {
                row.createCell(celnum);
            }
            sheet.getRow(rowNum).getCell(0).setCellValue(Plan.get(source).getSku());
            sheet.getRow(rowNum).getCell(1).setCellValue(Plan.get(source).getName());
            if (((double)Plan.get(source).getPalletCount()/(double)Plan.get(source).getMaxPallet())>0.9)
            {
                sheet.getRow(rowNum).getCell(2).setCellValue("NIEPLANOWANA");
                sheet.getRow(rowNum).getCell(2).setCellStyle(nieplanowany);
            }
            else
            {
                sheet.getRow(rowNum).getCell(2).setCellValue("");
            }
            sheet.getRow(rowNum).getCell(3).setCellValue(Plan.get(source).getPalletCount());
            sheet.getRow(rowNum).getCell(4).setCellValue("PAL");
            sheet.getRow(rowNum).getCell(5).setCellValue(Plan.get(source).getMaxPallet());
            sheet.getRow(rowNum).getCell(6).setCellValue("");
            sheet.getRow(rowNum).getCell(7).setCellValue("");
            if (((double)Plan.get(source).getPalletCount()/(double)Plan.get(source).getMaxPallet())<0.9)
            {
                sheet.getRow(rowNum).getCell(8).setCellValue("po produkcji do FRESH");
                sheet.getRow(rowNum).getCell(8).setCellStyle(FRESH);
            }
            else
            {
                sheet.getRow(rowNum).getCell(8).setCellValue("");
            }
            sheet.getRow(rowNum).getCell(10).setCellValue("");
            if(sheet.getRow(rowNum).getCell(8).getStringCellValue().equals(""))
            {
                sheet.getRow(rowNum).getCell(9).setCellValue(ordernum);
                ordernum=ordernum+1;
                sheet.getRow(rowNum).getCell(9).setCellStyle(order);
            }
            else
            {
                sheet.getRow(rowNum).getCell(9).setCellValue("");
            }
            sheet.getRow(rowNum).getCell(11).setCellValue(Plan.get(source).getSDate());
            sheet.getRow(rowNum).getCell(11).setCellStyle(data);
            sheet.getRow(rowNum).getCell(12).setCellValue(Plan.get(source).getSTime());
            sheet.getRow(rowNum).getCell(12).setCellStyle(time);
            sheet.getRow(rowNum).getCell(13).setCellValue(Plan.get(source).getProductionTime());
            sheet.getRow(rowNum).getCell(14).setCellValue(Plan.get(source).getDest());
            Handle++;
            updateProgress(Handle, AllToHandle);
        }
        ExcelController.FileOut(book,name);
    }

    private ArrayList<LiveLoadKTW> CalculatProductionPlan(ArrayList<ArrayList<LiveLoadKTW>> all){
        updateMessage("Calculate Plan");
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
                    } else
                    {
                        newRecord.setPalletCount(dane.getPalletCount() - handlePallet);
                        handlePallet += dane.getPalletCount() - handlePallet;
                        StartTime += (dane.getPalletCount() - handlePallet) * dane.getProductionTime();
                    }
                    newRecord.setEDate(Math.floor(StartTime));
                    newRecord.setETime(StartTime - Math.floor(StartTime));
                    Plan.add(newRecord);
                }
                Handle++;
                updateProgress(Handle, AllToHandle);
            }
        }
        return Plan;
    }
    private ArrayList<LiveLoadPOL> CalculatPoland(ArrayList<LiveLoadKTW> list) {
        updateMessage("Calculate Poland");
        String input = "";
        while (true)
        {
            input = JOptionPane.showInputDialog(null,"Podaj początkową ilość palet", 0);
            if (CheckType.isInteger(input)) break;
        }
        int PalletCount = Integer.valueOf(input);
        ArrayList<LiveLoadPOL> ret = new ArrayList<>();
        Date Now = new Date();
        if (Now.getMinutes() >= 30)
        {
            Now.setTime(Now.getTime() + (3600000));
        }
        Now.setMinutes(0);
        Now.setSeconds(0);
        System.out.println(Now);

        double Start = DateUtil.getExcelDate(Now);
        double End = 0.0;
        double checkenEnd;
        for (LiveLoadKTW dane : list)
        {
            if ((dane.getSDate() + dane.getSTime()) < Start && dane.getEDate() + dane.getETime() > Start)
            {
                dane.setSDate(Math.floor(Start));
                dane.setSTime(Start - Math.floor(Start));
            }
            checkenEnd = dane.getEDate() + dane.getETime();
            if (End < checkenEnd) End = checkenEnd;
            Handle++;
            updateProgress(Handle, AllToHandle);
        }

        double TimeLenght = 1.0/ 24.0;
        int MoveTrackAfterSteps = 4;
        int MinPalletCountToNoRemoveTruck = 25;
        int LastPallCount = -1;
        int Steps = 0;
        boolean AddSkipedTruck = false;
        int NeddedTruck = PalletCount == 0 ? 1 : 0;
        while (Start < End)
        {
            LiveLoadPOL newRecord = new LiveLoadPOL();
            newRecord.setDate(Math.floor(Start));
            newRecord.setTime(Start - Math.floor(Start));

            for (LiveLoadKTW dane : list)
            {
                double StartProduction = dane.getSDate() + dane.getSTime();
                double EndProduction = dane.getEDate() + dane.getETime();

                if (Start <= StartProduction && StartProduction < (Start + TimeLenght) )
                {
                    double productionTime;
                    if (EndProduction > (Start + TimeLenght)) productionTime = (Start + TimeLenght) - StartProduction;
                    else                                        productionTime = EndProduction - StartProduction;
                    PalletCount += Math.abs(productionTime / dane.getProductionTime());
                } else if (Start > StartProduction && EndProduction > Start)
                {
                    double productionTime;
                    if (EndProduction > (Start + TimeLenght)) productionTime = TimeLenght;
                    else                                        productionTime = EndProduction - Start;
                    PalletCount += Math.abs(productionTime / dane.getProductionTime());
                }
            }
            newRecord.setPaletCoun(PalletCount);

            if (AddSkipedTruck && LastPallCount != PalletCount)
            {
                AddSkipedTruck = false;
                NeddedTruck += 1;
            }
            while (PalletCount >= 30)
            {
                NeddedTruck += 1;
                PalletCount -= 30;
            }
            newRecord.setNeededTruck(NeddedTruck);
            Start += TimeLenght;
            ret.add(newRecord);
            NeddedTruck = 0;

            if (LastPallCount == PalletCount)
            {
                Steps++;
                if (Steps >= MoveTrackAfterSteps && !AddSkipedTruck) {
                    if (ret.get(ret.size() - MoveTrackAfterSteps - 1).getPaletCoun() < MinPalletCountToNoRemoveTruck)
                        ret.get(ret.size() - MoveTrackAfterSteps - 1).setNeededTruck((ret.get(ret.size() - MoveTrackAfterSteps - 1).getNeededTruck()) - 1);
                    AddSkipedTruck = true;
                    PalletCount = 0;
                }
            } else Steps = 0;
            LastPallCount = PalletCount;

            Handle++;
            updateProgress(Handle, AllToHandle);
        }
        System.out.println(ret.size());
        return ret;
    }

    @Override
    protected Object call() {
        if (!Ireland && !Plan && !Poland)
        {
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING,"None Selected Skip Live Load");
            updateProgress(0,1);
            updateMessage("Live Load Cancell");
            return null;
        }

        ArrayList<ArrayList<LiveLoadKTW>> DataLiveLoad = ConvertPPdata(ImportExcelFile());

        if(DataLiveLoad.get(DataLiveLoad.size() - 1).size() > 0)
        {
            StringBuilder ExtendetInfo = new StringBuilder();
            for(LiveLoadKTW var : DataLiveLoad.get(DataLiveLoad.size() - 1))
            {
                ExtendetInfo.append("SKU: ").append(var.getSku()).append(" Destination").append(var.getDest()).append("\n");
            }
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING, "Don't recognized some Destination", ExtendetInfo.toString());
        }
        AllToHandle *= 2;
        AllToHandle /= 3;
        if (Ireland) AllToHandle += DataLiveLoad.get(7).size();
        if (Plan)
        {
            for (ArrayList<LiveLoadKTW> liveLoadKTWS : DataLiveLoad) {
                AllToHandle += liveLoadKTWS.size() * 2;
            }
        }
        if (Poland) AllToHandle += DataLiveLoad.get(0).size() * 3;

        if (Ireland) IrelandSplit(DataLiveLoad.get(7));
        if (Plan) {
            ArrayList<InWork.DataStructure.LiveLoadKTW> calculated = CalculatProductionPlan(DataLiveLoad);
            ProductionPlan(calculated);
        }
        if (Poland) {
            ArrayList<LiveLoadPOL> pol = CalculatPoland(DataLiveLoad.get(0));
            Poland(pol);
        }
        updateProgress(1,1);
        updateMessage("Live Load Complet");
        return null;
    }
}
