package com.sumi.monitor.core;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * Created by kery on 2015/12/2.
 */
public class QuartzManager {
    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "group1";
    private static String TRIGGER_GROUP_NAME = "trigger1";

    public static void addImmediateJob(String id, String name, String file)
            throws SchedulerException, ParseException {
        JobDetail jobDetail = JobBuilder.newJob(PerlJob.class).build();
        jobDetail.getJobDataMap().put("id", id);
        jobDetail.getJobDataMap().put("name", name);
        jobDetail.getJobDataMap().put("file", file);
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger().startNow().build();

        Scheduler sched = sf.getScheduler();
        sched.scheduleJob(jobDetail,trigger);

        //启动
        if(!sched.isShutdown())
            sched.start();

    }

    /**
     *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @throws SchedulerException
     * @throws ParseException
     */
    public static void addJob(String id, String name, String file, String cron)
            throws SchedulerException, ParseException {
        JobDetail jobDetail = JobBuilder.newJob(PerlJob.class).withIdentity(id,JOB_GROUP_NAME).build();
        jobDetail.getJobDataMap().put("id", id);
        jobDetail.getJobDataMap().put("name", name);
        jobDetail.getJobDataMap().put("file", file);
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(id, TRIGGER_GROUP_NAME).
                withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        Scheduler sched = sf.getScheduler();
        sched.scheduleJob(jobDetail,trigger);

        //启动
        if(!sched.isShutdown())
            sched.start();

    }

    public static void modifyJobTime(String id, String name, String file, String cron) {
        try {
            Scheduler sched = sf.getScheduler();
            Trigger trigger = sched.getTrigger(TriggerKey.triggerKey(id, TRIGGER_GROUP_NAME));

            if (trigger == null) {
                return;
            }

            removeJob(id);
            addJob(id, name, file, cron);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeJob(String id) {
        try {
            Scheduler sched = sf.getScheduler();
            sched.pauseTrigger(TriggerKey.triggerKey(id, TRIGGER_GROUP_NAME)); // 停止触发器
            sched.unscheduleJob(TriggerKey.triggerKey(id, TRIGGER_GROUP_NAME));// 移除触发器
            sched.deleteJob(JobKey.jobKey(id, JOB_GROUP_NAME));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     *
     *
     * @Title: QuartzManager.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:50:18
     * @version V2.0
     */
    public static void startJobs() {
        try {
            Scheduler sched = sf.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     *
     *
     * @Title: QuartzManager.java
     * @Copyright: Copyright (c) 2014
     *
     * @author Comsys-LZP
     * @date 2014-6-26 下午03:50:26
     * @version V2.0
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = sf.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
