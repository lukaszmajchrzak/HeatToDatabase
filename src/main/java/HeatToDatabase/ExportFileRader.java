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
    private DbConnect dbConnect;
    private String importSheetName = "data";
    private MyLogger myLogger = new MyLogger();


    public ExportFileRader(String importFileName, String importFilePath, DbConnect dbConnect) {
        this.importFileName = importFileName;
        this.importFilePath = importFilePath;
        this.dbConnect = dbConnect;
    }

    private void runReading(String importFilePath, String filename){
        ReadImportedSheet readImportedSheet;
        try {
            File importFile = new File(importFilePath + filename);
            FileInputStream fis = new FileInputStream(importFile);

            Workbook importWorkbook = new XSSFWorkbook(fis);

            Sheet dataSheet = importWorkbook.getSheet(this.importSheetName);
            readImportedSheet = new ReadImportedSheet(filename,importFilePath,dataSheet,dbConnect);
            readImportedSheet.readData();
        } catch(IOException e){
            myLogger.sendLog(e.getMessage());
            e.printStackTrace();
        }
    }
    public void readImportExcel() {
        String readFileName;
        File importPath = new File(this.importFilePath);
        for (String filename : importPath.list()) {

            if (filename.contains(importFileName)) {
                readFileName = filename;
                runReading(importFilePath, readFileName);
            }
        }
    }


    public void setImportSheetName(String importSheetName) {
        this.importSheetName = importSheetName;
    }
}