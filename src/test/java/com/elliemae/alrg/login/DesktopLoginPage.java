package com.elliemae.alrg.login;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.utils.ALRGLogger;

/**
 * <b>Name:</b> DesktopLoginPage</br>
 * <b>Description: </b>It extends AbstractLoginPage and covers Desktop's Login
 * Page functionality page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopLoginPage extends AbstractLoginPage {

	public static Logger _log = Logger.getLogger(DesktopLoginPage.class);

	public DesktopLoginPage(WebDriver driver) {
		super(driver);
		objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
	}

	@FindBy(xpath = ALRGConsts.LOGIN_BTN)
	WebElement loginBtn;

	@FindBy(xpath = ALRGConsts.LOGIN_FORGOTPASSWORD_LINK)
	WebElement forgotPassword;

	@FindBy(xpath = ALRGConsts.LOGIN_NEEDTOREGISTER_LINK)
	WebElement needToRegister;

	@FindBy(xpath = ALRGConsts.LOGIN_EYE_ICON)
	WebElement eyeIcon;

	@FindBy(xpath = ALRGConsts.LOGIN_ALLREG_ICON)
	WebElement allregIcon;

	/**
	 * <b>Name:</b> clickLoginBtn<br>
	 * <b>Description:</b> This method is used to click on the Login button
	 * <b>@author: Archana Joshi</b>
	 * 
	 */
	public void clickLoginBtn() {
		objEllieMaeActions.alrg_clickElementNoWait(loginBtn, "Log In button");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}

	/**
	 * <b>Name:</b> clickContinueToAllRegsBtn<br>
	 * <b>Description:</b> This method is used to click on the Login button
	 * 
	 * @param device
	 *            - it is optional parameter. If set any string, it will use
	 *            locator for a device
	 */
	public void clickContinueToAllRegsBtn() {
		objEllieMaeActions.alrg_clickElementNoWait(continueToAllRegsBtn, "Contiue to AllRegs Online button");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}

	/**
	 * <b>Name:</b> clickTakeMeToHomePageBtn<br>
	 * <b>Description:</b> This method is used to click on TakeMeToHomePage
	 * button <b>@author: Archana Joshi</b>
	 */
	public void clickTakeMeToHomePageBtn() {
		objEllieMaeActions.alrg_clickElementNoWait(takeMeToHomePageBtn, "No Thanks, Take me to the Home Page button");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}

	// This method is not needed as of now.
	
	/**
	 * <b>Name:</b> clickSignUpForAlertsBtn<br>
	 * <b>Description:</b> This method is used to click on TakeMeToHomePage
	 * button <b>@author: Archana Joshi</b>
	 */
	public void clickSignUpForAlertsBtn() {
		objEllieMaeActions.alrg_clickElementNoWait(signUpForAlertsBtn, "No Thanks, Take me to the Home Page button");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}

	@Override
	public String getLoginUrl() {

		String url = getLoginUrl("DesktopURL");
		return url;
	}

	/**
	 * <b>Name:</b> isForgotPasswordLinkDisplayed<br>
	 * <b>Description:</b> This method checks if forgot password is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isForgotPasswordLinkDisplayed() {
		ALRGLogger.log(_log, "Verify if Forgot password? link is displayed or not", EllieMaeLogLevel.reporter);
		return objEllieMaeActions.alrg_isElementDisplayed(forgotPassword);

	}

	/**
	 * <b>Name:</b> isNeedToRegisterDisplayed<br>
	 * <b>Description:</b> This method checks if need to register is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isNeedToRegisterDisplayed() {
		ALRGLogger.log(_log, "Verify if Need to register? link is displayed or not", EllieMaeLogLevel.reporter);
		return objEllieMaeActions.alrg_isElementDisplayed(needToRegister);

	}

	/**
	 * <b>Name:</b> isEyeIconDisplayed<br>
	 * <b>Description:</b> This method checks if EyeIcon is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isEyeIconDisplayed() {
		ALRGLogger.log(_log, "Verify if Eye Icon is displayed or not", EllieMaeLogLevel.reporter);
		return objEllieMaeActions.alrg_isElementDisplayed(eyeIcon);

	}

	/**
	 * <b>Name:</b> isAllRegIconDisplayed<br>
	 * <b>Description:</b> This method checks if AllRegIcon is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isAllRegIconDisplayed() {

		ALRGLogger.log(_log, "Verify if All Reg Icon is displayed or not", EllieMaeLogLevel.reporter);
		return objEllieMaeActions.alrg_isElementDisplayed(allregIcon);

	}

	/**
	 * <b>Name:</b> isPasswordFiledDisplayed<br>
	 * <b>Description:</b> This method checks if password filed is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isPasswordFiledDisplayed() {

		ALRGLogger.log(_log, "Verify if password textbox is displayed or not", EllieMaeLogLevel.reporter);
		return objEllieMaeActions.alrg_isElementDisplayed(passwordField);

	}

	/**
	 * <b>Name:</b> isUserIdFiledDisplayed<br>
	 * <b>Description:</b> This method checks if userId filed is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isUserIdFiledDisplayed() {

		ALRGLogger.log(_log, "Verify if userId/emailId textbox is displayed or not", EllieMaeLogLevel.reporter);

		return objEllieMaeActions.alrg_isElementDisplayed(userIDField);

	}

}
