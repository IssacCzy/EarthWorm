@echo off
echo ************************************** �������ܲ���ʹ�ù��� **************************************
echo * 
echo * ���������ļ��������򡾽��顿��
echo *		��Ŀ��_action ��eg�� baidu_select.js��
echo *		(ע:��ȻҲ�����������abc...��������ֻ��Ϊ�˿��ٶ�λ���������ľ��幦��)
echo *
echo *  
echo * JS����������д����
echo *  1����BrowserAction������js�ļ���
echo *  2������BrowserAction�������
echo *  3��ͨ���ڶ��������ı���������BrowserAction��ĺ�����
echo *	ע�⣺
echo *     	1��JS�ļ�ÿһ�б�ʾһ��action��
echo *		2����һ��action������һ��action�ĳɹ����У�
echo *		 
echo *   
echo * ϵͳ�����б�
echo * 	��A����ҪXpathΪ����ֵ�Ķ�����
echo *		open����һ�� URL 					
echo *		click���Ե�ǰԪ�ؽ��е������		
echo *		type��������Ҫ�����ı���Ԫ��		
echo *		enterFrame������ iframe 			
echo *		expectElement������Ƿ���ڸ�Ԫ�� 	
echo *
echo * 	��B������Ҫ����ֵ�Ķ�����
echo *		leaveFrame������ iframe				
echo *		refresh�������ˢ��					
echo *		quit��������˳�					
echo *		expectText������Ƿ���ָ��ı�		
echo *		pause�����ߣ����룩					
echo * 		scenario������						
echo *
echo *	(ע������ϵͳ���������Ű汾�����������)
echo *    
echo *********************************************************************************************

echo=

set /p module_code=��Ҫ���Ե���Ŀ����  
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