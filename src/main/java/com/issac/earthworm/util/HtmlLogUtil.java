package com.issac.earthworm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import com.netease.dagger.BrowserEmulator;

/**
 * util class 4 html log 
 * @author Issac_czy@163.com
 */
public class HtmlLogUtil {

	static Logger logger = Logger.getLogger(HtmlLogUtil.class);
	
	/**
	 * create log file
	 * @param resultPath
	 * @param module
	 * @param fileName
	 * @return
	 */
	public static String createResultLog(String resultPath,String module,String fileName){
		
		File logDir = new File(resultPath +"/"+ module);
		if(!logDir.getParentFile().exists())
			logDir.mkdir();
		
		if(!logDir.exists())
			logDir.mkdir();
		
		String resultLog = module + "/" + fileName + "_result.html";
		File resultFile = new File(resultPath + "/" + resultLog);
		try {
			if(!resultFile.exists())
				resultFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		appendHead2Html(resultFile,module,fileName);
		return resultLog;
	}
	
	/**
	 * The header 4 log file
	 * @param logFile
	 * @param module
	 * @param fileName
	 */
	private static void appendHead2Html(File logFile,String module,String fileName){
		
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta charset=\"utf-8\">");
			sb.append("<title>EARTH WORM Testing Result地龙测试结果：[");
			sb.append(module+"——"+fileName);
			sb.append("]</title>");
			sb.append("<link href=\"../assets/css/bootstrap.css\" rel=\"stylesheet\">");
			sb.append("<link href=\"../assets/css/bootstrap-responsive.css\" rel=\"stylesheet\">");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<div class=\"container\">");
			sb.append("<div class=\"row\">");
			sb.append("<div class=\"span12\">");
			sb.append("<section id=\"collapse\">");
			sb.append("<div class=\"page-header\">");
			sb.append("<h1>EARTH WORM Testing —— [<small>"+module+" >> "+fileName+".js</small>]</h1>");
			sb.append("</div>");
			sb.append("<div class=\"accordion\" id=\"accordion\">");
			
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logFile.getAbsoluteFile()), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			bufferedWriter.write(sb.toString());
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * The Foot 4 log file
	 * @param logFile
	 */
	public static void appendFoot2Html(File logFile){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("</div></section>");
			sb.append("<div class=\"copyright\">&copy; 2020 Issac. EARTH WORM Auto Testing. All Rights Reserved.</div>    ");
			sb.append("</div></div></div>");
			sb.append("<script src=\"../assets/js/jquery.js\"></script>");
			sb.append("<script src=\"../assets/js/bootstrap-transition.js\"></script>");
			sb.append("<script src=\"../assets/js/bootstrap-collapse.js\"></script>");
			sb.append("</body></html>");
			
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logFile.getAbsoluteFile(),true), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			bufferedWriter.write(sb.toString());
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Add Scenario Head Tag
	 * @param logFile
	 * @param ScenarioName：场景名
	 * @param code：场景号
	 */
	public static void appendScenarioHead2Html(File logFile,String ScenarioName,int code){
		
		String href = "Scenario"+String.valueOf(code);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("<div class=\"accordion-group\">");
			sb.append("<div class=\"accordion-heading\">");
			sb.append("<a class=\"accordion-toggle\" data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#"+href+"\">");
			sb.append(ScenarioName);
			sb.append("</a></div>");
			if(code==1)
				sb.append("<div id=\""+href+"\" class=\"accordion-body collapse in\">");
			else
				sb.append("<div id=\""+href+"\" class=\"accordion-body collapse\">");
			sb.append("<div class=\"accordion-inner\">");
			
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logFile.getAbsoluteFile(),true), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			bufferedWriter.write(sb.toString());
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add Scenario Foot Tag
	 * @param logFile
	 */
	public static void appendScenarioFoot2Html(File logFile){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("</div></div></div>");
			
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logFile.getAbsoluteFile(),true), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			bufferedWriter.write(sb.toString());
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add log info 2 log file
	 * @param logFile
	 * @param status：状态  1：成功     0：失败
	 * @param content
	 */
	public static void appendLog2Html(File logFile,int status,String content){
		String title = "";
		String titleCode = "";
		try {
			StringBuffer sb = new StringBuffer();
			
			if(status==1){
				sb.append("<div class=\"alert alert-success alert-dismissable\">");
				title = "成功 >>";
				titleCode = "SUCCESS!";
			}
			else{
				sb.append("<div class=\"alert alert-block alert-error fade in\">");
				title = "失败 >>";
				titleCode = "ERROR!";
			}
			sb.append("<h4>");
			sb.append(title + titleCode);
			sb.append("</h4>");
			sb.append(content);
			sb.append("</div>");
			
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logFile.getAbsoluteFile(),true), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			bufferedWriter.write(sb.toString());
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open log file in new browser window when the testing is over
	 * @param resultPath
	 * @param resultLog
	 */
	public static void readResultLog(String resultPath,String resultLog){
		//"file://"+DoResolve4JsTestCase.class.getResource("/").getPath()+"/caseResult/module/resultLog.html";
		BrowserEmulator be = new BrowserEmulator();
		be.open("file://" + resultPath + "/" +resultLog); 
	}
	
}
