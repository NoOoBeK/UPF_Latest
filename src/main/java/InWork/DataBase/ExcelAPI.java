package InWork.DataBase;

import InWork.DataBase.DataStructure.DataKTW;
import InWork.DataBase.DataStructure.DataPP;
import InWork.DataBase.DataStructure.LiveLoadKTW;
import InWork.DataBase.DataStructure.LiveLoadKTWList;
import InWork.Operations.Calculations;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ExcelAPI
{

    static private File ChoseFile(Component caller)
    {
        String Dir = System.getProperty("user.dir");
        JFileChooser file = new JFileChooser(Dir);
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
        XSSFWorkbook book = null;
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
        Double skudouble;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
            newRecord = new DataKTW();
            cell = sheet.getRow(source).getCell(0);
            skudouble = cell.getNumericCellValue();
            if (skudouble.intValue() < 1) {
                break;
            }
            newRecord.setSKU(skudouble.intValue());

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
            if (cell.getStringCellValue().equals("1200/100")) {
                newRecord.setPaltype("IND");
            } else {
                newRecord.setPaltype("EUR");
            }
            int temp;
            if (newRecord.getDest().contains("FRESH")) {
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
        XSSFWorkbook book = null;
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
        Double skudouble;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
            newRecord = new DataPP();
            cell = sheet.getRow(source).getCell(7);
            skudouble = cell.getNumericCellValue();
            if (skudouble.intValue() < 1) {
                break;}
            newRecord.setSKU(skudouble.intValue());
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
            temp = temp.replace(".","");
            temp = temp.replace(",",".");
            newRecord.setQNT(Double.valueOf(temp));
            cell = sheet.getRow(source).getCell(10);
            newRecord.setLane(cell.getStringCellValue());
            ret.add(newRecord);
        }

        return ret;
    }
    static public void FileOut(XSSFWorkbook book, String name){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\demo\\"+name+".xlsx");
        } catch (FileNotFoundException var9) {
            var9.printStackTrace();
        }
        try {
            book.write(out);
            out.close();
        } catch (IOException var8) {
            var8.printStackTrace();

        }
    }
    static public void IrelandSplit(ArrayList<LiveLoadKTW> Ireland){
        String name = "Ireland";
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("List");
        int source;
        for (source = 0; source<Ireland.size();source++){
            XSSFRow row = sheet.createRow(source);
            XSSFCell cell0 = row.createCell(0);
            XSSFCell cell1 = row.createCell(1);
            XSSFCell cell2 = row.createCell(2);
            XSSFCell cell3 = row.createCell(3);
            sheet.getRow(source).getCell(0).setCellValue(Ireland.get(source).getSku());
            sheet.getRow(source).getCell(1).setCellValue(Ireland.get(source).getName());
            sheet.getRow(source).getCell(2).setCellValue(Ireland.get(source).getPalletCount());
            sheet.getRow(source).getCell(3).setCellValue(Ireland.get(source).getSDate());
        }
        FileOut(book,name);


    }
        static public void ProductionPlan(ArrayList<ArrayList<LiveLoadKTW>> all){
            String name = "Plan";
            XSSFWorkbook book = new XSSFWorkbook();
            XSSFSheet sheet = book.createSheet("Plan");
            ArrayList<LiveLoadKTW> Plan = Calculations.ProductionPlan(all);
            int rowNum =0;
            int source;
            int celnum;
            for (source = 0; source<Plan.size();source++){
                rowNum = rowNum +1;
                if ((source > 0) && (Plan.get(source).getSku()!=Plan.get(source-1).getSku())){rowNum = rowNum +1;}
                XSSFRow row = sheet.createRow(rowNum);
                for (celnum=0;celnum<15;celnum++) {
                    XSSFCell cell0 = row.createCell(celnum);
                }
                sheet.getRow(rowNum).getCell(0).setCellValue(Plan.get(source).getSku());
                sheet.getRow(rowNum).getCell(1).setCellValue(Plan.get(source).getName());
                if (((double)Plan.get(source).getPalletCount()/(double)Plan.get(source).getMaxPallet())>0.9){sheet.getRow(source).getCell(2).setCellValue("Nieplanowane");}else {sheet.getRow(source).getCell(2).setCellValue("");}
                sheet.getRow(rowNum).getCell(3).setCellValue(Plan.get(source).getPalletCount());
                sheet.getRow(rowNum).getCell(4).setCellValue("PAL");
                sheet.getRow(rowNum).getCell(5).setCellValue(Plan.get(source).getMaxPallet());
                sheet.getRow(rowNum).getCell(6).setCellValue("");
                sheet.getRow(rowNum).getCell(7).setCellValue("");
                if (((double)Plan.get(source).getPalletCount()/(double)Plan.get(source).getMaxPallet())<0.9){sheet.getRow(source).getCell(8).setCellValue("po produkcji do FRESH");}else {sheet.getRow(source).getCell(2).setCellValue("");}
                sheet.getRow(rowNum).getCell(9).setCellValue("");
                sheet.getRow(rowNum).getCell(10).setCellValue("");
                sheet.getRow(rowNum).getCell(11).setCellValue(Plan.get(source).getSDate());
                sheet.getRow(rowNum).getCell(12).setCellValue(Plan.get(source).getSTime());
                sheet.getRow(rowNum).getCell(13).setCellValue(Plan.get(source).getProductionTime());
                sheet.getRow(rowNum).getCell(14).setCellValue(Plan.get(source).getDest());
            }
        FileOut(book,name);
    }
}
