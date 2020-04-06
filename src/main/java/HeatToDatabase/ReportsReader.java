package HeatToDatabase;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReportsReader {
    private String xmlFile;

    public ReportsReader() {
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * Method reads reports.XML file and extracts reports details:
     *  report name
     *  report destination
     *  move old / delete old
     *  add date / no date
     * @return
     */
    public ArrayList<Report> readReports() {
        ArrayList<Report> reports = new ArrayList<>();
        try {

            File reportsXML = new File(this.xmlFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(reportsXML);

            NodeList nReportNames= doc.getElementsByTagName("reportName");
            NodeList nReportDestinations = doc.getElementsByTagName("reportDestinationSubFolder");
            NodeList nReportAddDate = doc.getElementsByTagName("reportAddDate");
            NodeList nReportMoveOld = doc.getElementsByTagName("reportMoveOld");

            ArrayList<String> reportNames = new ArrayList<>();
            ArrayList<String> reportDestinations = new ArrayList<>();
            ArrayList<Boolean> reportAddDate = new ArrayList<>();
            ArrayList<Boolean> reportMoveOld = new ArrayList<>();


            for (int i = 0; i < nReportNames.getLength(); i++) {
                reportNames.add(nReportNames.item(i).getTextContent());
            }
            for (int i = 0; i < nReportDestinations.getLength(); i++) {
                reportDestinations.add(nReportDestinations.item(i).getTextContent());
            }
            for (int i = 0; i < nReportAddDate.getLength(); i++) {
                if(nReportAddDate.item(i).getTextContent().equalsIgnoreCase("true")) {
                    reportAddDate.add(true);
                } else reportAddDate.add(false);
            }
            for (int i = 0; i < nReportMoveOld.getLength(); i++) {
                if(nReportMoveOld.item(i).getTextContent().equalsIgnoreCase("true")) {
                    reportMoveOld.add(true);
                } else reportMoveOld.add(false);
            }
            if (reportNames.size() == reportDestinations.size() && reportMoveOld.size() == reportAddDate.size() && reportAddDate.size() == reportNames.size()) {
                for (int i = 0; i < reportNames.size(); i++) {
                    reports.add(new Report(reportNames.get(i), reportDestinations.get(i), reportMoveOld.get(i),reportAddDate.get(i)));
                }
            }
            for(int i=0;i<reports.size();i++){
                reports.get(i).getLogger().sendLog("Report: " + reports.get(i).getReportName() + "added to listener. Files will be moved to: " + reports.get(i).getReportDestination());
            }
//        String[] urls = doc.getElementsByTagName("reportURL").item(x);
//        String[] repoNames = doc.getElementsByTagName()

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return reports;
    }
}