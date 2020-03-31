package com.elliemae.alrg.agencyupdates;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.home.DesktopHomePage;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;


public class DesktopAgencyUpdatesTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopAgencyUpdatesPage agencyUpdatesPage;
	DesktopHomePage	homePage;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyPutNameHere <br>
	 * <b>Description:</b> 
	 * This test case covers Jira ALRG-4120(step 1,2,3,4,6) 
	 * 1.Validate Agency Updates
	 * 2.Contains Icon, Title, Desc, Date
	 * 3.Click a title from Agency Updates widgets and opens update detail
	 * 4.Show more/less if over 10
	 * 5.View document in update detail opens document
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyUpdateDetails(HashMap<String,String> testData) {

		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			homePage=new DesktopHomePage(driver);
			agencyUpdatesPage=new DesktopAgencyUpdatesPage(driver);
			
			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue("Login failure",loginPage.isWelcomeMessageDisplayed()); 
			loginPage.clickContinueToAllRegsBtn();
		
			//objEllieMaeActions.alrg_pauseFor(300);
			//Add All widgets if not present on home screen.
			homePage.addAllWidgets();
			
			//Verify if Agency Updates widget is added to homePage
			ALRGLogger.log(_log, "verify  Header elements for Agency Updates Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(homePage.verifyWidgetHeader(HOMEConsts.AGENCY_UPDATES_TEXT),"One of the Header Element not displayed.");

			//Validate display for updates
			ALRGLogger.log(_log, "validate updates display for Agency Updates Widget.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(agencyUpdatesPage.validateUpdates(),"One of the update is missing display element.");
			
			//check how may updates are displayed/ check for show more link
			if (agencyUpdatesPage.getUpdatesCount()>=10) 
			{
			ALRGLogger.log(_log, "Verify if Show more link is displayed or not.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(agencyUpdatesPage.validateShowMoreLink(),"Show More updates link not displayed.");
			}
			
			//TODO:
			ALRGLogger.log(_log, "Verify Update Details.", EllieMaeLogLevel.reporter);
			//sAssert.assertTrue(agencyUpdatesPage.validateShowMoreLink(),"Show More updates link not displayed.");
			
			sAssert.assertAll();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
}
