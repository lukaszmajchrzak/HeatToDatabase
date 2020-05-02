package HeatToDatabase;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExportFileRader {
    private final String importFileName;
    private final String importFilePath;
    private String importSheetName = "data";
    private MyLogger myLogger = new MyLogger();


    public ExportFileRader(String importFileName, String importFilePath) {
        this.importFileName = importFileName;
        this.importFilePath = importFilePath;
    }


    public Sheet readImportExcel() {
        try {
            File importFile = new File(importFilePath + importFileName);
            FileInputStream fis = new FileInputStream(importFile);

            Workbook importWorkbook = new XSSFWorkbook(fis);

            Sheet dataSheet = importWorkbook.getSheet(this.importSheetName);
            return dataSheet;
        } catch(IOException e){
            myLogger.sendLog(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void setImportSheetName(String importSheetName) {
        this.importSheetName = importSheetName;
    }
}