package InWork.Controllers;

import InWork.GUI.PopUpWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelController
{
    static public Workbook ImportFile(File file)
    {
        try {
            return WorkbookFactory.create(file);
        } catch (IOException e) {
            PopUpWindow.showMsgWarrning(Alert.AlertType.ERROR, "Opening Excel file ERROR", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
    static public void FileOut(XSSFWorkbook book, String name){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\demo\\" + name + ".xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            PopUpWindow.showMsgWarrning(Alert.AlertType.WARNING, "File " + name + " is open, close it and try again");
            return;
        }
        try {
            book.write(out);
            if (out != null) out.close();
        } catch (IOException e) {
            PopUpWindow.showMsgWarrning(Alert.AlertType.ERROR, "Problem with saving File", e.getMessage());
            e.printStackTrace();
        }
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
