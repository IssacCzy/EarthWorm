var BrowserAction = Java.type("com.issac.earthworm.BrowserAction");
var browser = new BrowserAction("igoal","test");

browser.scenario("创建主线",1);
browser.open("https://web.jingoal.com/#igoal");
browser.enterFrame("//*[@id=\"main_igoal\"]/iframe",1);
browser.click("//*[@id=\"containerDiv\"]/div/div[1]/div[2]/span/span");
browser.type("//*[@id=\"toNewigoalTitle\"]","我的自动化测试主线");
browser.click("//*[@id=\"editIgoalOk\"]");
browser.expectElement("//*[@id=\"containerDiv\"]/div/div[2]/div[2]/div/div/h3/i");

browser.scenario("创建一级工作框",2);
browser.click("//*[@id=\"newFrame\"]/div");
browser.type("//*[@id=\"frameTitle\"]","一级工作框");
browser.expectLocation("//*[@id=\"frameTitle\"]", 200, 350);
browser.expectCssValue("//*[@id=\"editframeOk\"]","background-image","none");
browser.click("//*[@id=\"editframeOk\"]");

browser.scenario("引用备忘",3);
browser.click("//*[@id=\"igoalTreeContainer\"]/ul/li/div/span[2]");
browser.expectCssValue("//*[@id=\"igoalTreeContainer\"]/ul/li[1]/div/div[2]","max-width","460px");
browser.click("//*[@id=\"modulesUl\"]/li[1]/span[1]/i");
browser.enterFrame("//*[@id=\"moduleIframe\"]",2);
browser.click("//*[@id=\"memoTitle3470381\"]");
browser.click("//*[@id=\"referButton\"]");
browser.leaveFrame();
browser.enterFrame("//*[@id=\"main_igoal\"]/iframe",2);
browser.expectElement("//*[@id=\"igoalTreeContainer\"]/ul/li/ul/li/div[1]");

browser.end();