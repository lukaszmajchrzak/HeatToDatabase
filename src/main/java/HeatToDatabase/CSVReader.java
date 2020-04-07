package HeatToDatabase;



import java.io.*;
import java.util.Date;
import java.util.ArrayList;


public class CSVReader {
    private String fileName;
    private String filePath;
    private String destinationPath;
    private ArrayList<SingleRow> readRows;
    GenericRows genericRows = new GenericRows();
    private DbConnect dbConnect;
    private MyLogger logger = new MyLogger();

    public CSVReader(String fileName, String filePath, String destinationPath,ArrayList<SingleRow> readRows,DbConnect dbConnect) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.destinationPath = destinationPath;
        this.readRows = readRows;
        this.dbConnect = dbConnect;
    }

    /**
     * <p> Method reads csv File from specified path:
     * 1. If column name value equals any database column name then adds value to genericRows ArrayList
     * 2. After reading all fields then singleRow is being send to database.
     * 3. After sending row genericRows is cleared for next row</p>
     */
    public void readCSV(){
        File report = new File(filePath + ((char)92) + ((char)92) + fileName);
        String[] headers;
        String csvSplitter =";";
        String line;
        String[] splittedLine;
        dbConnect.getColumnsNames(genericRows);
        try{
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(report)));

            headers = bufferedReader.readLine().split(csvSplitter);

            while((line = bufferedReader.readLine()) != null){
               splittedLine = line.split(csvSplitter);

               for(int i=0;i<headers.length;i++){
//                   System.out.println(headers[i] + " : " + genericRows.getRows().get(i).getValueDBName());
                   if(genericRows.isValueDBName(headers[i]) && i < splittedLine.length){
                       genericRows.addSingleValueNoType(splittedLine[i],headers[i]);
                   }
               }
                dbConnect.sendSingleRow(genericRows);
            }

        genericRows.clearRows();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
