package HeatToDatabase;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.quartz.utils.DBConnectionManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadImportedSheet extends ExportFileRader {
    private String importFileName;
    private String importFilePath;
    private String backupFolder;
    private DbConnect dbConnect = new DbConnect();
    GenericRows genericRows = new GenericRows();

    private final int headersRowPosition = 0;


    public ReadImportedSheet(String importFileName, String importFilePath) {
        super(importFileName, importFilePath);
        this.importFileName = importFileName;
        this.importFilePath = importFilePath;
    }

    public void setDataSheet(String dataSheet) {
        super.setImportSheetName(dataSheet);
    }

    public void readData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Sheet dataSheet = super.readImportExcel();
        Row headersRow = dataSheet.getRow(this.headersRowPosition);
        dbConnect = new DbConnect();
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
        while (dataSheet.getRow(i).getCell(0) != null) {
            j = 0;
            while (dataSheet.getRow(i).getCell(j) != null) {
                if (genericRows.isValueDBName(headersRow.getCell(j).getStringCellValue())) {

                    switch (dataSheet.getRow(i).getCell(j).getCellTypeEnum()) {
                        case STRING:
                            genericRows.addSingleValueNoType(headersRow.getCell(j).getStringCellValue(), dataSheet.getRow(i).getCell(j).getStringCellValue());
                            break;
                        case NUMERIC:
                            genericRows.addSingleValueNoType(headersRow.getCell(j).getStringCellValue(), String.valueOf(dataSheet.getRow(j).getCell(j).getNumericCellValue()));
                            break;
                    }
                }
                j = j + 1;
            }
            i = i + 1;
        }
        dbConnect.sendSingleRow(genericRows);
        this.importFileName = this.importFileName.substring(0,importFileName.length()-4) + sdf.format(new Date()) + this.importFileName.substring(importFileName.length()-4);
        new File(this.importFilePath + this.importFileName).renameTo(new File(this.importFilePath + this.backupFolder + ((char) 92) + ((char)92 + this.importFileName)));

    }
}

