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
rem echo ģ���б�
rem echo 	��
rem echo 		1��igoal		2��memo		3��email		4��client	
rem echo 		5��approve		6��plan		7��task			8��disk
rem echo 		9��notice		10��log		11��doc			12��talk
rem echo 		13��project		14��bbs		15��knowledge 		16��calendar
rem echo 		17��workbench
rem echo 	��
echo=

set /p module_code=��Ҫ���Ե�ģ�飺  
echo=

set /p case_code=�Ƿ�ָ������[0���������У�	1��ָ��]��
echo=

if %case_code%==0 goto runCase
if %case_code%==1 goto markCase

:markCase
set /p file_code=��Ҫ���еĲ�����������   
goto runCase

:runCase
java -cp . -server -Xms32m -Xmx8000m  ServerStarter com.issac.earthworm.Door %module_code% %case_code% %file_code%
@pause