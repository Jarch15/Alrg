package com.elliemae.alrg.login;

import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;

import com.elliemae.core.asserts.SoftAssert;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

/**
 * <b>Name:</b> AbstractLoginTest<br>
 * <b>Description:</b>This testcase class includes the following JIRA:<br>
 * 1. JIRA#: Verify user can login with valid credentials <b>@author: Aditya
 * Shrivastava</b>
 */
public abstract class AbstractLoginTest extends ALRGApplicationBase {

	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;

	protected abstract ALRGApplicationActions getActions(WebDriver driver);

	public abstract ILoginPage getLoginPage(WebDriver driver);

	/**
	 * <b>Name:</b> validLogin<br>
	 * <b>Description:</b> Verify user can login with correct credentials 1.
	 * Login in <b>@author: Aditya Shrivastava</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider = "get-test-data-method")
	public void validLogin(HashMap<String, String> testData) {

		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		sAssert = new SoftAssert(testStatus);
		try {
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			// objEllieMaeActions = getActions(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			
			sAssert.assertTrue(loginPage.isWelcomeMessageDisplayed(), "Welcome message is not present");
			sAssert.assertTrue(loginPage.isContinueToAllRegsBtnDisplayed(), "Could not find  ContinueToAllRegs Button");
			
			if (!loginPage.isContinueToAllRegsBtnDisplayed() && loginPage.isSignUpForAlertsBtnDisplayed()) {
				loginPage.clickTakeMeToHomePageBtn();
			}
		
			loginPage.clickContinueToAllRegsBtn();

			sAssert.assertTrue(loginPage.isSubMenuDisplayed(), "Sub menu is not displayed.");

			headerFooterPage.logout();

			sAssert.assertAll();

		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
			// headerFooterPage.logoutNoException();
		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

	/**
	 * <b>Name:</b> loginInvalidCredential<br>
	 * <b>Description:</b> Verify user is not able to login with invalid
	 * credential <b>@author: Aditya Shrivastava</b>
	 */
	@Test(dataProvider = "get-test-data-method")
	public void loginInvalidCredential(HashMap<String, String> testData) {

		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));

		try {
			loginPage = getLoginPage(driver);
			// objEllieMaeActions = getActions(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			ALRGLogger.log(_log, "Verify login error message", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(loginPage.verifyLoginErrorMessages(testData.get("Error_Case")));
			
			sAssert.assertAll();

		} catch (Exception e) {
			handleException(_log, testData, e);
		} finally {
		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

	/**
	 * <b>Name:</b> verifyLoginUI<br>
	 * <b>Description:</b> Verify UI elements on login page .
	 *  <b>@author: Archana Joshi</b>
	 */
	@Test(dataProvider = "get-test-data-method")
	public void verifyLoginUI(HashMap<String, String> testData) {

		sAssert = new SoftAssert(testStatus);
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));

		try {

			loginPage = getLoginPage(driver);
			loginPage.goToThePage(loginPage.getLoginUrl());

			sAssert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), "Forgot password Link is  not present.");
			sAssert.assertTrue(loginPage.isEyeIconDisplayed(), "Eye icon is not present");
			sAssert.assertTrue(loginPage.isNeedToRegisterDisplayed(), "Need to Register Link is not present.");
			sAssert.assertTrue(loginPage.isRememberMeCheckBoxPresent(), "Remember Me CheckBox is not present.");
			sAssert.assertTrue(loginPage.isAllRegIconDisplayed(), "AllReg Icon is not displayed.");
			sAssert.assertTrue(loginPage.isPasswordFiledDisplayed(), "Password field is not displayed.");
			sAssert.assertTrue(loginPage.isUserIdFiledDisplayed(), "EmaildId field is not displayed.");
			sAssert.assertAll();

		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

}
