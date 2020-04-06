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
    private String[] getColumnNames(String[] query, SingleRow singleRow){
        this.connect();
        query[0] = "";
        try (Statement stmt = this.con.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT COLUMN_NAME FROM information_schema.COLUMNS WHERE table_name='HEATDATA'");
            int i = 0;
//            System.out.println(rs.getString(1));
            while(rs.next()){

//                System.out.println(rs.getString(1));
                if(singleRow.getResolvedOn().equals("")) {
                    if (!rs.getString(1).equals("ResolvedOn")) {
                        if(i != 0)
                            query[0] += ",";
                        query[0] += rs.getString(1);
                        System.out.println("here : "  + i);
                    }
                } else {
                    if(i != 0)
                        query[0] += ",";
                    System.out.println("here : "  + i);
                    query[0] += rs.getString(1);
                }
                i++;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println(query[0]);
        return query;
    }

    private String[] buildSingleRowQuery(SingleRow singleRow){





        String dataTypes[] = { "varchar(255)","int", "double", "Date" , "Boolean" };
         String query,c;
         query="";
         c =",";
         String d = "'";
         singleRow.prepare();
        Field[] fields = SingleRow.class.getFields();
        System.out.println((fields.toString()));

        query = query + "'" + singleRow.getIncidentNumber() + "'" + c;

         query= query + "'" +singleRow.getIncidentOwner() + "'" + c;

        query= query +  "'" +singleRow.getCustomer() + "'" + c;

        query= query +  "'" +singleRow.getSubject() + "'" + c;

        query= query + "'" +singleRow.getStatus()+ "'" + c;

        query= query + "'" +singleRow.getOwnerTeam()+ "'" + c;

        query= query + "'" +singleRow.getcIName() + "'" + c;

        query= query +  "'" +singleRow.getCiType() + "'" + c;

        query = query + "'" +singleRow.getCreatedOn() + "'" + c;
        if(!singleRow.getResolvedOn().equals(""))
        query= query +  "'" +singleRow.getResolvedOn() + "'" + c;

        query= query +  "'" +singleRow.getPriority() + "'" + c;

        query= query +  "'" +singleRow.getCreatedBy() + "'" + c;

        query= query +  "'" +singleRow.getInitialOwnerTeam() + "'" + c;

        query= query +  "'" +singleRow.getResolvedBy() + "'" + c;

        query= query + singleRow.getSentToVendor() + c;

        query= query + singleRow.getReopened()+ c;

        query= query +  singleRow.getBreachedResponse() + c;

        query= query +  singleRow.getBreachedResolution() + c;

        query= query + singleRow.getBreachTeamResponse() + c;

        query= query +  singleRow.getBreachTeamResolution() +  c;

        query= query +  "'" +singleRow.getResponseTimeSec() + "'" + c;

        query= query +  "'" +singleRow.getResolutionTimeSec() + "'" + c;

        query= query +  "'" +singleRow.getTotalTimeSpent() + "'" + c;

        query= query +  "'" + singleRow.getDescription() + "'";
        System.out.println(query);
        String bQuery[] = { "",query};
        return bQuery;
    }
    private String updateQuery(String[] query , SingleRow singleRow){
        String rquery = "";
        String splittedQuery[];
        String splittedTables[];
        query = getColumnNames(query,singleRow) ;
        splittedQuery= query[1].split(",");
        splittedTables = query[0].split(",");
        rquery = splittedTables[0] + " = " + splittedQuery[0];
        for(int i=1;i<splittedQuery.length;i++){
            rquery =  rquery + "," + splittedTables[i] + " = " + splittedQuery[i];
        }
        return rquery;
    }
    public void sendSingleRow(GenericRows genericRows){
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
            if(!genericRows.getRows().get(i).getValueType().equals("varchar")){
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesInsert += "," +  genericRows.getRows().get(i).getValue();
            } else valuesInsert += ",'" + genericRows.getRows().get(i).getValue() + "'";
        }

        if(!genericRows.getRows().get(0).getValueType().equals("varchar")){
            valuesNamesUpdate += "`" + genericRows.getRows().get(0).getValueDBName() + "` =  " + genericRows.getRows().get(0).getValue();
        } else valuesNamesUpdate += "`" + genericRows.getRows().get(0).getValueDBName() + "` = '" + genericRows.getRows().get(0).getValue() + "'";

        for(int i=1;i<genericRows.getRows().size();i++){
            if(!genericRows.getRows().get(i).getValueType().equals("varchar")){
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesNamesUpdate += ",`" + genericRows.getRows().get(i).getValueDBName() + "` =  " + genericRows.getRows().get(i).getValue();
            } else{
                if(!genericRows.getRows().get(i).getValue().equals(""))
                valuesNamesUpdate += ",`" + genericRows.getRows().get(i).getValueDBName() + "`= '" + genericRows.getRows().get(i).getValue() + "'";
            }
        }



        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 'Incident ID' FROM HEAT.HEATDATA WHERE 'Incident ID'='" + genericRows.getRows().get(0).getValue() +"'");
            if(!rs.next() == false){
                logger.sendLog("Incident: " + genericRows.getRows().get(0).getValue() + " already exist in DB. updating...");
                System.out.println("UPDATE HEAT.HEATDATA SET " + valuesNamesUpdate + " WHERE IncindentNumber='"+ genericRows.getRows().get(0).getValue() +"'");
                stmt.executeUpdate("UPDATE HEAT.HEATDATA SET " + valuesNamesUpdate + " WHERE IncindentNumber='"+ genericRows.getRows().get(0).getValue() +"'");
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
    public void sendRow(SingleRow singleRow){
        this.connect();
        try{
            Statement stmt = this.con.createStatement();
            String query[] = buildSingleRowQuery(singleRow);
            ResultSet rs = stmt.executeQuery("SELECT IncidentNumber FROM HEAT.HEATDATA WHERE IncidentNumber='" + singleRow.getIncidentNumber() +"'");
            if(!rs.next() == false){
                logger.sendLog("Incident: " + singleRow.getIncidentNumber() + " already exist in DB. updating...");
                String rQuery;
                rQuery = updateQuery(query,singleRow);
                System.out.println("UPDATE HEAT.HEATDATA SET " + rQuery + " WHERE IncindentNumber='"+singleRow.getIncidentNumber()+"'");
                stmt.executeUpdate("UPDATE HEAT.HEATDATA SET " + rQuery + " WHERE IncindentNumber='"+singleRow.getIncidentNumber()+"'");
                logger.sendLog("Incident " + singleRow.getIncidentNumber() + " updated!");
            } else{
                query = getColumnNames(query,singleRow);
                System.out.println("INSERT INTO HEAT.HEATDATA ("+query[0] +") VALUES(" + query[1] + ");");
                stmt.executeUpdate("INSERT INTO HEAT.HEATDATA ("+query[0] +") VALUES(" + query[1] + ");");
                logger.sendLog("Incident: " + singleRow.getIncidentNumber() + " added to database");
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
