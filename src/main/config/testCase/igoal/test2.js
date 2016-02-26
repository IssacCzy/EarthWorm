var BrowserAction = Java.type("com.issac.earthworm.BrowserAction");
var browser = new BrowserAction("igoal","test2");

browser.scenario("百度搜索",1);
browser.open("http://www.baidu.com");
browser.type("//*[@id=\"kw\"]","自动化测试 Selenium");
browser.click("//*[@id=\"su\"]");

browser.end();