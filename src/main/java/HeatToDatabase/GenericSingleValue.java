package HeatToDatabase;

public class GenericSingleValue {
    private String value;
    private String valueType;
    private String valueDBName;


    public GenericSingleValue(String value, String valueType, String valueDBName) {
        this.value = value;
        this.valueType = valueType;
        this.valueDBName = valueDBName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueDBName() {
        return valueDBName;
    }

    public void setValueDBName(String valueDBName) {
        this.valueDBName = valueDBName;
    }
}
