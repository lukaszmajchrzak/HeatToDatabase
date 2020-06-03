package HeatToDatabase;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class ReportJob implements Job {
    private DbConnect dbConnect;

    public ReportJob(DbConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("appConfig.xml");
        ReportListener reportListener = context.getBean("ReportListener",ReportListener.class);
        CSVReader csvReader = new CSVReader(reportListener.getFileName(),reportListener.getFilePath(),reportListener.getBackupPath());
        System.out.println("job started at: " + new Date());
        if(reportListener.Listen()){
            csvReader.readCSV();
        }
    }

    public void oneTimeExecute(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("appConfig.xml");
        ReportListener reportListener = context.getBean("ReportListener",ReportListener.class);
        ExportFileRader exportFileRader = new ExportFileRader(reportListener.getFileName(),reportListener.getFilePath(),dbConnect);
//        CSVReader csvReader = new CSVReader(reportListener.getFileName(),reportListener.getFilePath(),reportListener.getBackupPath());
        System.out.println("job started at: " + new Date());
            exportFileRader.readImportExcel();

    }
}
