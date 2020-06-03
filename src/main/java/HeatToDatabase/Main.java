package HeatToDatabase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("DBConnector.xml");
        ConnectionReader connectionReader = context.getBean("ConnectionReader", ConnectionReader.class);

        connectionReader.readConnectionSetup();
        DbConnect dbConnect = new DbConnect(connectionReader);

      ReportJob oneTimeJob = new ReportJob(dbConnect);
        oneTimeJob.oneTimeExecute();
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("quartzConfig.xml");
//        QuartzScheduler quartzScheduler = context.getBean("QuartzScheduler",QuartzScheduler.class);
//        quartzScheduler.scheduleJob();



    }
}
