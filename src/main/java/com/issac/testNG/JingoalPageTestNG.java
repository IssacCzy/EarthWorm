package com.issac.testNG;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.dagger.BrowserEmulator;

public class JingoalPageTestNG {
	Logger logger = Logger.getLogger(JingoalPageTestNG.class);
	BrowserEmulator be = new BrowserEmulator();
	RemoteWebDriver browserCore = null;

	@BeforeClass
	public void befortest(){
		logger.info("[登录...] befortest!");
	}
	
	@Test
	public void jingoalLogin(){
		String igoalUrl = "web.jingoal.com";
		String accountBox = "//input[@id='email']";
		String pwdBox = "//input[@id='password']";
		String loginBtn = "//a[@class='btn-submit']";
		
		be.open(igoalUrl);
		be.inputText(accountBox, "0000@0101005");
		be.inputText(pwdBox, "12qwaszx");
		be.click(loginBtn);
		
		boolean flag = false;
		
		if(be.isTextPresent("工作台", 5000)){
			flag = true;
			logger.info("[登录...] login SUCCESS!");
		}
		else{
			//请输入正确的帐号
			String error_info = "//*[@id='commonForm']/div/div/div[3]";		
			error_info = be.getText(error_info);
			if(error_info!=null && !"".equals(error_info))
				logger.error("[登录...] account error info: "+error_info);
			
			//请输入正确的密码 or 帐号或密码错误
			error_info = "//*[@id='commonForm']/div/div/div[4]/div[2]";		 
			error_info = be.getText(error_info);
			if(error_info!=null && !"".equals(error_info))
				logger.error("[登录...] account or password error info: "+error_info);
			
			//验证码错误
			error_info = "//*[@id='commonForm']/div/div/div[6]";
			error_info = be.getText(error_info);
			if(error_info!=null && !"".equals(error_info))
				logger.error("[登录...] validate code error info: "+error_info);
		}
		
		be.quit();
		
		Assert.assertTrue(flag);
	} 
	
	@AfterClass
	public void aftertest(){
		logger.info("[登录...] aftertest!");
	}
}
