var BrowserAction = Java.type("com.issac.earthworm.BrowserAction");
var browser = new BrowserAction("baidu","test");

browser.scenario("百度搜索 Selenium",1);
browser.open("http://www.baidu.com");
browser.type("//*[@id=\"kw\"]","自动化测试 Selenium");
browser.click("//*[@id=\"su\"]");

browser.scenario("百度搜索 Java",2);
browser.open("http://www.baidu.com");
browser.type("//*[@id=\"kw\"]","Java");
browser.click("//*[@id=\"su\"]");
browser.expectElement("//*[@id=\"100\"]/div[5]/a/span");

browser.end();