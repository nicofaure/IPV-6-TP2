package org.uqbar.asteroids.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceUtil {

	private static ResourceBundle properties = ResourceBundle.getBundle("config",new Locale("es"));

	public static String getResource(String name){
		return properties.getString(name);
	}
	
	
}
