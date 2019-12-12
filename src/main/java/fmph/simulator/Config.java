package fmph.simulator;

import java.io.File;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public  class Config {
	
	private static String configFile = "data/app.properties";
	
	private Config() {
		
	}
	
	 
	private static  PropertiesConfiguration config; 
	
	public static PropertiesConfiguration getConfig() {
		if(config == null) {
			Configurations configs = new Configurations();
			try {
				String configFile = "data"+File.separator+"app.properties";
				System.out.println("load config file from " + configFile);
				config = configs.properties(new File(configFile));
								
			} catch (ConfigurationException e) {
				System.out.println("Problem with load config " + configFile);
				System.exit(1);
			}
		}
		return config;
	}
	
}
