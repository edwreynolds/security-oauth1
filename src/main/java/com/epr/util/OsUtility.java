package com.epr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OsUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(OsUtility.class);
	
	private static String operatingSystemName = null;
	public static String getOsName() {
		if( operatingSystemName == null )  {
			operatingSystemName= System.getProperty("os.name").toLowerCase();
		}
		return operatingSystemName;
	}
	
	public static boolean isWindowsOs() {
        return (getOsName().indexOf("win") >= 0);
    }
	public static boolean isUnixOs()
    {
        return (getOsName().indexOf("nux") >= 0);
    }
	
	/**
	 * Get the value of a given operating system environment variable using its name. 
	 * <p>
	 * Note in Windows OS some directory path variables are wrapped in quotations and these 
	 * must be removed before using the path string to create a file path.
	 * </p>
	 * @param evarName  Environment variable name (may be case sensitive depending on OS)
	 * @return If found then variable's value as a {@code String} otherwise {@code null} 
	 */
	public static String getOsEnvironmentVariable(String evarName) {
		if( evarName == null )
			throw new IllegalArgumentException("Environment variable name is null");
		String value = null;
		try {
			value = System.getenv(evarName);
	        if (value == null) {
	        	logger.warn("Environment variable not found: {}",evarName);
	        }
		} catch( Exception ex ) {
			logger.error("Get environment variable: "+evarName, ex);
		}
        return value;
	}
	

}
