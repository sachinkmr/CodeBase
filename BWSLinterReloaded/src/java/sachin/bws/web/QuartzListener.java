/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.web;

/**
 * Web application lifecycle listener.
 *
 * @author sku202
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.json.JSONException;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.WeeklyCalendar;
import sachin.bws.site.Data;

public class QuartzListener implements ServletContextListener {

    Scheduler scheduler = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
        try {
            // Setup the Job class and the Job group
            JobDetail job = newJob(ScheduledJob.class).withIdentity("CronQuartzJob", "Group").build();
            WeeklyCalendar cal = new WeeklyCalendar();
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.addCalendar("MyCal", cal, true, true);
//            // Create a Trigger that fires every 5 minutes.
//            // Create a Trigger that fires every 1 minutes.
//                        Trigger trigger = newTrigger()
//                        .withIdentity("TriggerName", "Group")
//                        .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
//                        .build();
            Trigger trigger = newTrigger()
                    .withIdentity("TriggerName", "Group")
                    .modifiedByCalendar("MyCal")
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(Integer.parseInt(Data.getDATA_CONFIG().getString("runningHoursIn24fromat")), Integer.parseInt(Data.getDATA_CONFIG().getString("runningMinIn24fromat"))))
                    .build();

            // Setup the Job and Trigger with Scheduler & schedule jobs
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (JSONException ex) {
            Logger.getLogger(QuartzListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
        System.out.println("Context Destroyed");
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
