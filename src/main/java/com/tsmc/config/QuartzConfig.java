package com.tsmc.config;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.tsmc.batch.MailJobOne;
import com.tsmc.batch.MailJobTwo;

@Configuration
public class QuartzConfig {

  @Bean
  public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
    // 建立無狀態的JobDetail
    MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
    obj.setTargetBeanName("mailJobOne");
    obj.setTargetMethod("sendMail");
    return obj;
  }

  @Bean
  public SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
    SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
    stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject()); // set JobDetail
    stFactory.setStartDelay(1000); // 開始遞延時間(單位:毫秒)
    stFactory.setRepeatInterval(60 * 1000); // 重複時間(單位:毫秒)
    stFactory.setPriority(10); // 優先順序，預設是5
    stFactory.setMisfireInstruction(Trigger.MISFIRE_INSTRUCTION_SMART_POLICY); // 預設
    return stFactory;
  }

  @Bean
  public JobDetailFactoryBean jobDetailFactoryBean() {
    JobDetailFactoryBean factory = new JobDetailFactoryBean();
    factory.setJobClass(MailJobTwo.class);
    Map<String, Object> jobDataMap = new HashMap<String, Object>();
    jobDataMap.put("count", 1); // 計數器
    factory.setJobDataAsMap(jobDataMap);
    factory.setGroup("sendMailGroup");
    factory.setName("mailJobTwo");
    return factory;
  }

  @Bean
  public CronTriggerFactoryBean cronTriggerFactoryBean() {
    CronTriggerFactoryBean ctFactory = new CronTriggerFactoryBean();
    ctFactory.setJobDetail(jobDetailFactoryBean().getObject());
    ctFactory.setStartDelay(3000);
    ctFactory.setName("mailTrigger");
    ctFactory.setGroup("sendMailGroup");
    ctFactory.setCronExpression("0 0/1 * 1/1 * ? *"); // Cron
    ctFactory.setPriority(10);
    ctFactory.setMisfireInstruction(Trigger.MISFIRE_INSTRUCTION_SMART_POLICY);
    return ctFactory;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {
    SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
    scheduler.setJobFactory(autoWiringSpringBeanJobFactory());
    // scheduler.setTriggers(simpleTriggerFactoryBean().getObject(),
    // cronTriggerFactoryBean().getObject());
    scheduler.setTriggers(cronTriggerFactoryBean().getObject());
    return scheduler;
  }

  @Bean
  public AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory() {
    return new AutoWiringSpringBeanJobFactory();
  }

}
