package HeatToDatabase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
      ReportJob oneTimeJob = new ReportJob();
        oneTimeJob.oneTimeExecute();
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("quartzConfig.xml");
//        QuartzScheduler quartzScheduler = context.getBean("QuartzScheduler",QuartzScheduler.class);
//        quartzScheduler.scheduleJob();



    }
}
