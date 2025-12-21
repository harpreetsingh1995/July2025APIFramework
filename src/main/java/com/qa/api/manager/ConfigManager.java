package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	//read prop from config prop file using java code...
	
	private static Properties prop=new Properties();
	
	static {
		
		
		//mvn clean install-Denv=qa/dev/stage/uat/prod
		//mvn clean instal -Denv=qa
		//mvn clean install--- by default, testcases should execute on QA ENV
		
	String envName=	System.getProperty("env","prod");
		System.out.println("Running testcases on env: "+envName);
		String fileName="config_"+envName+".properties";//config_stage.properties
		
				InputStream input =ConfigManager.class.getClassLoader().getResourceAsStream(fileName);
		
			if(input!= null) {
				try {
					prop.load(input);
					System.out.println("Config Properties ====> " + prop);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	}
	
	
	public static String get(String key) {
		return prop.getProperty(key).trim();
	}
	
	public static void set(String key, String value) {
		prop.setProperty(key, value);
	}
	
	
	
	

}
