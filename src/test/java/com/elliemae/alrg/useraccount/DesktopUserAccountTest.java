package com.elliemae.alrg.useraccount;



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
import com.elliemae.alrg.manageusers.DesktopManageUsersPage;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;


public class DesktopUserAccountTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopManageUsersPage  manageUsersPage;
	DesktopUserAccountPage userAccountPage;
	private HashMap<String, String> verificationDataMap;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyUserAccountsUI <br>
	 * <b>Description:</b> 
	 * This test case includes ALRG-4218-step(1,2,3,4,9) 
	 * 1.Verify elements on UI
	 * 2. Make a change to one or more of the editable columns click cancle.
	 * 3.Make a change to one or more of the editable columns click save.
	 * 4.Leave the first and last name field blank and click save.
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyUserAccountsUI(HashMap<String,String> testData) {
		
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
						

			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));
			
			userAccountPage=new DesktopUserAccountPage(driver);
			userAccountPage.clickAccountLink();
		
			ALRGLogger.log(_log, "verify UI elements on Account screen.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(userAccountPage.verifyUIElements(),"UI elements are not Editable.");
			
			ALRGLogger.log(_log, "verify Edit Cancel on UI Elements.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(userAccountPage.verifyUpdateAccountCancel(verificationDataMap),"Could not cancel Edit operation.");
			
			ALRGLogger.log(_log, "verify Edit Save on UI Elements.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(userAccountPage.verifyUpdateAccountSave(verificationDataMap),"Could not Save edit operation.");
			
			ALRGLogger.log(_log, "verify Blank First and LastName.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(userAccountPage.verifyBlankName(),"Could not verify blank username.");
			
			sAssert.assertAll();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();

        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
}
