package com.tsmc.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("BatchJobService")
public class BatchJobService {

  private static final Logger logger = LoggerFactory.getLogger(BatchJobService.class);

  // @Scheduled(cron = "30 * * * * ?")
  public void doBatchJob() {
    Calendar now = Calendar.getInstance();
    StringBuilder time = new StringBuilder();
    time.append(now.get(Calendar.YEAR)).append("-");
    time.append(now.get(Calendar.MONTH)).append("-");
    time.append(now.get(Calendar.DATE)).append("-");
    time.append(now.get(Calendar.HOUR)).append("-");
    time.append(now.get(Calendar.MINUTE));
    logger.info(time.toString());
  }
}
