package HeatToDatabase;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DbConnect dbConnect = new DbConnect();
        ArrayList<SingleRow> rows = new ArrayList<>();
        CSVReader csvReader = new CSVReader("test.csv","C:\\Users\\lukmaj\\Desktop\\test\\","",rows,dbConnect);
        csvReader.readCSV();
    }
}
