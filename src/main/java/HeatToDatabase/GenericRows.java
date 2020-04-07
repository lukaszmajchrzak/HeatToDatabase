package HeatToDatabase;


import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GenericRows {
    private ArrayList<GenericSingleValue> genericRows = new ArrayList<>();
    MyLogger logger = new MyLogger();
    public GenericRows() {
    }

    /**
     * <p> Method allows to add manually single value to ArrayList (whole row)</p>
     * @param value - Value from row
     * @param valueType - Value-Type for database
     * @param valueDBName - Column name in Database/InputCSV file  [both needs to be equal]
     */
    public void addSignleValue(String value, String valueType, String valueDBName) {
        boolean isFound = false;
        for (int i = 0; i < genericRows.size(); i++) {
            if (genericRows.get(i).getValueDBName().equals(valueDBName)) {
                genericRows.get(i).setValue(value);
                genericRows.get(i).setValueDBName(valueDBName);
                genericRows.get(i).setValueType(valueType);
                isFound = true;
            }
        }
            if(!isFound)
            genericRows.add(new GenericSingleValue(value, valueType, valueDBName));
        }

    /**
     * <p> Methods allows to add single value to ARrayList (whole row). DataType is read from database</p>
     * @param value - Value from row
     * @param valueDBName - Column name in Database/InputCSV file [both needs to be equal]
     */
        public void addSingleValueNoType(String value,String valueDBName) {
            boolean isFound = false;
            for (int i = 0; i < genericRows.size(); i++) {
                if (genericRows.get(i).getValueDBName().equals(valueDBName)) {
                    genericRows.get(i).setValue(value);
//                    System.out.println(valueDBName + " : " + value);
                    isFound = true;
                }
            }
            if (!isFound) {
                genericRows.add(new GenericSingleValue(value, "", valueDBName));
//                System.out.println(valueDBName + " : " + value);
            }
        }
    private String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    private String deAccentv2(String string){
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        return string;
    }
    public void clearRows(){
        genericRows.clear();
    }
    public ArrayList<GenericSingleValue> getRows(){
        return genericRows;
    }

    /**
     * <p> Method prepares data for datbase input:
     *  1. Change date input from Excel to database -> from dd.mm.yyyy to yyyy-mm-dd
     *  2. replaces all apostrofe signs to dots
     *  3. runs deAccent methods for strings
     *  4. Deletes descr.   - will be deleted </p>
     */
    public void replaceAll(){
        for(int i=0;i<getRows().size();i++){
            if(getRows().get(i).getValueType().equals("date") && getRows().get(i).getValue().length() > 10){
                getRows().get(i).setValue(getRows().get(i).getValue().substring(0,10));
                getRows().get(i).setValue(getRows().get(i).getValue().substring(6,10)+ "-" + getRows().get(i).getValue().substring(3,5) + "-" + getRows().get(i).getValue().substring(0,2));

            }
            getRows().get(i).setValue(genericRows.get(i).getValue().replaceAll("'","."));
            getRows().get(i).setValue(deAccent(getRows().get(i).getValue()));
            if(getRows().get(i).getValueDBName().equals("Owner") || getRows().get(i).getValueDBName().equals("Customer")){
                getRows().get(i).setValue(deAccentv2(getRows().get(i).getValue()));
            if(getRows().get(i).getValueDBName().equals("Description")){
                getRows().get(i).setValue("Description in incident - sory");
            }
            }
            logger.sendLog("{HeatToDB} :  @Data prepared for @Incident : " + getRows().get(0).getValue());
        }
        printAll();
    }

    /**
     * <p> Method returns true if typed String value is in a list of column names</p>
     * @param valueDBName - Value which will be compared to list of column names from database
     * @return
     */
    public boolean isValueDBName(String valueDBName){
        for(int i=0;i<genericRows.size();i++){
            if(genericRows.get(i).getValueDBName().equals(valueDBName))
                return true;
        }
        return false;
    }

    /**
     * <p> Method prints all stored values in GenericRows ArrayList</p>
     */
    public void printAll(){
        for(int i=0;i<genericRows.size();i++)
        System.out.println(genericRows.get(i).getValueDBName()+ "[" + genericRows.get(i).getValueType() + "]: " + genericRows.get(i).getValue());
    }
}


