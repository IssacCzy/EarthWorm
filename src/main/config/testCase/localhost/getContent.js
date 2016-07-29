var BrowserAction = Java.type("com.issac.earthworm.BrowserAction");
var browser = new BrowserAction("baidu","test");

browser.scenario("抓取页面table表单数据",1);
browser.open("http://localhost:8080/tableInfo.html");
browser.getTableContent("/html/body/table/tbody/tr");

browser.end();