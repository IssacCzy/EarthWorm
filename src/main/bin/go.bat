@echo off
echo ************************************** 地龙功能测试使用规则 **************************************
echo * 
echo * 测试用例文件命名规则【建议】：
echo *		项目名_action 【eg： baidu_select.js】
echo *		(注:当然也可以随便命名abc...命名规则只是为了快速定位测试用例的具体功能)
echo *
echo *  
echo * JS测试用例编写规则：
echo *  1、将BrowserAction类引入js文件；
echo *  2、声明BrowserAction类变量；
echo *  3、通过第二步声明的变量来调用BrowserAction类的函数；
echo *	注意：
echo *     	1、JS文件每一行表示一个action；
echo *		2、下一个action依赖上一个action的成功运行；
echo *		 
echo *   
echo * 系统动作列表：
echo * 	【A、需要Xpath为变量值的动作】
echo *		open：打开一个 URL 					
echo *		click：对当前元素进行点击操作		
echo *		type：修饰需要输入文本的元素		
echo *		enterFrame：进入 iframe 			
echo *		expectElement：检测是否存在该元素 	
echo *
echo * 	【B、不需要变量值的动作】
echo *		leaveFrame：跳出 iframe				
echo *		refresh：浏览器刷新					
echo *		quit：浏览器退出					
echo *		expectText：检测是否出现该文本		
echo *		pause：休眠（毫秒）					
echo * 		scenario：场景						
echo *
echo *	(注：更多系统动作会随着版本的升级逐渐添加)
echo *    
echo *********************************************************************************************

echo=

set /p module_code=您要测试的项目名：  
echo=

set /p case_code=是否指定用例[0：运行所有；	1：指定]：
echo=

if %case_code%==0 goto runCase
if %case_code%==1 goto markCase

:markCase
set /p file_code=您要运行的测试用例名：   
goto runCase

:runCase
java -cp . -server -Xms32m -Xmx8000m  ServerStarter com.issac.earthworm.Door %module_code% %case_code% %file_code%
@pause