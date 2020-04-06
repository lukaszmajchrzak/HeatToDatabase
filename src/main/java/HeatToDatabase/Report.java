package HeatToDatabase;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Report {
    private String reportName;
    private String reportDestination;
    private boolean addDate;
    private boolean moveOld;
    private MyLogger logger;

    public Report(String reportName, String reportDestination,  boolean moveOld, boolean addDate) {
        this.reportName = reportName;
        this.reportDestination = reportDestination;
        this.addDate = addDate;
        this.moveOld = moveOld;
        this.logger = new MyLogger();
    }

    public MyLogger getLogger() {
        return logger;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDestination() {
        return reportDestination;
    }

    public void setReportDestination(String reportDestination) {
        this.reportDestination = reportDestination;
    }

    public boolean isAddDate() {
        return addDate;
    }

    public void setAddDate(boolean addDate) {
        this.addDate = addDate;
    }

    public boolean isMoveOld() {
        return moveOld;
    }

    public void setMoveOld(boolean moveOld) {
        this.moveOld = moveOld;
    }
}