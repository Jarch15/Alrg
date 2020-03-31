package com.elliemae.alrg.consts;

/**
 * <b>Name:</b> ALRGConsts</br>
 * <b>Description: </b>Common constants
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public class ALRGConsts {

	public static final int DEFAULT_WAIT_TIMEOUT_SECONDS = 3;
	
	public static String REPORT_FILE_NAME = "ALRG-emailable-report.html";
	public static String REPORT_FILE_TITLE = "ALRG TestNG Report";	

    public static final int MAX_WAIT_TIMEOUT_SECONDS = 30;
    
	public static final String DEVICE_WEB = "web";
	public static final String DEVICE_MOBILE = "mobile";
	//public static final String DEVICE_TABLET = "tablet";
	
	public static final String TESTDATA_TEST_CASE_DESCRIPTION = "Test_Case_Description";
	public static final String TESTDATA_USER_ID = "UserId";
	public static final String TESTDATA_PASSWORD = "Password";
	public static final String TESTDATA_JIRAID = "JIRAID";
		
    public static String ENVIRONMENT_URL="";
    public static String ENVIRONMENT_URL_PARAM = "environmentUrl";
    
    public static String RUNNING_DEVICE_NAME = "";  
    public static String RUNNING_DEVICE_MODE = "";
    public static String RUNNING_DEVICE_MODE_PARAM = "runningDeviceMode";   	
    
    public static String AUTO_COMPLETE_LIST = "[class='autocomplete-list']"; 
    
    
	// *****************************************
	public static final String LST_A_VALUE_START = "option[value='";
	public static final String LST_A_VALUE_START1 = "option[value='string:";
	public static final String LST_A_VALUE_END = "']";
	public static final String LST_A_LABEL_START = "option[label='";
	public static final String LST_A_LABEL_END = "']";
    public static final String EM_DATE_PICKER_INPUT = "div.date-picker input";
    public static final String SPINNER_BLOCK_UI_MSG = "div[style='opacity: 1;'] div.block-ui-message";
    
    
   //****************** Login Page Constants *********************************
    public static final String LOGIN_USER_ID_FLD_BY_ID = "//*[@id='Email']";
    public static final String LOGIN_PWD_FLD_BY_ID = "//*[@id='Password']";
    public static final String LOGIN_BTN = "/html/body/div[2]/div/div/div/div/div/div[2]/form/div[2]/button";
    
    
    public static final String LOGIN_ERROR_MSG = "/html/body/div[2]/div/div/div/div/div/div[2]/form/div[4]/ul/li";
    public static final String LOGIN_INVALID_USERID_ERROR_MSG = "/html/body/div[2]/div/div/div/div/div/div[2]/form/span[1]";
    public static final String LOGIN_BLANK_USERID_ERROR_MSG = "//*[@id='Email-error']";
    public static final String LOGIN_BLANK_PASSWORD_ERROR_MSG = "//*[@id='Password-error']";
    
    
    public static final String LOGIN_ALLREG_ICON = "/html/body/div[2]/div/div/div/div/div/div[1]/div[2]";
    public static final String LOGIN_EYE_ICON = "/html/body/div[2]/div/div/div/div/div/div[2]/form/span[2]";
    public static final String LOGIN_REMEMBERME_CHKBOX = "//*[@id='RememberMe']";
    public static final String LOGIN_FORGOTPASSWORD_LINK = "/html/body/div[2]/div/div/div/div/div/div[2]/form/div[3]/div[1]/a";
    public static final String LOGIN_NEEDTOREGISTER_LINK = "/html/body/div[2]/div/div/div/div/div/div[2]/form/div[3]/div[2]/a";
            
    public static final String MOBL_LOGIN_BTN = "NotAvailable";
    public static final String LOGIN_TOGGLE_BTN = "NotAvailable";
    public static final String LOGIN_LOGO_LBL = "NotAvailable";
    	
    public static final String WELCOME_MSG =  "//*[@id='forgot-paswd-msg']/div/div[1]/h3";
    public static final String CONTINUE_TO_ALLREGS_BTN = "//*[@id='splash_cont']/b";
    public static final String SUB_MENU =  "//*[@id='ao-sub-menu']/li[1]";
    
    public static final String TAKE_ME_TO_HOME_PAGE_BTN="";//TODO: add locator
    public static final String SIGNUP_FOR_ALERTS="";//TODO: add locator
    
    public static final String LOGGED_OFF_MSG =  "//*[@id='forgot-paswd-msg']/div/div[1]";
    
}
