package HeatToDatabase;

import com.google.common.reflect.ClassPath;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReportJob implements Job {
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("appConfig.xml");
        ReportListener reportListener = context.getBean("ReportListener",ReportListener.class);
        CSVReader csvReader = new CSVReader(reportListener.getFileName(),reportListener.getFilePath(),reportListener.getBackupPath());

        if(reportListener.Listen()){
            csvReader.readCSV();
        }
    }
}
