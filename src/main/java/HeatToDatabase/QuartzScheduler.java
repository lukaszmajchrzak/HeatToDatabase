package HeatToDatabase;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {
    private String quartzValue;

    public QuartzScheduler() {
    }

    public void setQuartzValue(String quartzValue) {
        this.quartzValue = quartzValue;
    }

    public void scheduleJob() {
        try {
            JobDetail job = JobBuilder.newJob(ReportJob.class)
                    .withIdentity("ReportJob", "group1").build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("ReportJob", "group1")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(this.quartzValue))
                    .build();

            //schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}

