package com.issac.earthworm;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 
 * @author Issac_czy@163.com
 */
public class Door {
	
	static String module = "";		//测试模块名
	static int caseAll = 0;		//是否全部测试  0：全部    1：指定用例
	static String fileName = "";		//测试用例
	
	public static void main(String[] args) {
		
		initParams(args);
		
		String jsCaseFile = "../testCase/" + module + "/" + fileName + ".js";;
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(jsCaseFile), "UTF-8");

			//解析 JavaScript 脚本,对脚本表达式进行求值
			engine.eval(reader);

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化参数
	 * @param args
	 */
	private static void initParams(String[] args){
		if(args!=null && args.length>0){
			module = args[0];
			caseAll =  Integer.valueOf(args[1]);
			fileName = args[2];
		}
	}
}
