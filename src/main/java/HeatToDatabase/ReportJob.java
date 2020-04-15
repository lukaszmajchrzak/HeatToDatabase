package HeatToDatabase;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class ReportJob implements Job {
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
        CSVReader csvReader = new CSVReader(reportListener.getFileName(),reportListener.getFilePath(),reportListener.getBackupPath());
        System.out.println("job started at: " + new Date());
        if(reportListener.Listen()){
            csvReader.readCSV();
        }
    }
}
