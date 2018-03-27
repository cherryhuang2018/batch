package com.tsmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tsmc.pojo.User;
import com.tsmc.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/rest/ta")
@Api(value = "ta controller")
public class TestController {

  @Autowired
  @Qualifier("TestService")
  private TestService testService;

  @ApiOperation(value = "查詢User基本資料")
  @RequestMapping(value = "/helloWorld/{name}/{mail}/{phone}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<User> helloWorld(
      @PathVariable @ApiParam(value = "姓名", required = true) String name,
      @PathVariable @ApiParam(value = "信箱", required = true) String mail,
      @PathVariable @ApiParam(value = "電話", required = true) String phone) {

    User user = new User(name, mail, phone);
    testService.showUserInfo(user);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }
}
