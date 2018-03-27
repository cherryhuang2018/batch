package com.tsmc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tsmc.pojo.User;

@Service("TestService")
public class TestService {

  private static final Logger logger = LoggerFactory.getLogger(TestService.class);

  public void showUserInfo(User user) {

    StringBuilder msg = new StringBuilder(300);
    msg.append("Name:").append(user.getName()).append("\t");
    msg.append("Mail:").append(user.getMail()).append("\t");
    msg.append("Phone:").append(user.getPhone());
    logger.info(new String(msg));
  }
}
