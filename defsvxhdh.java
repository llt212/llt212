
import org.quartz.*; 
import org.quartz.impl.StdSchedulerFactory; 

public class QuartzExample { 
    public static void main(String[] args) throws SchedulerException { 
        JobDetail job = JobBuilder.newJob(HelloJob.class)
            .withIdentity("myJob", "group1")
            .build(); 
        Trigger trigger = TriggerBuilder.newTrigger() 
            .withIdentity("myTrigger", "group1") 
            .startNow() 
            .withSchedule(SimpleScheduleBuilder.simpleSchedule() 
                .withIntervalInSeconds(1) 
                .repeatForever()) 
            .build(); 
Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); 
scheduler.start();
scheduler.scheduleJob(job, trigger); 
    } 
}

public class HelloJob implements Job {
    public void execute(JobExecutionContext context) {
        System.out.println("hello timer");
    } 
}
