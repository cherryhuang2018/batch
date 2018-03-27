package com.tsmc.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("mailJobOne")
public class MailJobOne {
  
  private static final Logger logger = LoggerFactory.getLogger(MailJobOne.class);

  public void sendMail() {
    logger.info("mailJobOne send the mail~");
  }
}

