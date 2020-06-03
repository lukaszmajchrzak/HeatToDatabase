package HeatToDatabase;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ReadImportedSheet {
    private String importFileName;
    private String importFilePath;
    private String backupFolder;
    private Sheet dataSheet;
    private DbConnect dbConnect;
    GenericRows genericRows = new GenericRows();

    private final int headersRowPosition = 0;


    public ReadImportedSheet(String importFileName, String importFilePath,Sheet dataSheet, DbConnect dbConnect) {

        this.importFileName = importFileName;
        this.importFilePath = importFilePath;
        this.dataSheet =dataSheet;
        this.dbConnect = dbConnect;
    }

    public void readData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Row headersRow = dataSheet.getRow(this.headersRowPosition);
        dbConnect.connect();
        dbConnect.getColumnsNames(genericRows);

        ArrayList<String> headers = new ArrayList();
        int i = 0;
        while (headersRow.getCell(i) != null) {
            if (headersRow.getCell(i).getCellType() == 1) {
                headers.add(headersRow.getCell(i).getStringCellValue());
            }
            i++;
        }
        i = 1;
        int j;
        while (dataSheet.getRow(i) != null) {
            j = 0;
            while (dataSheet.getRow(i).getCell(j) != null) {
                if (genericRows.isValueDBName(headersRow.getCell(j).getStringCellValue())) {
                    switch (dataSheet.getRow(i).getCell(j).getCellTypeEnum()) {
                        case STRING:
                            genericRows.addSingleValueNoType(dataSheet.getRow(i).getCell(j).getStringCellValue(),headersRow.getCell(j).getStringCellValue());
                            break;
                        case BOOLEAN:
                            genericRows.addSingleValueNoType(String.valueOf(dataSheet.getRow(i).getCell(j).getBooleanCellValue()),headersRow.getCell(j).getStringCellValue());
                            break;
                        case NUMERIC:
                            if(DateUtil.isCellDateFormatted(dataSheet.getRow(i).getCell(j))) {
                                genericRows.addSingleValueNoType(sdf.format(dataSheet.getRow(i).getCell(j).getDateCellValue()), headersRow.getCell(j).getStringCellValue());
                            } else
                            genericRows.addSingleValueNoType(String.valueOf(dataSheet.getRow(i).getCell(j).getNumericCellValue()),headersRow.getCell(j).getStringCellValue());
                            break;
                    }
                }
                j = j + 1;
            }
            dbConnect.sendSingleRow(genericRows);
            i = i + 1;
        }

        try {
            Files.createDirectories(Paths.get(importFilePath, "backup"));
        }catch(IOException  e)
        {
            System.out.println("folder exist");
        }
        new File(this.importFilePath + this.importFileName).renameTo(new File(this.importFilePath + ((char)92) + ((char)92) +"backup" + ((char) 92) + ((char)92) + this.importFileName));

    }
}

