@echo off
echo ************************************** 地龙功能测试使用规则 **************************************
echo * 
echo * 测试用例文件命名规则【建议】：
echo *	模块名_版本号_action 【eg： memo_3.0_createMemo.js】
echo * 
echo *	(注:当然也可以随便命名abc...命名规则只是为了快速定位测试用例的具体功能)
echo *
echo *  
echo * JS测试用例编写规则：
echo *	1、JS文件每一行表示一个action；
echo *	2、下一个action依赖上一个action的运行结果；也就是说JS中命名变量的顺序即为实际执行顺序；
echo *	3、变量命名规则：
echo *		 【A、需要Xpath为变量值的变量：action_code_describe】
echo *		 【B、不需要值的变量：action_code_】
echo * 	(说明：action：系统动作；code:同类型的action出现的次数；describe:动作描述)
echo *		 
echo *   
echo * 系统动作列表：
echo * 	【A、需要Xpath为变量值的动作】
echo *		open：打开一个 URL 					【var open_1_主线首页 = xpath;】
echo *		click：对当前元素进行点击操作		【var click_1_提交 = xpath;】
echo *		type：修饰需要输入文本的元素		【var type_1_我的自动化测试主线 = xpath;】
echo *		enterFrame：进入 iframe 			【var enterFrame_1_iframe = xpath;】
echo *		expectElement：检测是否存在该元素 	【var expectElement_1_出现铅笔 = xpath;】
echo *
echo * 	【B、不需要变量值的动作】
echo *		leaveFrame：跳出 iframe				【var leaveFrame_1;】
echo *		refresh：浏览器刷新					【var refresh_1;】
echo *		quit：浏览器退出					【var quit_1;】
echo *		expectText：检测是否出现该文本		【var expectText_1_我的台历;】
echo *		pause：休眠（毫秒）					【var pause_1_5000;】
echo * 		scenario：场景						【var scenario_1_创建主线;】
echo *
echo *	(注：更多系统动作会随着版本的升级逐渐添加)
echo *    
echo *********************************************************************************************

echo=  
echo 模块列表：
echo 	【
echo 		1：igoal		2：memo		3：email		4：client	
echo 		5：approve		6：plan		7：task			8：disk
echo 		9：notice		10：log		11：doc			12：talk
echo 		13：project		14：bbs		15：knowledge 		16：calendar
echo 		17：workbench
echo 	】
echo=

set /p module_code=您要测试的模块：  
echo=

set /p file_code=您要运行的测试用例名：   
echo=

if %module_code%==1 goto igoal
if %module_code%==2 goto memo
if %module_code%==3 goto email
if %module_code%==4 goto client
if %module_code%==5 goto approve
if %module_code%==6 goto plan
if %module_code%==7 goto task
if %module_code%==8 goto disk
if %module_code%==9 goto notice
if %module_code%==10 goto log
if %module_code%==11 goto doc
if %module_code%==12 goto talk
if %module_code%==13 goto project
if %module_code%==14 goto bbs
if %module_code%==15 goto knowledge
if %module_code%==16 goto calendar
if %module_code%==17 goto workbench

:igoal
echo igoal测试开始：
goto end

:memo
echo memo测试开始：
goto end

:email
echo email测试开始：
goto end

:client
echo client测试开始：
goto end

:approve
echo approve测试开始：
goto end

:plan
echo plan测试开始：
goto end

:task
echo task测试开始：
goto end

:disk
echo disk测试开始：
goto end

:notice
echo notice测试开始：
goto end

:log
echo log测试开始：
goto end

:doc
echo doc测试开始：
goto end

:talk
echo talk测试开始：
goto end

:project
echo project测试开始：
goto end

:bbs
echo bbs测试开始：
goto end

:knowledge
echo knowledge测试开始：
goto end

:calendar
echo calendar测试开始：
goto end
 
:workbench
echo workbench测试开始：
goto end


:end
java -cp . -server -Xms32m -Xmx8000m  ServerStarter com.jingoal.earthworm.Door %module_code% %file_code%
@pause