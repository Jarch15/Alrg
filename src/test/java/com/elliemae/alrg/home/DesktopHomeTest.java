package com.elliemae.alrg.home;



import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;


public class DesktopHomeTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopHomePage	homePage;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyPreferences <br>
	 * <b>Description:</b> 
	 * This test case covers Jira ALRG-4117(step 1,2,3,4)
	 * 1. Click on  personalize settings button.
	 * 2. Add/Remove widgets
	 * * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyPreferences(HashMap<String,String> testData) {
	
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			homePage=new DesktopHomePage(driver);
			
			loginPage.login(testData);
			
			//stop executing scripts if login is not successful.
			Assert.assertTrue("Login failure",loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();
			
			//Click on Personalize button.	
			//homePage.clickPersonalizeBtn();
			
			ALRGLogger.log(_log, "Verify Add/ Remove Widgets.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyAddRemoveWidgets(),"Could not Add/Remove one of the widget.");
	
			sAssert.assertAll();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
	/**
	 * <b>Name:</b> verifyWidgetHeader <br>
	 * <b>Description:</b> 
	 * This test case covers Jira ALRG-4117(step 5,6)
	 * Each widget should have: 1.Edit icon 2.Expand Collapse arrow in header 3.Search past updates in header 4.Show More if there are more than 10 updates
	 * * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyWidgetHeader(HashMap<String,String> testData) {
	
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			homePage=new DesktopHomePage(driver);
			
			loginPage.login(testData);
			
			//stop executing scripts if login is not successful.
			Assert.assertTrue("Login failure",loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();		
			ALRGLogger.log(_log, "Add all widgets to home page, if not present alreay.", EllieMaeLogLevel.reporter);
			homePage.addAllWidgets();
			
			ALRGLogger.log(_log, "verify  Header elements for Agency Updates Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.AGENCY_UPDATES_TEXT),"One of the Header Element not displayed.");
			
			ALRGLogger.log(_log, "verify  Header elements for Investor Updates Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.INVESTOR_UPDATES_TEXT),"One of the Header Element not displayed.");
			
			ALRGLogger.log(_log, "verify  Header elements for Federal Compliance Updates Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.FEDERAL_COMPLIANCE_UPDATES_TEXT),"One of the Header Element not displayed.");
			
			ALRGLogger.log(_log, "verify  Header elements for State Compliance Updates Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.STATE_COMPLIANCE_UPDATES_TEXT),"One of the Header Element not displayed.");
			
			
			ALRGLogger.log(_log, "verify  Header elements for Recently viewed  Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.RECENTLY_VIEWED_TEXT),"One of the Header Element not displayed.");
			
			ALRGLogger.log(_log, "verify  Header elements for Favorites Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.FAVORITES_TEXT),"One of the Header Element not displayed.");
			
			ALRGLogger.log(_log, "verify  Header elements for Public Notes Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.PUBLIC_NOTES_TEXT),"One of the Header Element not displayed.");
			
			sAssert.assertAll();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
}
