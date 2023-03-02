package InWork.Controllers;

import InWork.DataStructure.*;
import InWork.Settings;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelController
{

    static private File ChoseFile(Component caller){
        JFileChooser file = new JFileChooser(Settings.getInstance().getFileChoserPath());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "xlsx|xls|xlt","xlsx", "xls", "xlt");
        file.setFileFilter(filter);
        int returnValue = file.showOpenDialog(caller);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return file.getSelectedFile();
        }
        return null;
    }
    static public ArrayList<DataKTW> ImportKTW(Component caller) {
        XSSFWorkbook book;
        File excelfile = ChoseFile(caller);
        ArrayList<DataKTW> ret = new ArrayList<>();
        if (excelfile == null) {return ret;}
        try {
             book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(caller,"Błąd otwarcia pliku Excel");
            e.printStackTrace();
            return ret;
        }
        XSSFSheet sheet = book.getSheetAt(1);
        DataKTW newRecord;
        int source;
        XSSFCell cell;
        double skudouble;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
            newRecord = new DataKTW();
            cell = sheet.getRow(source).getCell(0);
            skudouble = cell.getNumericCellValue();
            if ((int) skudouble < 1) {
                break;
            }
            newRecord.setSKU((int) skudouble);

            cell = sheet.getRow(source).getCell(1);
            newRecord.setName(cell.getStringCellValue());
            cell = sheet.getRow(source).getCell(52);
            newRecord.setGross(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(57);
            newRecord.setNet(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(56);
            newRecord.setCs(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(61);
            newRecord.setHeight(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(54);
            newRecord.setDest(cell.getStringCellValue());
            cell = sheet.getRow(source).getCell(58);
            String str = cell.getStringCellValue();
            str = str.replaceAll("\\s+","");
            if (str.equals("1200/1000")) {
                newRecord.setPaltype("IND");
            } else {
                newRecord.setPaltype("EUR");
            }
            int temp;
            if (newRecord.getDest().toLowerCase().contains("fresh")) {
                temp = newRecord.getDest().charAt(0);
                if (temp == 50) {
                    newRecord.setQatime(2);
                } else {
                    if (temp == 52) {
                        newRecord.setQatime(4);
                    } else {
                        if (temp == 51) {
                            newRecord.setQatime(3);
                        } else {
                            newRecord.setQatime(0);
                        }
                    }
                }
            } else {
                newRecord.setQatime(0);
            }
            ret.add(newRecord);
        }
        return ret;
    }
    static public ArrayList<DataPP> ImportPP(Component caller){
        XSSFWorkbook book;
        File excelfile = ChoseFile(caller);
        ArrayList<DataPP> ret = new ArrayList<>();
        if (excelfile == null) {return ret;}
        try {
            book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(caller,"Błąd otwarcia pliku Excel");
            e.printStackTrace();
            return ret ;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        DataPP newRecord;
        int source;
        XSSFCell cell;
        double skudouble;

        for(source = 1 ; source < sheet.getLastRowNum()+1;++source) {
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


            if(temp.contains(","))
            {

                temp = temp.replace(".","");
                temp = temp.replace(",",".");
            }
            String[] strtable = temp.split(".");
            String str = strtable[0];
            int actualValue = str.length();
            if(actualValue<4)
            {
                temp = temp.replace(".","");
            }
            newRecord.setQNT(Double.parseDouble(temp));
            cell = sheet.getRow(source).getCell(10);
            newRecord.setLane(cell.getStringCellValue());
            ret.add(newRecord);
        }

        return ret;
    }
    static public ArrayList<DataPrzewoznik> ImportPrzewoznik(Component caller){
        XSSFWorkbook book;
        File excelfile = ChoseFile(caller);
        ArrayList<DataPrzewoznik> ret = new ArrayList<>();
        if (excelfile == null) {return ret;}
        try {
            book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(caller,"Błąd otwarcia pliku Excel");
            e.printStackTrace();
            return ret ;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        DataPrzewoznik newRecord;
        int source;
        XSSFCell cell;
        double iddouble;

        for(source = 1 ; source < sheet.getLastRowNum()+1;++source) {
            newRecord = new DataPrzewoznik();
            cell = sheet.getRow(source).getCell(0);
            iddouble = cell.getNumericCellValue();
            if (Math.round(iddouble) < 1) {
                break;
            }else{
                System.out.println(Math.round(iddouble));
                newRecord.setPrzewoznikID(Math.round(iddouble));}
            cell = sheet.getRow(source).getCell(1);
            newRecord.setPrzewoznik(cell.getStringCellValue());
            cell = sheet.getRow(source).getCell(2);
            newRecord.setMail(cell.getStringCellValue());
            ret.add(newRecord);
        }
        return ret;
    }
    static public ArrayList<DataOrders>ImportOrders (Component caller){
        XSSFWorkbook book;
        File excelfile = ChoseFile(caller);
        ArrayList<DataOrders> ret = new ArrayList<>();
        if (excelfile == null) {return ret;}
        try {
            book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(caller,"Błąd otwarcia pliku Excel");
            e.printStackTrace();
            return ret ;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        DataOrders newRecord;
        int source;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
            newRecord = new DataOrders();
            ret.add(newRecord);
        }
        return ret;
    }
    static public ArrayList<DataOrdersUpdate>ImportOrdersUpdate(Component caller){
        XSSFWorkbook book;
        File excelfile = ChoseFile(caller);
        ArrayList<DataOrdersUpdate> ret = new ArrayList<>();
        if (excelfile == null) {return ret;}
        try {
            book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(caller,"Błąd otwarcia pliku Excel");
            e.printStackTrace();
            return ret ;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        DataOrdersUpdate newRecord;
        int source;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
            newRecord = new DataOrdersUpdate();
            ret.add(newRecord);
        }
        return ret;

    }
    static public ArrayList<DataOrdersUpdate>Update(Component caller){
        XSSFWorkbook book;
        File excelfile = ChoseFile(caller);
        ArrayList<DataOrdersUpdate> ret = new ArrayList<>();
        if (excelfile == null) {return ret;}
        try {
            book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(caller,"Błąd otwarcia pliku Excel");
            e.printStackTrace();
            return ret ;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        DataOrdersUpdate newRecord;
        int source;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
            newRecord = new DataOrdersUpdate();
            ret.add(newRecord);
        }
        return ret;

    }

    static public void FileOut(XSSFWorkbook book, String name){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\demo\\"+name+".xlsx");
        } catch (FileNotFoundException var9) {
            JOptionPane.showMessageDialog(new Frame(),"Otwarty Plik " +name +" Zamknij i Powtórz operacje");
        }
        try {
            book.write(out);
            if (out != null) out.close();
        } catch (IOException var8) {
            var8.printStackTrace();

        }
    }
    static public void IrelandSplit(ArrayList<LiveLoadKTW> Ireland){
        String name = "Ireland";
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("List");
        int source;
        int rowNum =1;
        XSSFCellStyle data = date(book);

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
        }
        FileOut(book,name);


    }
    static public void Poland(ArrayList<LiveLoadPOL> list){
        String name = "Poland";
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Polska");
        XSSFCellStyle data = date(book);
        XSSFCellStyle time = time(book);
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
            //if (list.get(source).getNeededTruck()>0){

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
            //     }
        }
        FileOut(book,name);
    }
    static public void ProductionPlan(ArrayList<LiveLoadKTW> Plan){
            String name = "Plan";
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet("Plan");
            XSSFCellStyle nieplanowany = style(book,255,153,204,128,0,128,true);
            XSSFCellStyle FRESH= style(book,0,112,192,255,255,0,false);
            XSSFCellStyle order= style(book,223,159,225,0,0,0,false);
            XSSFCellStyle data = date(book);
            XSSFCellStyle time = time(book);

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
            }
        FileOut(book,name);
    }

    static public XSSFCellStyle style(XSSFWorkbook book,int BR,int BG,int BB,int TR,int TG,int TB,boolean bold) {
        XSSFCellStyle NC = book.createCellStyle();
        NC.setFillForegroundColor(new XSSFColor(new Color(BR,BG,BB),new DefaultIndexedColorMap()));
        NC.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont NF = book.createFont();
        NF.setColor(new XSSFColor(new Color(TR,TG,TB), new DefaultIndexedColorMap()));
        NF.setBold(bold);
        NC.setFont(NF);

        return NC;
    }
    static public XSSFCellStyle date(XSSFWorkbook book) {
        CreationHelper createHelper = book.getCreationHelper();
        XSSFCellStyle cellStyle = book.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
        return cellStyle;
    }
    static public XSSFCellStyle time(XSSFWorkbook book) {
        CreationHelper createHelper = book.getCreationHelper();
        XSSFCellStyle cellStyle = book.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm:ss"));
        return cellStyle;
    }

}
