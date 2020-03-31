package com.elliemae.alrg.mortgagementor;



import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.annotation.ApplyRetryListener;


public class DesktopMortgageMentorTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyPutNameHere <br>
	 * <b>Description:</b> 
	 * 1. Put test case steps here
	 * <b>@author: Aditya Shrivastava</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyPutNameHere(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			loginPage.login(testData);
			loginPage.clickContinueToAllRegsBtn();

			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
          //Put details here like log out etc
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
}
