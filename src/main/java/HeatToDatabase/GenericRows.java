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
    public boolean isValueDBName(String valueDBName){
        for(int i=0;i<genericRows.size();i++){
            if(genericRows.get(i).getValueDBName().equals(valueDBName))
                return true;
        }
        return false;
    }
    public void printAll(){
        for(int i=0;i<genericRows.size();i++)
        System.out.println(genericRows.get(i).getValueDBName()+ "[" + genericRows.get(i).getValueType() + "]: " + genericRows.get(i).getValue());
    }
}


