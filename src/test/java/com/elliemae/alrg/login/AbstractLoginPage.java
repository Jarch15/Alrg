package com.elliemae.alrg.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.asserts.Assert;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.config.ALRGEnvironmentDataApplication;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;

/**
 * <b>Name:</b> LoginPage</br>
 * <b>Description: </b>This page object class is for Login related functionality
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractLoginPage implements ILoginPage {

    public static Logger _log = Logger.getLogger(AbstractLoginPage.class);
    public static final String PAGE_LOAD_ERROR_400 = "400 Bad Request";

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ALRGApplicationActions objEllieMaeActions;
    

    public HashMap<String, String> environmentData = new HashMap<String, String>();
    private HashMap<String, HashMap<String, String>> userList;

    protected AbstractLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

	@FindBy(xpath = ALRGConsts.LOGIN_USER_ID_FLD_BY_ID)
	WebElement userIDField;
	
	@FindBy(xpath = ALRGConsts.LOGIN_PWD_FLD_BY_ID)
	WebElement passwordField;
	
	@FindBy(xpath = ALRGConsts.LOGIN_ERROR_MSG)
	WebElement errorMessageLabel;
	
	@FindBy(xpath = ALRGConsts.LOGIN_INVALID_USERID_ERROR_MSG)
	WebElement errorMessageInvalidEmail;

	@FindBy(xpath = ALRGConsts.LOGIN_BLANK_USERID_ERROR_MSG)
	WebElement errorMessageEmailRequired;
	
	@FindBy(xpath = ALRGConsts.LOGIN_BLANK_PASSWORD_ERROR_MSG)
	WebElement errorMessagePasswordRequired;
	
    @FindBy(xpath = ALRGConsts.WELCOME_MSG)
    public WebElement welcomeMessage;

    @FindBy(xpath = ALRGConsts.CONTINUE_TO_ALLREGS_BTN)
    public WebElement continueToAllRegsBtn;
    
    @FindBy(xpath = ALRGConsts.TAKE_ME_TO_HOME_PAGE_BTN)
    public WebElement takeMeToHomePageBtn;
  
    @FindBy(xpath = ALRGConsts.SIGNUP_FOR_ALERTS)
    public WebElement signUpForAlertsBtn; 
    
    
    @FindBy(xpath = ALRGConsts.SUB_MENU)
    public WebElement subMenu;
     
    @Override
    public abstract void clickLoginBtn();
    @Override
    public abstract boolean isForgotPasswordLinkDisplayed();
    @Override
    public abstract boolean isNeedToRegisterDisplayed();
    @Override
    public abstract boolean isEyeIconDisplayed();
    @Override
    public abstract boolean isAllRegIconDisplayed();    
    @Override
    public abstract void clickContinueToAllRegsBtn();
 
    /**
	 * <b>Name:</b> setUserID<br>
	 * <b>Description:</b> This method is used to set user ID.
	 *  <b>@author: Archana Joshi</b>
	 *  @param userID
	 */
    @Override
    public void setUserID(String userID) {
        objEllieMaeActions.alrg_typeNoWait(userIDField, userID, "User ID");
    }    
   
    /**
     * <b>Name:</b> setPassword<br>
     * <b>Description:</b> This method is used to set user password
     * <b>@author: Archana Joshi</b>
     * @param password
     */
    @Override
    public void setPassword(String password) {
        objEllieMaeActions.alrg_typeNoWait(passwordField, password, "Password");
    }

    /**
     * <b>Name:</b> getUserName<br>
     * <b>Description:</b> This  method will get user name from testData parameter.
     * <b>@author: Aditya Shrivastava</b>
     * @param testDataUserName
     * 
     * 
     */
    @Override
    public String getUserName(String testDataUserName) {
        userList = ALRGEnvironmentDataApplication.getUserListData();
        for (Map.Entry<String, HashMap<String, String>> entry : userList.entrySet()) {
            if (entry.getKey() != "" && testDataUserName.contains(entry.getKey())) {
                if (testDataUserName.charAt(0) == '$') {
                    testDataUserName = testDataUserName.replace("$" + entry.getKey(), entry.getValue().get("UserName")).trim();
                } else {
                    testDataUserName = entry.getValue().get("UserName").trim();
                }
                break;
            }
        }
        ALRGLogger.log(_log, "User name from EnvironmentConfig: " + testDataUserName, EllieMaeLogLevel.reporter);
        return testDataUserName;
    }
    
    /**
     * <b>Name:</b> getLoginErrorMessage<br>
     * <b>Description:</b> This method is used to get Error message text
     * <b>@author: Archana Joshi</b>
     * @param password
     */
    @Override
    public String getLoginErrorMessage() {
        return objEllieMaeActions.getText(errorMessageLabel).trim();
    }
  
    /**
     * <b>Name:</b> isRememberMeCheckBoxPresent<br>
     * <b>Description:</b> Check if RememberMeCheckBox is present
     * 
     * <b>@author: Archana Joshi</b>
     * 
     */
    @Override
    public boolean isRememberMeCheckBoxPresent() {
        return objEllieMaeActions.isElementPresent(By.xpath(ALRGConsts.LOGIN_REMEMBERME_CHKBOX));
    }

    /**
     * <b>Name:</b> clickRememberMe<br>
     * <b>Description:</b> Click on RememberMeCheckBox
     * <b>@author: Archana Joshi</b>
     * 
     */
    @Override
    public void clickRememberMe() {
        objEllieMaeActions.alrg_clickElement(driver.findElement(By.xpath(ALRGConsts.LOGIN_REMEMBERME_CHKBOX)), "rememberMe");
    }
    
    /**
     * <b>Name:</b> getLoginUrl<br>
     * <b>Description:</b> This method will fetch URL from environmentData(from EnvironmentConfig)
     * 
     * <b>@author: Aditya Shrivastava</b>
     * 
     */
     protected String getLoginUrl(String envDataColumn) {
        _log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
        String URL = ALRGConsts.ENVIRONMENT_URL;
        if (StringUtils.isEmpty(URL)) {
            environmentData = ALRGEnvironmentDataApplication.getEnvironmentData(FrameworkConsts.ENVIRONMENTNAME);
            URL = (StringUtils.isEmpty(environmentData.get(envDataColumn).trim()) ? URL : environmentData.get(envDataColumn).trim());
            ALRGLogger.log(_log, "URL from EnvironmentConfig: " + URL, EllieMaeLogLevel.reporter);
        } else {
        	ALRGLogger.log(_log, "URL from test parameter: " + URL, EllieMaeLogLevel.reporter);
        }
        return URL;
    }
   
    /**
     * <b>Name:</b> goToThePage<br>
     * <b>Description:</b> This method will reach required page based on parameter URL.</b>
     * <b>@author: Aditya Shrivastava</b>
     * 
     * @param URL
     * 
     */
    @Override
    public void goToThePage(String URL) {
        objEllieMaeActions.alrg_goToThePage(URL);
        objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
       // ALRGLogger.log(_log, "Expected to complete load Login page using URL: " + URL, EllieMaeLogLevel.reporter);
       // Getting ERROR 400 Bad Request intermittently
        Assert.assertFalse(driver.getTitle().contains(PAGE_LOAD_ERROR_400), "ERROR on page load '" + driver.getTitle() + "'");
       // ALRGLogger.log(_log, "No error found on loading Login Page", EllieMaeLogLevel.reporter);
    }
    
    /**
     * <b>Name:</b> login<br>
     * <b>Description:</b> This method will load userid ,Password from userlist.
     * @param testData
     * <b>@author: Aditya Shrivastava</b>
     * 
     */
    @Override
    public void login(HashMap<String, String> testData) {

        ALRGLogger.log(_log, "Log into App".toUpperCase(), EllieMaeLogLevel.reporter);
        String url = getLoginUrl();
        String userName = getUserName(testData.get("UserId"));
        String password = getPassword(testData.get("Password"), testData.get("UserId"));

        login(url, userName, password);
    }
    
    /**
     * <b>Name:</b> login<br>
     * <b>Description:</b> This overload method will load the page,type user name, password and click on login button.
     * @param testData
     * <b>@author: Aditya Shrivastava</b>
     * @param url
     * @param userID
     * @param pwd
     */
    @Override
    public void login(String url, String userID, String pwd) {

        if (!ALRGConsts.RUNNING_DEVICE_NAME.equals("")) {
            ALRGLogger.log(_log, "Login Application on DEVICE NAME:" + ALRGConsts.RUNNING_DEVICE_NAME, EllieMaeLogLevel.reporter);
        }
        ALRGLogger.log(_log, "Login: URL=" + url + ", userID=" + userID, EllieMaeLogLevel.reporter);
        goToThePage(url);        
        
        setUserID(userID);
        setPassword(pwd);
        clickLoginBtn();        
        		
    }

    /**
     * <b>Name:</b> getPassword<br>
     * <b>Description:</b> This  method will get password for user key provided.
     * @param testDataPassword
     * @param userIdKey
     * <b>@author: Aditya Shrivastava</b>
     * 
     */
    @Override
    public String getPassword(String testDataPassword, String userIdKey) {

        _log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
        userList = ALRGEnvironmentDataApplication.getUserListData();
        for (Map.Entry<String, HashMap<String, String>> entry : userList.entrySet()) {
            if (entry.getKey() != "" && userIdKey.contains(entry.getKey())) {
                testDataPassword = testDataPassword.replace("$Password", entry.getValue().get("Password"));
                break;
            }
        }
        ALRGLogger.log(_log, "Password from EnvironmentConfig: " + testDataPassword, EllieMaeLogLevel.reporter);
        return testDataPassword;
    }    
        
    /**
	 * <b>Name:</b> isWelcomeMessageDisplayed<br>
	 * <b>Description:</b> This method checks if Welcome message is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
    public boolean isWelcomeMessageDisplayed() {
    	ALRGLogger.log(_log, "Verify if Welcome Message is displayed or not", EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(welcomeMessage);
    
    }
    
    /**
   	 * <b>Name:</b> isSignUpForAlertsBtnDisplayed<br>
   	 * <b>Description:</b> This method checks if SignUp for e-alerts button is displayed or not.
   	 *  <b>@author: Archana Joshi</b>
   	 */    
    public boolean isSignUpForAlertsBtnDisplayed() {
    	ALRGLogger.log(_log, "Verify if SignUp for e-alerts Button is displayed or not", EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(signUpForAlertsBtn);
    }

    /**
   	 * <b>Name:</b> isContinueToAllRegsBtnDisplayed<br>
   	 * <b>Description:</b> This method checks if ContinueToAllRegsBtnDisplayed is displayed or not.
   	 *  <b>@author: Archana Joshi</b>
   	 */    
    public boolean isContinueToAllRegsBtnDisplayed() {
    	ALRGLogger.log(_log, "Verify if Continue To AllRegs Button is displayed or not", EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(continueToAllRegsBtn);
    }

    
    /**
	 * <b>Name:</b> isSubMenuDisplayed<br>
	 * <b>Description:</b> This method checks if sub menu is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
    public boolean isSubMenuDisplayed() {
    	ALRGLogger.log(_log, "Verify if application is landed on Home page", EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(subMenu);
    }    
    
    /**
   	 * <b>Name:</b> verifyLoginErrorMessages<br>
   	 * <b>Description:</b> This method will verify Login error message based on error condition.
   	 *  <b>@author: Aditya Shrivastava</b>
   	 */
	public boolean verifyLoginErrorMessages(String errorMessage) {
		boolean status = false, userIdStatus = false, passwordStatus = false;
		switch (errorMessage) {
		case "invalid_useridpassword":
			status = objEllieMaeActions.alrg_isTextPresent(errorMessageLabel,
					"Incorrect email address or password. Please try again.");
			break;
		case "invalid_password":
			status = objEllieMaeActions.alrg_isTextPresent(errorMessageLabel,
					"Incorrect email address or password. Please try again.");
			break;
		case "invalid_userid":
			status =objEllieMaeActions.alrg_isTextPresent(errorMessageInvalidEmail,
					"The Email field is not a valid e-mail address.");
			break;
		case "invalid_blank_useridpassword":
			userIdStatus = objEllieMaeActions.alrg_isTextPresent(errorMessageEmailRequired,
					"The Email field is required.");
			passwordStatus = objEllieMaeActions.alrg_isTextPresent(errorMessagePasswordRequired,
					"The Password field is required.");
			
			if (userIdStatus && passwordStatus)
				status = true;
			else
				status = false;
			break;
		case "invalid_blank_password":
			status = objEllieMaeActions.alrg_isTextPresent(errorMessagePasswordRequired,
					"The Password field is required.");
			break;
		case "invalid_blank_userid":
			status = objEllieMaeActions.alrg_isTextPresent(errorMessageEmailRequired, "The Email field is required.");
			break;
		default:
			ALRGLogger.log(_log, " Unexpected error occured" + objEllieMaeActions.getText(errorMessageLabel),
					EllieMaeLogLevel.reporter);

		}
		return status;
	}
	
}
