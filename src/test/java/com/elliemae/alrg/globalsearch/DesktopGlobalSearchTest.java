package com.elliemae.alrg.globalsearch;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.base.ALRGApplicationBase;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.GLOBALSEARCHConsts;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;
//import com.gargoylesoftware.htmlunit.javascript.host.Map;
import com.elliemae.core.asserts.SoftAssert;

public class DesktopGlobalSearchTest extends ALRGApplicationBase {

	protected ILoginPage loginPage;
	// protected IGlobalSearchPage globalSearchPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopGlobalSearchPage desktopGlobalSearchPage;
	private HashMap<String, String> verificationDataMap;

	protected ILoginPage getLoginPage(WebDriver driver) {
		return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
	}

	/**
	 * <b>Name:</b> verifySearcLabel <br>
	 * <b>Description:</b> 
	 * This test case includes JIRA ALRG-4369-step(1,2,4) 
	 * 1.Verify Default search label //text "All content" should be displayed 
	 * 2.verify Search indicator.
	 * 3.Verify filter indicator
	 * 4.verify filtered search label// text "Filtered" should be displayed 
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider = "get-test-data-method")

	
	public void verifySearchLabel(HashMap<String, String> testData) {

		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		
		try {
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			desktopGlobalSearchPage = new DesktopGlobalSearchPage(driver);

			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();
					
			ALRGLogger.log(_log, "Verify if Search content label displays text 'All Content'", EllieMaeLogLevel.reporter);
			sAssert.assertEquals(desktopGlobalSearchPage.getSearchLabelTxt(), GLOBALSEARCHConsts.SEARCHCONTENT_LABEL_ALL_TEXT);
				
			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));
			for (Map.Entry<String, String> entry : verificationDataMap.entrySet()) {
				if(entry.getValue().toLowerCase().equals("yes"))
				{
					sAssert.assertTrue(desktopGlobalSearchPage.verifySearchContentFilter(entry.getKey(), entry.getValue()),"Filter indicator for "+ entry.getKey() +" not displayed.");			
					break;  //check just for one filter and exit.
				}
				
			}
			ALRGLogger.log(_log, "Verify if Search indicator is displayed or not", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopGlobalSearchPage.isSearchIndicatorDisplayed(),"Search Indicator not displayed.");
			
			ALRGLogger.log(_log, "Verify if Search content label displays text 'Filtered'", EllieMaeLogLevel.reporter);
			sAssert.assertEquals(desktopGlobalSearchPage.getSearchLabelTxt(), GLOBALSEARCHConsts.SEARCHCONTENT_LABEL_FILTERED_TEXT);
			
			sAssert.assertAll();
			
		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
			headerFooterPage.logout();

		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
	/**
	 * <b>Name:</b> verifySearchContentFilters<br>
	 * <b>Description:</b> ALRG-4369-step(3)
	 *  1.Verify Only content areas a user access to are displayed on search filters 
	 *  2.Verify filter indicator
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider = "get-test-data-method")
	public void verifySearchContentFilters(HashMap<String, String> testData) {

		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));

		try {
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			desktopGlobalSearchPage = new DesktopGlobalSearchPage(driver);

			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();

			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));

			for (Map.Entry<String, String> entry : verificationDataMap.entrySet()) {
				if (entry.getValue().toLowerCase().equals("yes"))
				{
					sAssert.assertTrue(desktopGlobalSearchPage.verifySearchContentFilter(entry.getKey(), entry.getValue()),"Filter indicator for "+entry.getKey()+" not displayed.");
					desktopGlobalSearchPage.closeFilterOptions(); // close filters  when done.
				}
				else
				{
					sAssert.assertFalse(desktopGlobalSearchPage.verifySearchContentFilter(entry.getKey(), entry.getValue()),"SelectAll check box for "+entry.getKey()+" is displayed.");
					desktopGlobalSearchPage.closeFilterOptions(); // close filters when done.
				}
			}
			sAssert.assertAll();
			
		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
			headerFooterPage.logout();
		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

	/**
	 * <b>Name:</b> verifySearchTabLinks<br>
	 * <b>Description:</b> ALRG-4369-step(5,6,7,8,9)
	 * 1.Verify Filer, Options links are present or not.
	 * 2.verify Clear All link 
	 * 3.Search for some text.
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider = "get-test-data-method")
	public void verifySearchTabLinks(HashMap<String, String> testData) {

		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));

		try {
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			desktopGlobalSearchPage = new DesktopGlobalSearchPage(driver);

			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();
			ALRGLogger.log(_log, "Verify if filter link is displayed or not.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopGlobalSearchPage.isFiltersLinkPresent(),"Could not find Filters tab.");
			desktopGlobalSearchPage.closeFilterOptions(); // close when done.
			
			ALRGLogger.log(_log, "Verify if Options link is displayed or not", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopGlobalSearchPage.isOptionsLinkPresent(),"Could not find options tab.");
			desktopGlobalSearchPage.closeFilterOptions(); // close when done.
			
			ALRGLogger.log(_log, "Verify if Find Synonyms checkbox is selected or not", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopGlobalSearchPage.isFindSynonymsChkBoxChecked(), "Find Synonyms checkbox is not selected");
			desktopGlobalSearchPage.closeFilterOptions(); // close when done.
			
			ALRGLogger.log(_log, "Verify if Match All words option button is selected or not", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopGlobalSearchPage.isMatchAllWordsOptBtnSelected(), "Match All words option button is not selected");
			desktopGlobalSearchPage.closeFilterOptions(); // close when done.
		
			desktopGlobalSearchPage.clickOnClearAllLink();;
			ALRGLogger.log(_log, "Verify if Search content label displays text 'All Content'", EllieMaeLogLevel.reporter);
			
			sAssert.assertEquals(desktopGlobalSearchPage.getSearchLabelTxt(), GLOBALSEARCHConsts.SEARCHCONTENT_LABEL_ALL_TEXT);
			desktopGlobalSearchPage.closeFilterOptions(); // close when done.
			
			desktopGlobalSearchPage.searchText(testData);
			sAssert.assertAll();

		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
			headerFooterPage.logout();
		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

}
