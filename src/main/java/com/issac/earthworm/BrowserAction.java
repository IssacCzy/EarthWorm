package com.issac.earthworm;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.issac.earthworm.util.HtmlLogUtil;
import com.netease.dagger.BrowserEmulator;
import com.netease.dagger.GlobalSettings;

/**
 * Browser Action Class
 * @author Issac_czy@163.com
 *
 */
public class BrowserAction {
	Logger logger = Logger.getLogger(BrowserAction.class.getName());
	File htmlLogFile = null;
	BrowserEmulator be = null;
	String resultPath = BrowserAction.class.getResource("/").getPath()+"../caseResultLog";
	String resultLog = "";
	boolean isElementOut = false;
	
	public BrowserAction(String module, String fileName){
		//create log file
		resultLog = HtmlLogUtil.createResultLog(resultPath,module,fileName);
		htmlLogFile = new File(resultPath + "/" + resultLog);
		logger.info("***** The Name Of LogFile："+htmlLogFile);
		
		//init BrowserEmulator class
		be = new BrowserEmulator();
	}
	
	/**
	 * Create Scenario Node
	 * @param scenarioName：场景描述
	 * @param code：场景code
	 */
	public void scenario(String scenarioName, int code) {
		logger.info("***** Create Scenario Node："+scenarioName);
		if(code==1){
			//Just Create Log Head
			HtmlLogUtil.appendScenarioHead2Html(htmlLogFile, scenarioName, code);
		}
		else{
			//Create Log Foot 4 Ahead Scenario , And Start A New Scenario
			HtmlLogUtil.appendScenarioFoot2Html(htmlLogFile);
			HtmlLogUtil.appendScenarioHead2Html(htmlLogFile, scenarioName, code);
		}
		logger.info("***** Create Scenario Node SUCCESS! \n");
	}

	/**
	 * Open URL
	 * @param url
	 */
	public void open(String url) {
		be.open(url);
		HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "打开成功: ["+url+"]");
	}

	/**
	 * Click Element
	 * @param xpath
	 * @return
	 */
	public void click(String xpath) {
		if(elementExist(xpath)){
			be.click(xpath);
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "点击完毕: ["+xpath+"]");
		}
		else
			elementOut();
	}

	/**
	 * Input Info Into Text Element
	 * @param xpath
	 * @param content
	 */
	public void type(String xpath,String content) {
		if(elementExist(xpath)){
			be.inputText(xpath, content);
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "输入框内容输入完毕: ["+xpath+"——"+content+"]");
		}
		else
			elementOut();
	}

	/**
	 * Into Iframe
	 * @param xpath
	 * @param code
	 * @return
	 */
	public void enterFrame(String xpath, int code) {
		if(elementExist(xpath)){
			if(code>1){
				logger.info("***** Iframe Switch :"+xpath);
				WebElement frame = be.getBrowserCore().findElement(By.xpath(xpath));
				be.getBrowserCore().switchTo().frame(frame);
				logger.info("***** Iframe Switch SUCCESS！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "iframe 切换成功: ["+xpath+"]");
			}
			else{
				logger.info("***** Into Iframe："+xpath);
				be.enterFrame(xpath);
				logger.info("***** Into Iframe SUCCESS！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "成功进入 iframe : ["+xpath+"]");
			}
		}
		else
			elementOut();
	}

	/**
	 * Out Current Iframe
	 */
	public void leaveFrame() {
		be.leaveFrame();
		HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "iframe 逃离成功 ");
	}

	/**
	 * Check The ExpectText Is In The Page Or Not
	 * @param textContent
	 */
	public void expectText(String textContent) {
		boolean flag = be.isTextPresent(textContent, Integer.parseInt(GlobalSettings.stepInterval));
		
		if(flag){
			logger.info("***** Text Exsit！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "文本检验已存在！["+textContent+"]");
		}
		else{
			logger.info("***** Text Not Exsit！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "文本检验不存在！["+textContent+"]");
		}
	}

	/**
	 * Check The ExpectElement Is In The Page Or Not
	 * @param xpath
	 */
	public void expectElement(String xpath) {
		if(elementExist(xpath)){
			boolean flag = be.isElementPresent(xpath, Integer.parseInt(GlobalSettings.stepInterval)); 
			
			if(flag){
				logger.info("***** Element Exsit！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "元素检验已存在！["+xpath+"]");
			}
			else{
				logger.info("***** Element Not Exsit！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "元素检验不存在！["+xpath+"]");
			}
		}
		else
			elementOut();
	}
	
	/**
	 * Check Element Position
	 * @param xpath
	 * @param X
	 * @param Y
	 */
	public void expectLocation(String xpath, int X, int Y){
		if(elementExist(xpath)){
			Point point = be.getElementPosition(xpath);
			if(point.getX()==X && point.getY()==Y){
				logger.info("***** Position Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "元素位置检验正确 : ["+xpath+"]");
			}
			else{
				logger.info("***** Position Error！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "元素位置检验错误 : ["+xpath+"] X="+point.getX()+"  Y="+point.getY());
			}
			
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Attribute
	 * @param xpath
	 * @param attrName
	 * @return
	 */
	public void expectAttribute(String xpath,String attrName,String attrValue){
		
		if(elementExist(xpath)){
			String attrVal = be.getAttribute(xpath, attrName);
			if(null!=attrVal && !"".equals(attrVal) && attrVal.equals(attrValue)){
				logger.info("***** Element Attribute Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "元素属性检验正确 : ["+xpath+" —— attribute："+attrName+"]");
			}
			else{
				logger.info("***** Element Attribute Error！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "元素属性检验错误 : ["+xpath+"] —— attribute："+attrName+"]");
			}
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Select Check Option By Index
	 * @param xpath
	 * @param index
	 */
	public void selectByIndex(String xpath, int index){
		if(elementExist(xpath)){
			be.selectByIndex(xpath, index);
			logger.info("***** Select Option By Index SUCCESS！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "select 元素赋值完成 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Select Check Option By Value
	 * @param xpath
	 * @param val
	 */
	public void selectByValue(String xpath, String val){
		if(elementExist(xpath)){
			be.selectByValue(xpath, val);
			logger.info("***** Select Option By Value Success！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "select 元素赋值完成 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Select Check Option By VisibleText
	 * @param xpath
	 * @param visibleText
	 */
	public void selectByVisibleText(String xpath, String visibleText){
		if(elementExist(xpath)){
			be.selectByVisibleText(xpath, visibleText);
			logger.info("***** Select Option By VisibleText Success！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "select 元素赋值完成 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check The Element`s CSS Attribute  
	 * eg: width = 50px;
	 * @param xpath
	 * @param cssAttr:css 属性名  eg：width、height、marigin-left
	 * @param expectValue
	 */
	public void expectCssValue(String xpath, String cssAttr, String expectValue){
		
		if(elementExist(xpath)){
			String val = be.getCssValue(xpath, cssAttr);
			if(null!=val && !"".equals(val) && val.equals(expectValue)){
				logger.info("***** Element`s Css Attribute Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "元素样式属性值检验正确 : ["+xpath+"]——"+cssAttr+"——"+val);
			}
			else
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "元素样式属性值检验错误 : ["+xpath+"]——"+cssAttr+"——"+val);
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Clear Content Of Input Text 
	 * @param xpath
	 */
	public void clearInput(String xpath){
		
		if(elementExist(xpath)){
			be.clear(xpath);
			logger.info("***** Clear Success！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "清除输入框中的内容完成 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Is Visible >> display:none
	 * @param xpath
	 * @param flag 
	 */
	public void expectIsDisplayed(String xpath, boolean flag){
		if(elementExist(xpath)){
			boolean val = be.isDisplayed(xpath);
			if(val == flag ){
				logger.info("***** Element Visible Check Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "检测元素是否可见完成 : ["+xpath+"]");
			}
			else
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "检测元素是否可见错误 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Enable
	 * @param xpath
	 * @param flag
	 */
	public void expectIsEnabled(String xpath, boolean flag){
		if(elementExist(xpath)){
			boolean val = be.isEnabled(xpath);
			if(val == flag ){
				logger.info("***** Element Enable Check Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "检测元素是否被禁用完成 : ["+xpath+"]");
			}
			else
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "检测元素是否被禁用错误 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Is Selected
	 * @param xpath : select、radio、checkbox 中的选择
	 * @return
	 */
	public void expectIsSelected(String xpath, boolean flag){
		if(elementExist(xpath)){
			boolean val = be.isSelected(xpath);
			if(val == flag ){
				logger.info("***** Element Is Selected Check Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "检测元素是否被选中完成 : ["+xpath+"]");
			}
			else
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "检测元素是否被选中错误 : ["+xpath+"]");
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Width
	 * @param xpath
	 * @param expectWidth
	 */
	public void expectWidth(String xpath, int expectWidth){
		
		if(elementExist(xpath)){
			int width = be.getWidth(xpath);
			if(width == expectWidth ){
				logger.info("***** Element Width Check Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "检测元素宽度完成 : ["+xpath+"]");
			}
			else
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "检测元素宽度错误 : ["+xpath+"]  expectWidth:"+expectWidth+"  realVal:"+width);
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Height
	 * @param xpath
	 * @param expectHeight
	 */
	public void expectHeight(String xpath, int expectHeight){
		if(elementExist(xpath)){
			int height = be.getHeight(xpath);
			if(height == expectHeight ){
				logger.info("***** Element Height Check Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "检测元素高度完成 : ["+xpath+"]");
			}
			else
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "检测元素高度错误 : ["+xpath+"]  expectWidth:"+expectHeight+"  realVal:"+height);
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: Check Element Transparent
	 * @param xpath
	 * @param trans
	 */
	public void expectTransparent(String xpath, double trans){
		
		if(elementExist(xpath)){
			DecimalFormat df = new DecimalFormat("#.0"); 
			String val_trans = be.getCssValue(xpath, "opacity");
			String realVal = df.format(val_trans);
			
			BigDecimal dec_trans = new BigDecimal(realVal);
			BigDecimal expect_trans = new BigDecimal(trans);
			
			if(dec_trans.compareTo(expect_trans)!=0)
				HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "检测元素透明度错误 : ["+xpath+"]  expect:"+trans+"  realVal:"+realVal);
			else{
				logger.info("***** Element Transparent Check Right！\n");
				HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "检测元素透明度正确 : ["+xpath+"]");
			}
		}
		else
			elementOut();
	}
	
	/**
	 * Extend Method: onMouseOver
	 * @param xpath
	 */
	public void onMouseOver(String xpath){
		be.onMouseOver(xpath);
	}
	
	/**
	 * Browser Sleep
	 * @param sleepTime
	 */
	public void pause(int sleepTime) {
		int pause_time = sleepTime==0?Integer.parseInt(GlobalSettings.stepInterval):sleepTime;
		be.pause(pause_time);
		HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "浏览器休眠完毕 !");
	}

	/**
	 * Refresh Browser
	 */
	public void refresh() {
		be.refresh();
		HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "浏览器刷新完成 !");
	}

	/**
	 * Quit Browser
	 */
	public void quit() {
		be.quit();
		HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "浏览器成功退出!");
	}
	
	/**
	 * Testing Over
	 */
	public void end(){
		//Add The Foot Of Scenario
		HtmlLogUtil.appendScenarioFoot2Html(htmlLogFile);
		//Add The Foot Of LogFile
		HtmlLogUtil.appendFoot2Html(htmlLogFile);
				
		//Open The LogFile
		HtmlLogUtil.readResultLog(resultPath,resultLog);
	}
	
	/**
	 * Element Can`t Be Find , Testing Over , Quit Browser
	 */
	private void elementOut(){
		quit();
		end();
		
		isElementOut = true;
		if(isElementOut)
			System.exit(0);
	}
	
	/**
	 * Check Element Exist Or Not
	 * @param xpath
	 * @return
	 */
	private boolean elementExist(String xpath){
		if(!be.isElementPresent(xpath, Integer.parseInt(GlobalSettings.stepInterval))){
			logger.info("***** Element："+xpath+" Not Exist！\n");
			HtmlLogUtil.appendLog2Html(htmlLogFile, 0, "元素不存在: ["+xpath+"] ，程序暂停!");
			return false;
		}
		return true;
	}
	
	/**
	 * Extend Method: Actions: PowerFull Action When The Other API Cannot Work
	 * @return
	 */
	private Actions getActions(){
		return be.getActions();
	}
	
	/**
	 * Get the table element`s content
	 * @param xpath
	 */
	public void getTableContent(String xpath){
		HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "获取table 表单: ["+xpath+"] 中的内容");
		
		int rowCount = be.getXpthCount(xpath);
		int columnCount = 0;
		String curXpath = xpath;
		if(rowCount>0){
			curXpath = curXpath + "[1]/td";
			columnCount = be.getXpthCount(curXpath);
			
			HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "查询的表格大小为：["+rowCount+"]行     ["+ columnCount+"]列");
			
			for (int curRow=1;curRow<=rowCount;curRow++) {
				for(int curColumn=1;curColumn<=columnCount;curColumn++){
					curXpath = xpath + "["+curRow+"]/td["+curColumn+"]";
					WebElement cell = be.getBrowserCore().findElement(By.xpath(curXpath));
					String content = cell.getText();
					HtmlLogUtil.appendLog2Html(htmlLogFile, 1, "["+curRow+"]行     ["+ curColumn+"]列："+content);
				}
			}
		}
		
	}
}
