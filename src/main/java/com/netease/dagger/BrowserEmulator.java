/*
 * Copyright (c) 2012-2013 NetEase, Inc. and other contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.netease.dagger;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.thoughtworks.selenium.Wait;

/**
 * BrowserEmulator is based on Selenium2 and adds some enhancements
 * @author ChenKan
 */
public class BrowserEmulator {

	RemoteWebDriver browserCore;
	WebDriverBackedSelenium browser;
	ChromeDriverService chromeServer;
	JavascriptExecutor javaScriptExecutor;
	
	int stepInterval = Integer.parseInt(GlobalSettings.stepInterval);
	int timeout = Integer.parseInt(GlobalSettings.timeout);
	
	private static Logger logger = Logger.getLogger(BrowserEmulator.class.getName());

	public BrowserEmulator() {
		setupBrowserCoreType(GlobalSettings.browserCoreType);
		browser = new WebDriverBackedSelenium(browserCore, "https://github.com/");
		javaScriptExecutor = (JavascriptExecutor) browserCore;
		logger.info("Started BrowserEmulator");
	}

	/**
	 * 设置浏览器内核：IE/Firefox/Chrome/Safari......
	 * @param type
	 */
	private void setupBrowserCoreType(int type) {
		if (type == 1) {
			//如果火狐浏览器没有默认安装在C盘，需要制定其路径
			System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			browserCore = new FirefoxDriver();
			browserCore.manage().window().maximize();
			logger.info("Using Firefox");
			return;
		}
		if (type == 2) {
			chromeServer = new ChromeDriverService.Builder().usingDriverExecutable(new File(GlobalSettings.chromeDriverPath)).usingAnyFreePort().build();
			try {
				chromeServer.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
			browserCore = new RemoteWebDriver(chromeServer.getUrl(), capabilities);
			browserCore.manage().window().maximize();
			logger.info("Using Chrome");
			return;
		}
		if (type == 3) {
			System.setProperty("webdriver.ie.driver", GlobalSettings.ieDriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			browserCore = new InternetExplorerDriver(capabilities);
			browserCore.manage().window().maximize();
			logger.info("Using IE");
			return;
		}
		if (type == 4) {
			browserCore = new SafariDriver();
			browserCore.manage().window().maximize();
			logger.info("Using Safari");
			return;
		}

		Assert.fail("Incorrect browser type");
	}
	
	/**
	 * 获取 WebDriver 实例
	 * @return
	 */
	public RemoteWebDriver getBrowserCore() {
		return browserCore;
	}

	/**
	 * 获取 WebDriverBackedSelenium 实例
	 * @return
	 */
	public WebDriverBackedSelenium getBrowser() {
		return browser;
	}
	
	/**
	 * 获取 JavascriptExecutor 实例
	 * @return
	 */
	public JavascriptExecutor getJavaScriptExecutor() {
		return javaScriptExecutor;
	}

	/**
	 * Browser Open A Url
	 * @param url
	 */
	public void open(String url) {
		logger.info("***** Open Url："+url);
		pause(stepInterval);
		try {
			browser.open(url);
		} catch (Exception e) {
			e.printStackTrace();
			handleFailure("Failed 2 Open The Url " + url);
		}
		logger.info("***** Open Url SUCCESS！");
	}

	/**
	 * 退出浏览器
	 */
	public void quit() {
		logger.info("***** Quit Browser");
		pause(stepInterval);
		browserCore.quit();
		if (GlobalSettings.browserCoreType == 2) {
			chromeServer.stop();
		}
		logger.info("Quitted BrowserEmulator");
	}

	/**
	 * xpath指定的元素点击事件
	 * @param xpath
	 */
	public void click(String xpath) {
		logger.info("***** Button Click："+xpath);
		pause(stepInterval);
		expectElementExistOrNot(true, xpath, timeout);
		
		try {
			clickTheClickable(xpath, System.currentTimeMillis(), 2500);
		} catch (Exception e) {
			e.printStackTrace();
			handleFailure("Failed to click " + xpath);
		}
		logger.info("Clicked " + xpath);
	}

	/**
	 * Click an element until it's clickable or timeout
	 * 点击一个可点击的元素，直到超时不能点击
	 * @param xpath
	 * @param startTime
	 * @param timeout in millisecond
	 * @throws Exception
	 */
	private void clickTheClickable(String xpath, long startTime, int timeout) throws Exception {
		try {
			browserCore.findElementByXPath(xpath).click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeout) {
				logger.info("Element " + xpath + " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				logger.info("Element " + xpath + " is unclickable, try again");
				clickTheClickable(xpath, startTime, timeout);
			}
		}
	}

	/**
	 * Type text at the page element<br>
	 * Before typing, try to clear existed text
	 * 给xpath指定元素赋值，如果已经存在则先清除
	 * @param xpath
	 *            the element's xpath
	 * @param text
	 *            the input text
	 */
	public void inputText(String xpath, String text) {
		logger.info("***** Input Info Into：【"+xpath+"】");
		pause(stepInterval);
		expectElementExistOrNot(true, xpath, timeout);

		WebElement we = browserCore.findElement(By.xpath(xpath));
		try {
			we.clear();
		} catch (Exception e) {
			logger.warn("Failed to clear text at " + xpath);
		}
		try {
			we.sendKeys(text);
		} catch (Exception e) {
			e.printStackTrace();
			handleFailure("Failed to type " + text + " at " + xpath);
		}

		logger.info("Type " + text + " at " + xpath);
	}

	/**
	 * Hover on the page element
	 * 鼠标悬浮在xpath指定元素上
	 * @param xpath
	 *            the element's xpath
	 */
	public void mouseOver(String xpath) {
		pause(stepInterval);
		expectElementExistOrNot(true, xpath, timeout);
		// First make mouse out of browser
		Robot rb = null;
		try {
			rb = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		rb.mouseMove(0, 0);

		// Then hover
		WebElement we = browserCore.findElement(By.xpath(xpath));

		if (GlobalSettings.browserCoreType == 2) {
			try {
				Actions builder = new Actions(browserCore);
				builder.moveToElement(we).build().perform();
			} catch (Exception e) {
				e.printStackTrace();
				handleFailure("Failed to mouseover " + xpath);
			}

			logger.info("Mouseover " + xpath);
			return;
		}

		// Firefox and IE require multiple cycles, more than twice, to cause a
		// hovering effect
		if (GlobalSettings.browserCoreType == 1
				|| GlobalSettings.browserCoreType == 3) {
			for (int i = 0; i < 5; i++) {
				Actions builder = new Actions(browserCore);
				builder.moveToElement(we).build().perform();
			}
			logger.info("Mouseover " + xpath);
			return;
		}

		// Selenium doesn't support the Safari browser
		if (GlobalSettings.browserCoreType == 4) {
			Assert.fail("Mouseover is not supported for Safari now");
		}
		Assert.fail("Incorrect browser type");
	}

	/**
	 * Switch window/tab
	 * 选定 window窗口或tab窗口
	 * @param windowTitle
	 *            the window/tab's title
	 */
	public void selectWindow(String windowTitle) {
		pause(stepInterval);
		browser.selectWindow(windowTitle);
		logger.info("Switched to window " + windowTitle);
	}

	/**
	 * Enter the iframe
	 * 进入 iframe
	 * @param xpath
	 *            the iframe's xpath
	 */
	public void enterFrame(String xpath) {
		pause(stepInterval);
		browserCore.switchTo().frame(browserCore.findElementByXPath(xpath));
		logger.info("Entered iframe " + xpath);
	}

	/**
	 * Leave the iframe
	 * 离开/退出 iframe
	 */
	public void leaveFrame() {
		logger.info("***** out iframe：");
		pause(stepInterval);
		browserCore.switchTo().defaultContent();
		logger.info("Left the iframe");
	}
	
	/**
	 * Refresh the browser
	 * 刷新浏览器
	 */
	public void refresh() {
		logger.info("***** Refresh Browser");
		pause(stepInterval);
		browserCore.navigate().refresh();
		logger.info("Refreshed");
	}
	
	/**
	 * Mimic system-level keyboard event
	 * 模拟键盘事件
	 * @param keyCode
	 *            such as KeyEvent.VK_TAB, KeyEvent.VK_F11
	 */
	public void pressKeyboard(int keyCode) {
		pause(stepInterval);
		Robot rb = null;
		try {
			rb = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		rb.keyPress(keyCode);	// press key
		rb.delay(100); 			// delay 100ms
		rb.keyRelease(keyCode);	// release key
		logger.info("Pressed key with code " + keyCode);
	}

	/**
	 * Mimic system-level keyboard event with String
	 * 
	 * @param text
	 * 
	 */
	public void inputKeyboard(String text) {
		String cmd = System.getProperty("user.dir") + "\\res\\SeleniumCommand.exe" + " sendKeys " + text;

		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.destroy();
		}
		logger.info("Pressed key with string " + text);
	}
	
	//TODO Mimic system-level mouse event

	/**
	 * 查看文本在页面中是否存在
	 * Expect some text exist or not on the page<br>
	 * Expect text exist, but not found after timeout => Assert fail<br>
	 * Expect text not exist, but found after timeout => Assert fail
	 * @param expectExist
	 *            true or false
	 * @param text
	 *            the expected text
	 * @param timeout
	 *            timeout in millisecond
	 */
	public void expectTextExistOrNot(boolean expectExist, final String text, int timeout) {
		if (expectExist) {
			try {
				new Wait() {
					public boolean until() {
						return isTextPresent(text, -1);
					}
				}.wait("Failed to find text " + text, timeout);
			} catch (Exception e) {
				e.printStackTrace();
				handleFailure("Failed to find text " + text);
			}
			logger.info("Found desired text " + text);
		} else {
			if (isTextPresent(text, timeout)) {
				handleFailure("Found undesired text " + text);
			} else {
				logger.info("Not found undesired text " + text);
			}
		}
	}

	/**
	 * 查看元素在页面中是否存在
	 * Expect an element exist or not on the page<br>
	 * Expect element exist, but not found after timeout => Assert fail<br>
	 * Expect element not exist, but found after timeout => Assert fail<br>
	 * Here <b>exist</b> means <b>visible</b>
	 * @param expectExist
	 *            true or false
	 * @param xpath
	 *            the expected element's xpath
	 * @param timeout
	 *            timeout in millisecond
	 */
	public void expectElementExistOrNot(boolean expectExist, final String xpath, int timeout) {
		if (expectExist) {
			try {
				new Wait() {
					public boolean until() {
						return isElementPresent(xpath, -1);
					}
				}.wait("Failed to find element " + xpath, timeout);
			} catch (Exception e) {
				e.printStackTrace();
				handleFailure("Failed to find element " + xpath);
			}
			logger.info("Found desired element " + xpath);
		} else {
			if (isElementPresent(xpath, timeout)) {
				handleFailure("Found undesired element " + xpath);
			} else {
				logger.info("Not found undesired element " + xpath);
			}
		}
	}

	/**
	 * 指定文本是否在页面上存在
	 * @param text
	 * @param time
	 * @return
	 */
	public boolean isTextPresent(String text, int time) {
		logger.info("***** Check Text："+text+" Is Exist Or Not");
		pause(time);
		boolean isPresent = browser.isTextPresent(text);
		if (isPresent) {
			logger.info("Found text " + text);
			return true;
		} else {
			logger.info("Not found text " + text);
			return false;
		}
	}

	/**
	 * xpath 指定元素是否在页面上存在
	 * @param xpath
	 * @param time
	 * @return
	 */
	public boolean isElementPresent(String xpath, int time) {
		logger.info("***** Check Element ："+xpath+" Is Exist Or Not");
		pause(time);
		boolean isPresent = browser.isElementPresent(xpath) && browserCore.findElementByXPath(xpath).isDisplayed();
		if (isPresent) {
			logger.info("Found element " + xpath);
			return true;
		} else {
			logger.info("Not found element" + xpath);
			return false;
		}
	}
	
	/**
	 * 休眠 / 毫秒
	 * @param time
	 */
	public void pause(int time) {
		logger.info("***** Browser Sleept ："+time+" ss");
		if (time <= 0) {
			return;
		}
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			logger.info("Pause " + time + " ms");
			e.printStackTrace();
		}
	}
	
	/**
	 * 异常时截图
	 * @param notice：xpath
	 */
	private void handleFailure(String notice) {
		String png = LogTools.screenShot(this);
		String log = notice + " >> capture screenshot at " + png;
		logger.error(log);
		if (GlobalSettings.baseStorageUrl.lastIndexOf("/") == GlobalSettings.baseStorageUrl.length()) {
			GlobalSettings.baseStorageUrl = GlobalSettings.baseStorageUrl.substring(0, GlobalSettings.baseStorageUrl.length() - 1);
		}
		
		/**
		 * Reporter:This class is used for test methods to log messages that will be included in the HTML reports generated by TestNG.
		 * 暂时注释掉 
		 */
//		Reporter.log(log + "<br/><img src=\"" + GlobalSettings.baseStorageUrl + "/" + png + "\" />");
//		Assert.fail(log);
	}
	
	/**
	 * 获取 xpath 指定元素的 innerText
	 * @param xpath
	 * @return
	 */
	public String getText(String xpath) {
		WebElement element = this.getBrowserCore().findElement(By.xpath(xpath)); 
		return element.getText();
	}
	
	/**
	 * Extend Method: 获取元素属性
	 * @param xpath
	 * @param attrName
	 * @return
	 */
	public String getAttribute(String xpath,String attrName){
		WebElement element = this.browserCore.findElement(By.xpath(xpath)); 
		return element.getAttribute(attrName);
	}
	
	/**
	 * Extend Method: 获取元素位置,相对页面左上角
	 * @param xpath
	 * @return
	 */
	public Point getElementPosition(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.getLocation();
	}
	
	/**
	 * Extend Method: 选择框：根据 index 选择
	 * @param xpath
	 * @param index
	 */
	public void selectByIndex(String xpath, int index){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	/**
	 * Extend Method: 选择框：根据 Value 选择
	 * @param xpath
	 * @param val
	 */
	public void selectByValue(String xpath, String val){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		Select select = new Select(element);
		select.selectByValue(val);
	}
	
	/**
	 * Extend Method: 选择框：根据 visibleText 选择
	 * @param xpath
	 * @param visibleText
	 */
	public void selectByVisibleText(String xpath, String visibleText){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}
	
	/**
	 * Extend Method: 获取样式属性值  
	 * eg: width = 50px;
	 * @param xpath
	 * @param cssAttr:css 属性名  eg：width、height、marigin-left
	 * @return
	 */
	public String getCssValue(String xpath, String cssAttr){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.getCssValue(cssAttr);
	}
	
	/**
	 * Extend Method: 清除输入框中的内容
	 * @param xpath
	 */
	public void clear(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		element.clear();
	}
	
	/**
	 * Extend Method: 元素是否可见  display:none
	 * @param xpath
	 * @return
	 */
	public boolean isDisplayed(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.isDisplayed();
	}
	
	/**
	 * Extend Method: 元素被禁用
	 * @param xpath
	 * @return
	 */
	public boolean isEnabled(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.isEnabled();
	}
	
	/**
	 * Extend Method: 元素是否被选中
	 * @param xpath : select、radio、checkbox 中的选择
	 * @return
	 */
	public boolean isSelected(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.isSelected();
	}
	
	/**
	 * Extend Method: 获取元素宽度
	 * @param xpath : select、radio、checkbox 中的选择
	 * @return
	 */
	public int getWidth(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.getSize().getWidth();
	}
	
	/**
	 * Extend Method: 获取元素高度
	 * @param xpath : select、radio、checkbox 中的选择
	 * @return
	 */
	public int getHeight(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		return element.getSize().getHeight();
	}
	
	/**
	 * Extend Method: onMouseOver
	 * @param xpath
	 */
	public void onMouseOver(String xpath){
		WebElement element = this.browserCore.findElement(By.xpath(xpath));
		Actions actions = new Actions(this.browserCore);
		actions.moveToElement(element).perform();
	}
	
	/**
	 * Extend Method: Actions:以上 api 不能满足的操作均可有 actions 对象来实现
	 * @return
	 */
	public Actions getActions(){
		return new Actions(this.browserCore);
	}
}
