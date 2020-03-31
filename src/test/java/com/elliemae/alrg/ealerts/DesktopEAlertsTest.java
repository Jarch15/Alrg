package com.elliemae.alrg.ealerts;



import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;


public class DesktopEAlertsTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopEAlertsPage eAlertsPage;
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyEAlertSave <br>
	 * <b>Description:</b> 
	 * 1. Checking/unchecking will enable save button. Check one or more boxes for Alerts
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyEAlertSave(HashMap<String,String> testData) {
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
			loginPage.clickContinueToAllRegsBtn();
			
			eAlertsPage=new DesktopEAlertsPage(driver);
			
			eAlertsPage.clickEAlertsLink();
			
			//sequence of call  for these functions is important please do not change.
			//1.verifySaveChanges 2. verifyUnCheckAlertChanges 3. verifyCancelChanges 4. verifyIfAlertsClickable
			
			ALRGLogger.log(_log, "verify check save changes for E-Alerts.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(eAlertsPage.verifySaveChanges(),"Could not save check changes.");
				
			ALRGLogger.log(_log, "verify uncheck save changes for E-Alerts.'", EllieMaeLogLevel.reporter);
			sAssert.assertFalse(eAlertsPage.verifyUnCheckAlertChanges(),"Could not save uncheck changes.");
				
			ALRGLogger.log(_log, "verify cancel changes for E-Alerts.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(eAlertsPage.verifyCancelChanges(),"Could not cancel changes.");
			
			ALRGLogger.log(_log, "verify UI elements on E-alerts page are clickable'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(eAlertsPage.verifyIfAlertsClickable(),"UI elements are not clickable.");
			

			//ALRGLogger.log(_log, "verify single Alerts'", EllieMaeLogLevel.reporter);
			//sAssert.assertTrue(eAlertsPage.verifySingleAlert(),"verify single alert failed.");
			
			
			sAssert.assertAll();
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
          headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
}
