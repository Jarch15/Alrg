package com.elliemae.alrg.smoke;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.SMOKEConsts;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;
import com.elliemae.core.asserts.Assert;

//It is just for Demo, please do not follow Aditya Shrivastava
public class DesktopSmokeTest extends ALRGApplicationBase {

	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopSmokePage smokeTestPage;
	public static Logger _log = Logger.getLogger(DesktopSmokeTest.class);
	
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }

	/**
	 * <b>Name:</b> smokeTest <br>
	 * <b>Description:</b> Smoke test, navigate through the pages
	 * 1. Verify footer
	 * <b>@author: Aditya Shrivastava</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void smokeTest(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			//objEllieMaeActions = getActions(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			smokeTestPage = new DesktopSmokePage(driver);
			
			
			
			//Login Screen verification
			loginPage.login(testData);
			ALRGLogger.log(_log, "FAILED FAILED FAILED", EllieMaeLogLevel.error);
			sAssert.assertTrue(loginPage.isWelcomeMessageDisplayed(), "Is Welcome Page Displayed");
			sAssert.assertTrue(false, "Is Welcome Page Displayed");
			
			/*loginPage.isContinueToAllRegsBtnDisplayed();
			loginPage.clickContinueToAllRegsBtn();
			loginPage.isSubMenuDisplayed();
			
			//Footer contents verification
			headerFooterPage.isAcceptableUsePolicyDisplayed();
			headerFooterPage.isPrivacyPolicyDisplayed();
			headerFooterPage.isCopyRightDisplayed();
			headerFooterPage.isVersionDisplayed();			
			
			//Navigating through pages and verifying contents
			smokeTestPage.verifyAgencyGuidesPage();
			smokeTestPage.verifyInverterLibraryPage();			
			smokeTestPage.verifyOurLibraryPage();
			smokeTestPage.verifyFederalCompliancePage();
			smokeTestPage.verifyStateCompliancePage();
			smokeTestPage.verifyMortgageMentorPage();			
			smokeTestPage.verifyHomePage();

			headerFooterPage.logout();*/
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
          //headerFooterPage.logoutNoException();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

}





