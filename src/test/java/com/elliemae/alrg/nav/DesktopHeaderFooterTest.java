package com.elliemae.alrg.nav;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.annotation.ApplyRetryListener;

public class DesktopHeaderFooterTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyFooter <br>
	 * <b>Description:</b> Verify footer after login
	 * 1. Verify footer
	 * <b>@author: Aditya Shrivastava</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyFooter(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			//objEllieMaeActions = getActions(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			loginPage.isWelcomeMessageDisplayed();
			loginPage.isContinueToAllRegsBtnDisplayed();
			loginPage.clickContinueToAllRegsBtn();
			loginPage.isSubMenuDisplayed();	        
			headerFooterPage.isAcceptableUsePolicyDisplayed();
			headerFooterPage.isPrivacyPolicyDisplayed();
			headerFooterPage.isCopyRightDisplayed();
			headerFooterPage.isVersionDisplayed();
			headerFooterPage.logout();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
          //headerFooterPage.logoutNoException();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	

}
