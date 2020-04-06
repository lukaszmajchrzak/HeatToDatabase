package HeatToDatabase;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SingleRow {
    private int incidentNumber;
    private String incidentOwner;
    private String customer;
    private String subject;
    private String status;
    private String ownerTeam;
    private String cIName;
    private String ciType;
    private final DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy hh:mm:ss") ;
    private String createdOn;
    private String resolvedOn;
    private int priority;
    private String createdBy;
    private String initialOwnerTeam;
    private String resolvedBy;
    private Boolean isSentToVendor;
    private Boolean isReopened;
    private Boolean isBreachedResponse;
    private Boolean isBreachedResolution;
    private String breachTeamResponse;
    private String breachTeamResolution;
    private int responseTimeSec;
    private int resolutionTimeSec;
    private double totalTimeSpent;
    private String description;

    public SingleRow(int incidentNumber, String incidentOwner, String customer, String subject, String status, String ownerTeam, String cIName, String ciType, String createdOn, String resolvedOn, int priority, String createdBy, String initialOwnerTeam, String resolvedBy, Boolean isSentToVendor, Boolean isReopened, Boolean isBreachedResponse, Boolean isBreachedResolution, String breachTeamResponse, String breachTeamResolution, int responseTimeSec, int resolutionTimeSec, double totalTimeSpent, String description) {
        this.incidentNumber = incidentNumber;
        this.incidentOwner = incidentOwner;
        this.customer = customer;
        this.subject = subject;
        this.status = status;
        this.ownerTeam = ownerTeam;
        this.cIName = cIName;
        this.ciType = ciType;
        this.createdOn = createdOn;
        this.resolvedOn = resolvedOn;
        this.priority = priority;
        this.createdBy = createdBy;
        this.initialOwnerTeam = initialOwnerTeam;
        this.resolvedBy = resolvedBy;
        this.isSentToVendor = isSentToVendor;
        this.isReopened = isReopened;
        this.isBreachedResponse = isBreachedResponse;
        this.isBreachedResolution = isBreachedResolution;
        this.breachTeamResponse = breachTeamResponse;
        this.breachTeamResolution = breachTeamResolution;
        this.responseTimeSec = responseTimeSec;
        this.resolutionTimeSec = resolutionTimeSec;
        this.totalTimeSpent = totalTimeSpent;
        this.description = description;
    }
    private String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public void prepare(){
        setBreachTeamResponse(deAccent(getBreachTeamResponse()));
        setBreachTeamResolution(deAccent(getBreachTeamResolution()));
        setCreatedBy(deAccent(getCreatedBy()));
        setCustomer(deAccent(getCustomer()));
        setDescription(deAccent(getDescription()));
        setIncidentOwner(deAccent(getIncidentOwner()));
        setSubject(deAccent(getSubject()));
    }
    public int getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(int incidentNumber) {
        this.incidentNumber = incidentNumber;
    }

    public String getIncidentOwner() {
        return incidentOwner;
    }

    public void setIncidentOwner(String incidentOwner) {
        this.incidentOwner = incidentOwner;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerTeam() {
        return ownerTeam;
    }

    public void setOwnerTeam(String ownerTeam) {
        this.ownerTeam = ownerTeam;
    }

    public String getcIName() {
        return cIName;
    }

    public void setcIName(String cIName) {
        this.cIName = cIName;
    }

    public String getCiType() {
        return ciType;
    }

    public void setCiType(String ciType) {
        this.ciType = ciType;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getResolvedOn() {
        return resolvedOn;
    }

    public void setResolvedOn(String resolvedOn) {
        this.resolvedOn = resolvedOn;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getInitialOwnerTeam() {
        return initialOwnerTeam;
    }

    public void setInitialOwnerTeam(String initialOwnerTeam) {
        this.initialOwnerTeam = initialOwnerTeam;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public Boolean getSentToVendor() {
        return isSentToVendor;
    }

    public void setSentToVendor(Boolean sentToVendor) {
        isSentToVendor = sentToVendor;
    }

    public Boolean getReopened() {
        return isReopened;
    }

    public void setReopened(Boolean reopened) {
        isReopened = reopened;
    }

    public Boolean getBreachedResponse() {
        return isBreachedResponse;
    }

    public void setBreachedResponse(Boolean breachedResponse) {
        isBreachedResponse = breachedResponse;
    }

    public Boolean getBreachedResolution() {
        return isBreachedResolution;
    }

    public void setBreachedResolution(Boolean breachedResolution) {
        isBreachedResolution = breachedResolution;
    }

    public String getBreachTeamResponse() {
        return breachTeamResponse;
    }

    public void setBreachTeamResponse(String breachTeamResponse) {
        this.breachTeamResponse = breachTeamResponse;
    }

    public String getBreachTeamResolution() {
        return breachTeamResolution;
    }

    public void setBreachTeamResolution(String breachTeamResolution) {
        this.breachTeamResolution = breachTeamResolution;
    }

    public int getResponseTimeSec() {
        return responseTimeSec;
    }

    public void setResponseTimeSec(int responseTimeSec) {
        this.responseTimeSec = responseTimeSec;
    }

    public int getResolutionTimeSec() {
        return resolutionTimeSec;
    }

    public void setResolutionTimeSec(int resolutionTimeSec) {
        this.resolutionTimeSec = resolutionTimeSec;
    }

    public double getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(double totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
