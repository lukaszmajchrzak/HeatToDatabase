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
//    private String[] orderValues(String[] splittedLine[]){
//        String[] inOrder;
//        inOrder[0] =
//    }
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
                   if(genericRows.isValueDBName(headers[i])){
                       genericRows.addSingleValueNoType(splittedLine[i],headers[i]);
                   }
               }
            }
            dbConnect.sendSingleRow(genericRows);
        genericRows.clearRows();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void readCSVFile(){
        boolean isExisting = false;
        File report = new File(filePath + ((char)92 ) + ((char)92) + fileName);
        String line;
        String spliter = ";";
        String splitedLine[];
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(report)));

            bufferedReader.readLine(); // ignore first column

            while((line = bufferedReader.readLine()) != null) {
                splitedLine = line.split(spliter);
                dbConnect.getColumnsNames(genericRows);
                genericRows.printAll();
                    if (!splitedLine[46].equals("")) {
                        Date r;
                        System.out.println(splitedLine[16]);
                        readRows.add(new SingleRow((Integer.parseInt(splitedLine[0])), splitedLine[8], splitedLine[2], splitedLine[5], splitedLine[6], splitedLine[7], splitedLine[13], splitedLine[14],splitedLine[16].substring(0,9),splitedLine[46].substring(0,9),
                                (Integer.parseInt(splitedLine[18])), splitedLine[21], splitedLine[22], splitedLine[23], Boolean.parseBoolean(splitedLine[29].toLowerCase()), Boolean.parseBoolean(splitedLine[34].toLowerCase()),
                                Boolean.parseBoolean(splitedLine[37].toLowerCase()), Boolean.parseBoolean(splitedLine[35].toLowerCase()), splitedLine[38], splitedLine[36], Integer.parseInt(splitedLine[40]), Integer.parseInt(splitedLine[41]),
                                Double.parseDouble(splitedLine[44]), splitedLine[48]));
                        logger.sendLog("{HeatToDB} /  Incident #" + splitedLine[0] + " read.");
                    } else {
                        readRows.add(new SingleRow((Integer.parseInt(splitedLine[0])), splitedLine[8], splitedLine[2], splitedLine[5], splitedLine[6], splitedLine[7], splitedLine[13], splitedLine[14],splitedLine[16].substring(0,9), splitedLine[46],
                                (Integer.parseInt(splitedLine[18])), splitedLine[21], splitedLine[22], splitedLine[23], Boolean.parseBoolean(splitedLine[29].toLowerCase()), Boolean.parseBoolean(splitedLine[34].toLowerCase()),
                                Boolean.parseBoolean(splitedLine[37].toLowerCase()), Boolean.parseBoolean(splitedLine[35].toLowerCase()), splitedLine[38], splitedLine[36], Integer.parseInt(splitedLine[40]), Integer.parseInt(splitedLine[41]),
                                Double.parseDouble(splitedLine[44]), splitedLine[48]));
                        logger.sendLog("{HeatToDB} /  Incident #" + splitedLine[0] + " read.");
                    }
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        for(int i=0;i<readRows.size();i++){
            logger.sendLog("{HeatToDB} /  Sending Row to dbConnect");
            dbConnect.sendRow(readRows.get(i));
        }



    }
}
