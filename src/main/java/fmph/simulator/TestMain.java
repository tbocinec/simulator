package fmph.simulator;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.beanutils.PropertyUtils;

public class TestMain {

	public static void main(String[] args) {
	PropertiesConfiguration abc = Config.getConfig();
	System.out.println(abc.getString("database.timeout"));

		System.out.println(Math.cos(45));
		
	}
}
