package InWork.DataBase;

import InWork.DataBase.DataStructure.DataKTW;
import InWork.DataBase.DataStructure.DataPP;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ExcelAPI
{

    static private File ChoseFile(Component caller)
    {
        String ret = "";
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
    static public boolean ImportKTW(Component caller) {
        XSSFWorkbook book = null;
        File excelfile = ChoseFile(caller);
        if (excelfile == null) {return false;};
        try {
             book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException var10) {
            JOptionPane.showMessageDialog(null,"Błąd otwarcia pliku Excel");
            var10.printStackTrace();
            return false;
        }
        XSSFSheet sheet = book.getSheetAt(1);

        DataKTW newRecord = new DataKTW();
        int source;
        XSSFCell cell;
        Double skudouble;

        DataBaseAPI db = DataBaseAPI.getInstance();
        for(source = 1 ; source < sheet.getLastRowNum();++source) {
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
            db.addKTW(newRecord);
        }
        return true;
    }
    static public boolean ImportPP(Component caller){
        XSSFWorkbook book = null;
        File excelfile = ChoseFile(caller);
        if (excelfile == null) {return false;};
        try {
            book = new XSSFWorkbook(excelfile);
        } catch (IOException | InvalidFormatException var10) {
            JOptionPane.showMessageDialog(null,"Błąd otwarcia pliku Excel");
            var10.printStackTrace();
            return false;
        }
        XSSFSheet sheet = book.getSheetAt(0);
        DataPP newRecord = new DataPP();
        int source;
        XSSFCell cell;
        Double skudouble;

        for(source = 1 ; source < sheet.getLastRowNum();++source) {
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
            newRecord.setQNT(cell.getNumericCellValue());
            cell = sheet.getRow(source).getCell(10);
            newRecord.setLane(cell.getStringCellValue());

        }

        return true;
    }
}
