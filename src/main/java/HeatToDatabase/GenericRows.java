package HeatToDatabase;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GenericRows {
    private ArrayList<GenericSingleValue> genericRows = new ArrayList<>();

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
                    System.out.println(valueDBName + " : " + value);
                    isFound = true;
                }
            }
            if (!isFound) {
                genericRows.add(new GenericSingleValue(value, "", valueDBName));
                System.out.println(valueDBName + " : " + value);
            }
        }
    private String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    public void clearRows(){
        genericRows.clear();
    }
    public ArrayList<GenericSingleValue> getRows(){
        return genericRows;
    }
    public void replaceAll(){
        for(int i=0;i<getRows().size();i++){
            getRows().get(i).setValue(genericRows.get(i).getValue().replaceAll("'","."));
            getRows().get(i).setValue(deAccent(getRows().get(i).getValue()));
        }
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


