@echo off
echo ************************************** �������ܲ���ʹ�ù��� **************************************
echo * 
echo * ���������ļ��������򡾽��顿��
echo *	ģ����_�汾��_action ��eg�� memo_3.0_createMemo.js��
echo * 
echo *	(ע:��ȻҲ�����������abc...��������ֻ��Ϊ�˿��ٶ�λ���������ľ��幦��)
echo *
echo *  
echo * JS����������д����
echo *	1��JS�ļ�ÿһ�б�ʾһ��action��
echo *	2����һ��action������һ��action�����н����Ҳ����˵JS������������˳��Ϊʵ��ִ��˳��
echo *	3��������������
echo *		 ��A����ҪXpathΪ����ֵ�ı�����action_code_describe��
echo *		 ��B������Ҫֵ�ı�����action_code_��
echo * 	(˵����action��ϵͳ������code:ͬ���͵�action���ֵĴ�����describe:��������)
echo *		 
echo *   
echo * ϵͳ�����б�
echo * 	��A����ҪXpathΪ����ֵ�Ķ�����
echo *		open����һ�� URL 					��var open_1_������ҳ = xpath;��
echo *		click���Ե�ǰԪ�ؽ��е������		��var click_1_�ύ = xpath;��
echo *		type��������Ҫ�����ı���Ԫ��		��var type_1_�ҵ��Զ����������� = xpath;��
echo *		enterFrame������ iframe 			��var enterFrame_1_iframe = xpath;��
echo *		expectElement������Ƿ���ڸ�Ԫ�� 	��var expectElement_1_����Ǧ�� = xpath;��
echo *
echo * 	��B������Ҫ����ֵ�Ķ�����
echo *		leaveFrame������ iframe				��var leaveFrame_1;��
echo *		refresh�������ˢ��					��var refresh_1;��
echo *		quit��������˳�					��var quit_1;��
echo *		expectText������Ƿ���ָ��ı�		��var expectText_1_�ҵ�̨��;��
echo *		pause�����ߣ����룩					��var pause_1_5000;��
echo * 		scenario������						��var scenario_1_��������;��
echo *
echo *	(ע������ϵͳ���������Ű汾�����������)
echo *    
echo *********************************************************************************************

echo=  
echo ģ���б�
echo 	��
echo 		1��igoal		2��memo		3��email		4��client	
echo 		5��approve		6��plan		7��task			8��disk
echo 		9��notice		10��log		11��doc			12��talk
echo 		13��project		14��bbs		15��knowledge 		16��calendar
echo 		17��workbench
echo 	��
echo=

set /p module_code=��Ҫ���Ե�ģ�飺  
echo=

set /p file_code=��Ҫ���еĲ�����������   
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
echo igoal���Կ�ʼ��
goto end

:memo
echo memo���Կ�ʼ��
goto end

:email
echo email���Կ�ʼ��
goto end

:client
echo client���Կ�ʼ��
goto end

:approve
echo approve���Կ�ʼ��
goto end

:plan
echo plan���Կ�ʼ��
goto end

:task
echo task���Կ�ʼ��
goto end

:disk
echo disk���Կ�ʼ��
goto end

:notice
echo notice���Կ�ʼ��
goto end

:log
echo log���Կ�ʼ��
goto end

:doc
echo doc���Կ�ʼ��
goto end

:talk
echo talk���Կ�ʼ��
goto end

:project
echo project���Կ�ʼ��
goto end

:bbs
echo bbs���Կ�ʼ��
goto end

:knowledge
echo knowledge���Կ�ʼ��
goto end

:calendar
echo calendar���Կ�ʼ��
goto end
 
:workbench
echo workbench���Կ�ʼ��
goto end


:end
java -cp . -server -Xms32m -Xmx8000m  ServerStarter com.jingoal.earthworm.Door %module_code% %file_code%
@pause