package HeatToDatabase;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.quartz.utils.DBConnectionManager;

import java.util.ArrayList;

public class ReadImportedSheet extends ExportFileRader {
    private String importFileName;
    private String importFilePath;
    private DbConnect dbConnect = new DbConnect();
    GenericRows genericRows = new GenericRows();

    private final int headersRowPosition = 0;



    public ReadImportedSheet(String importFileName, String importFilePath) {
        super(importFileName,importFilePath);
        this.importFileName = importFileName;
        this.importFilePath = importFilePath;
    }

    public void setDataSheet(String dataSheet) {
        super.setImportSheetName(dataSheet);
    }

    public void readData(){
        Sheet dataSheet = super.readImportExcel();
        Row headersRow = dataSheet.getRow(this.headersRowPosition);
        dbConnect = new DbConnect();
        dbConnect.connect();
        dbConnect.getColumnsNames(genericRows);

        ArrayList<String> headers = new ArrayList();
        int i=0;
        while(headersRow.getCell(i) != null) {
            if (headersRow.getCell(i).getCellType() == 1) {
                headers.add(headersRow.getCell(i).getStringCellValue());
            }
            i++;
        }
        i = 1;
        int j = 0;
        while(dataSheet.getRow(i).getCell(0) != null){
            while(dataSheet.getRow(i).getCell(j) != null){
                
            }




        }


    }


}
