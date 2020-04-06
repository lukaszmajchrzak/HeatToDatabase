package HeatToDatabase;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class DbConnect {
    protected Connection con;
    private MyLogger logger = new MyLogger();

    /**
     * <p> Method connects to database using connection string typed in connectionString.xml file
     * <p>
     * to read the file method runs</p>
     */

    public void connect() {
        try {
//            this.con = DriverManager.getConnection(conReader.getAddress(), conReader.getUsername(), conReader.getPassword());
              this.con =DriverManager.getConnection("jdbc:mysql://10.13.135.10:3306/db", "LukMaj", "LukMaj123$%^");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public HashMap<String,String> readResources(String reportType, HashMap<String,String> resources) {
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PureCloud.Resources");
            while (rs.next()) {
                if (rs.getString(1).equals(reportType)) {
                    if (resources.containsKey(rs.getString(2))) {
                        resources.replace(rs.getString(2), rs.getString(1));
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resources;
    }
    public GenericRows getColumnsNames(GenericRows genericRows){
        this.connect();
        try (Statement stmt = this.con.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT COLUMN_NAME, DATA_TYPE FROM information_schema.COLUMNS WHERE table_name='HEATDATA'");
            int i = 0;
            while(rs.next()){
                genericRows.addSignleValue("",rs.getString(2),rs.getString(1));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return genericRows;
    }

    public void sendSingleRow(GenericRows genericRows){
        this.connect();
        String valuesNamesInsert = "";
        String valuesInsert= "";
        String valuesNamesUpdate = "";
        genericRows.replaceAll();


        valuesNamesInsert = "`" + genericRows.getRows().get(0).getValueDBName() + "`";
        for(int i=1;i<genericRows.getRows().size();i++){
            if(!genericRows.getRows().get(i).getValue().equals(""))
            valuesNamesInsert += ",`" + genericRows.getRows().get(i).getValueDBName() + "`";
        }

        valuesInsert = genericRows.getRows().get(0).getValue();
        for(int i=1;i<genericRows.getRows().size();i++){
            if(!genericRows.getRows().get(0).getValueType().equals("varchar") && !genericRows.getRows().get(0).getValueType().equals("date")){
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesInsert += "," +  genericRows.getRows().get(i).getValue();
            } else {
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesInsert += ",'" + genericRows.getRows().get(i).getValue() + "'";
            }
        }

        if(!genericRows.getRows().get(0).getValueType().equals("varchar") && !genericRows.getRows().get(0).getValueType().equals("date")){
            valuesNamesUpdate += "`" + genericRows.getRows().get(0).getValueDBName() + "` =  " + genericRows.getRows().get(0).getValue();
        } else valuesNamesUpdate += "`" + genericRows.getRows().get(0).getValueDBName() + "` = '" + genericRows.getRows().get(0).getValue() + "'";

        for(int i=1;i<genericRows.getRows().size();i++){
            if(!genericRows.getRows().get(0).getValueType().equals("varchar") && !genericRows.getRows().get(0).getValueType().equals("date")){
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesNamesUpdate += ",`" + genericRows.getRows().get(i).getValueDBName() + "` =  " + genericRows.getRows().get(i).getValue();
            } else{
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesNamesUpdate += ",`" + genericRows.getRows().get(i).getValueDBName() + "`= '" + genericRows.getRows().get(i).getValue() + "'";
            }
        }



        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT `Incident ID` FROM HEAT.HEATDATA WHERE `Incident ID`='" + genericRows.getRows().get(0).getValue() +"'");
            if(!rs.next() == false){
                logger.sendLog("Incident: " + genericRows.getRows().get(0).getValue() + " already exist in DB. updating...");
                System.out.println("UPDATE HEAT.HEATDATA SET " + valuesNamesUpdate + " WHERE `Incident ID`='"+ genericRows.getRows().get(0).getValue() +"'");
                stmt.executeUpdate("UPDATE HEAT.HEATDATA SET " + valuesNamesUpdate + " WHERE `Incident ID`='"+ genericRows.getRows().get(0).getValue() +"'");
                logger.sendLog("Incident " +  genericRows.getRows().get(0).getValue() + " updated!");
            } else{

                System.out.println("INSERT INTO HEAT.HEATDATA ("+valuesNamesInsert +") VALUES(" + valuesInsert + ");");
                stmt.executeUpdate("INSERT INTO HEAT.HEATDATA ("+valuesNamesInsert +") VALUES(" + valuesInsert + ");");
                logger.sendLog("Incident: " + genericRows.getRows().get(0).getValue()+ " added to database");
            }
            con.close();
        } catch(SQLException e){
            e.printStackTrace();
        }

    }
    /**
     * <p> Method closes connection to database</p>
     */
    public void close(){
        try{
            con.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
