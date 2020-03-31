package com.elliemae.alrg.config;

import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.ALRGLogger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class EnvironmentConfigDataHelper {

	public static Logger _log = Logger.getLogger(EnvironmentConfigDataHelper.class);
	
	private static HashMap<String, HashMap<String, String>> userList = ALRGEnvironmentDataApplication.getUserListData();
	
	public EnvironmentConfigDataHelper() {
	
	}
	
	public static String getUserName(String testDataUserName) {
		
	    testDataUserName = testDataUserName.replace("$", "");
	    if( userList.keySet().contains(testDataUserName) ) {
	        testDataUserName = userList.get(testDataUserName).get("UserName");
	    }
/*		for (Map.Entry<String, HashMap<String, String>> entry : userList.entrySet()) {
			if (!entry.getKey().equals("") && testDataUserName.contains(entry.getKey())) {
				testDataUserName = testDataUserName.replace("$" + entry.getKey(), entry.getValue().get("UserName"));
				break;
			}
		}*/
		ALRGLogger.log(_log, "User name from EnvironmentConfig: " + testDataUserName, EllieMaeLogLevel.reporter);
		return testDataUserName;
	}

	/**
	 * 
	 * @param userIdKey
	 * @return
	 */
	public static String getPassword(String userIdKey) {
	
		return getPassword("$Password", userIdKey);		
	}
	
	/**
	 * 
	 * @param testDataPassword
	 * @param userIdKey
	 * @return
	 */
	public static String getPassword(String testDataPassword, String userIdKey) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
/*		for (Map.Entry<String, HashMap<String, String>> entry : userList.entrySet()) {
			if ( !StringUtils.isEmpty(entry.getKey()) && userIdKey.contains(entry.getKey())) {
				testDataPassword = testDataPassword.replace("$Password", entry.getValue().get("Password"));
				break;
			}
		}*/
		userIdKey = userIdKey.replace("$", "");
        if( userList.keySet().contains(userIdKey) ) {
            testDataPassword = userList.get(userIdKey).get("Password");
        }		
		ALRGLogger.log(_log, "Password from EnvironmentConfig: " + testDataPassword, EllieMaeLogLevel.reporter);
		return testDataPassword;
	}
	
	/**
	 * 
	 * @param provider
	 * @return
	 */
	public static String getProviderPassword(String provider) {
        
	    String providerPwd = "";
        _log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
        for (Map.Entry<String, HashMap<String, String>> entry : userList.entrySet()) {
            if (!entry.getKey().equals("") && provider.contains(entry.getKey())) {
                providerPwd = entry.getValue().get("Password");
                break;
            }
        }
        ALRGLogger.log(_log, "Password for provider (" + provider + ") - " + providerPwd, EllieMaeLogLevel.reporter);
        return providerPwd;
    }	
	
	
	/**
	 * 
	 * @param provider
	 * @return
	 */
    public static String getProviderUserID(String provider) {
        
        String providerID = "";
        _log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
        for (Map.Entry<String, HashMap<String, String>> entry : userList.entrySet()) {
            if (!entry.getKey().equals("") && provider.contains(entry.getKey())) {
                providerID =entry.getValue().get("UserName");
                break;
            }
        }
        ALRGLogger.log(_log, "ID for provider (" + provider + ") - " + providerID, EllieMaeLogLevel.reporter);
        return providerID;
    }
    
}
