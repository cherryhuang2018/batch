package com.tsmc.batch;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MailJobTwo extends QuartzJobBean {

  private static final Logger logger = LoggerFactory.getLogger(MailJobTwo.class);
  private static final String COUNT = "count";

  @Override
  protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
    JobDataMap jobDataMap = ctx.getJobDetail().getJobDataMap();
    int count = jobDataMap.getInt(COUNT);
    
    JobKey jobKey = ctx.getJobDetail().getKey(); // Instance key
    logger.info(jobKey + ": " + count);
    logger.info("mailJobTwo send the mail~");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    jobDataMap.put(COUNT, ++count);
  }
}
