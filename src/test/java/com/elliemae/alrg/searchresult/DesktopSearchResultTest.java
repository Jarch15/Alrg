package com.elliemae.alrg.searchresult;



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
import com.elliemae.alrg.consts.SEARCHRESULTConsts;
import com.elliemae.alrg.globalsearch.DesktopGlobalSearchPage;
import com.elliemae.alrg.login.ILoginPage;
import com.elliemae.alrg.login.LoginPageFactory;
import com.elliemae.alrg.nav.DesktopHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.annotation.ApplyRetryListener;


public class DesktopSearchResultTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopSearchResultPage desktopSearchResultPage;
	DesktopGlobalSearchPage desktopGlobalSearchPage;
	private HashMap<String, String> verificationDataMap;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyTypeAhead <br>
	 * <b>Description:</b> 
	 * This Test Method covers JIRA ALRG-4370( Step 1, 2 ,3 ,4)
	 * 1.verify type ahead is working and displays
	 * 2.verify User can select item from type ahead by using arrows on keyboard.
	 * 3.verify User can select item from type ahead by mouse pointer.

	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyTypeAhead(HashMap<String,String> testData) {
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
			
			desktopSearchResultPage = new DesktopSearchResultPage(driver);
			
			ALRGLogger.log(_log, "Verify if Type ahead options are displayed or not'", EllieMaeLogLevel.reporter);
			desktopSearchResultPage.setSearchText(testData);
			sAssert.assertTrue(desktopSearchResultPage.isTypeAheadDisplayed(),"Type ahead options are not displayed.");
			
			ALRGLogger.log(_log, "Verify if able to select from Type ahead options'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopSearchResultPage.selectSearchText(testData),"Could not select testdata value.");
			
			ALRGLogger.log(_log, "Verify if user can select from Type ahead using down arrow key.'", EllieMaeLogLevel.reporter);
			desktopSearchResultPage.selectSearchTextUsingDownArrow(testData);
			sAssert.assertEquals(desktopSearchResultPage.getResultDisplayText(), testData.get("TextToVerify").toLowerCase());
			
			ALRGLogger.log(_log, "Verify if able to select from type ahead using mouse actions.'", EllieMaeLogLevel.reporter);
			desktopSearchResultPage.setSearchText(testData);
			sAssert.assertTrue(desktopSearchResultPage.selectTextUsingActions(),"Could not select testdata value using mouse actions.");
				
			sAssert.assertAll();
				
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
	/**
	 * <b>Name:</b> verifySearch <br>
	 * <b>Description:</b> 
	 * This Test Method covers JIRA ALRG-4370( Step 5,6)
	 * 1.verify Search results show up on right side with filtered content on left.
	 * 2.verify Expanding filtered content and checking boxes will narrow search results which will update on right side of page.
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifySearchClick(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			desktopGlobalSearchPage= new DesktopGlobalSearchPage(driver) ;
			desktopSearchResultPage = new DesktopSearchResultPage(driver);
			
			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();

			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));

			for (Map.Entry<String, String> entry : verificationDataMap.entrySet()) {
				if (entry.getValue().toLowerCase().equals("yes")) {
					desktopGlobalSearchPage.verifySearchContentFilter(entry.getKey(), entry.getValue());
					desktopGlobalSearchPage.closeFilterOptions(); //close when done.							
					break; //check just for one filter and exit.
				}
			}		
			desktopSearchResultPage.setSearchText(testData);
			desktopSearchResultPage.clickSearchIcon();
			
			ALRGLogger.log(_log, "verify Search results show up on right side with filtered content on left.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(desktopSearchResultPage.isSearchResultDisplayed(testData.get("TextToVerify")),"No search results displayed.");
			
			if (Integer.parseInt(desktopSearchResultPage.getSearchResultCount()) > 0) {
				ALRGLogger.log(_log,"verify Expanding filtered content and checking boxes will narrow search results. '",EllieMaeLogLevel.reporter);
				sAssert.assertTrue(desktopSearchResultPage.isKeyWordSearchResultDisplayed(),
						"No search results updated.");
			}
			else
			{
				ALRGLogger.log(_log, "No search results displayed.", EllieMaeLogLevel.reporter);
			}
			
			
			sAssert.assertAll();
			//headerFooterPage.logout();		
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
	/**
	 * <b>Name:</b> verifySearchResults<br>
	 * <b>Description:</b> 
	 * This Test Method covers JIRA ALRG-4370( Step 8,9,10)
	 * 1.verify  no of search results returned.
	 * 2.verify bread crumb, document Icon. 
	 * 3.search text highlight.
	 * <b>@author: Archana Joshi</b>
	 */
	/**
	 * @param testData
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifySearchResults(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			desktopGlobalSearchPage= new DesktopGlobalSearchPage(driver) ;
			desktopSearchResultPage = new DesktopSearchResultPage(driver);
			
			loginPage.login(testData);
			
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
			loginPage.clickContinueToAllRegsBtn();

			//Select any filter from testData 
			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));

			for (Map.Entry<String, String> entry : verificationDataMap.entrySet()) {
				if (entry.getValue().toLowerCase().equals("yes")) {
					desktopGlobalSearchPage.verifySearchContentFilter(entry.getKey(), entry.getValue());
					desktopGlobalSearchPage.closeFilterOptions(); //close when done.							
					break; //check just for one filter and exit.
				}
			}
			
			//Enter search text and click on search button.
			desktopSearchResultPage.setSearchText(testData);
			desktopSearchResultPage.clickSearchIcon();
			
			if (Integer.parseInt(desktopSearchResultPage.getSearchResultCount()) > 0) {
					
				ALRGLogger.log(_log, "verify search result display. '", EllieMaeLogLevel.reporter);
				sAssert.assertTrue(desktopSearchResultPage.verifySeacrchResultsDisplay(),"Search result is not in correct format.");
				
				ALRGLogger.log(_log, "verify if search text is highlighted . '", EllieMaeLogLevel.reporter);
				sAssert.assertTrue(desktopSearchResultPage.isSearchTextHighlighted(testData.get("TextToSearch").toLowerCase()),"Search text is not highlighted.");
				
			}
			else
			{
				ALRGLogger.log(_log, desktopSearchResultPage.getSearchResultCount()+" results found for '"+desktopSearchResultPage.getResultDisplayText(), EllieMaeLogLevel.reporter);
			}	
			
			sAssert.assertAll();
			//headerFooterPage.logout();		
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

	/**
	 * <b>Name:</b> verifySearchResultDocument<br>
	 * <b>Description:</b> 
	 * This Test Method covers JIRA ALRG-4370( Step 11,12,13)
	 * 1.TOC button appears collapsed on left.
	 * 2.document toolbar appears on right.
	 * 3.header contains "Back to search results" link 
	 * 4.header contains Bread crumb and full screen arrows
	 * 5.Clicking on back to search results takes user back to results
	 * <b>@author: Archana Joshi</b>
	 *
	
	 * @param testData
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifySearchResultDocument(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			desktopGlobalSearchPage= new DesktopGlobalSearchPage(driver) ;
			desktopSearchResultPage = new DesktopSearchResultPage(driver);
			
			loginPage.login(testData);
			
			//stop executing scripts if login is not successful.
			Assert.assertTrue(loginPage.isWelcomeMessageDisplayed()); 
		
			loginPage.clickContinueToAllRegsBtn();

			//Select any filter from testData 
			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));

			for (Map.Entry<String, String> entry : verificationDataMap.entrySet()) {
				if (entry.getValue().toLowerCase().equals("yes")) {
					desktopGlobalSearchPage.verifySearchContentFilter(entry.getKey(), entry.getValue());
					desktopGlobalSearchPage.closeFilterOptions(); //close when done.							
					break; //check just for one filter and exit.
				}
			}	
			//Enter search text and click on search button.
			desktopSearchResultPage.setSearchText(testData);
			desktopSearchResultPage.clickSearchIcon();
			
			if (Integer.parseInt(desktopSearchResultPage.getSearchResultCount()) > 0) {
				ALRGLogger.log(_log, "verify if search result document has necessary parameters. '", EllieMaeLogLevel.reporter);
				//sAssert.assertTrue(desktopSearchResultPage.verifySearchResultDocument(testData.get("TextToSearch").toLowerCase()),"One of the necessary parameter was not found.");
				desktopSearchResultPage.goToFirstResult();
				
				sAssert.assertTrue(desktopSearchResultPage.isBackToResultsLinkDisplayed(),"Back to search results link not displayed.");
				sAssert.assertTrue(desktopSearchResultPage.isTOCDisplayed(),"Table of contents not displayed.");
				sAssert.assertTrue(desktopSearchResultPage.isBreadCrumbDisplayed(),"Header bread crumb is not displayed.");
				sAssert.assertTrue(desktopSearchResultPage.isDocumentToolBarDisplayed(),"Document toolbar not displayed.");
				sAssert.assertTrue(desktopSearchResultPage.isFullScreenArrowDisplayed(),"Full screen viewer arrow not displayed.");
				sAssert.assertTrue(desktopSearchResultPage.isHitSearchTermDisplayed(),"Hit search term box is not displayed.");
				sAssert.assertTrue(desktopSearchResultPage.isResultTextHighlighted(testData.get("TextToSearch")),"Header bread crumb is not displayed.");
				
				ALRGLogger.log(_log, "verify Back to search Results link click. ", EllieMaeLogLevel.reporter);
				sAssert.assertTrue(desktopSearchResultPage.verifyBackToResultsClick(),"Back to search results link not working.");
				
				desktopSearchResultPage.goToFirstResult();
				
				ALRGLogger.log(_log, "verify Table of contents open/collapse view. ", EllieMaeLogLevel.reporter);
				sAssert.assertTrue(desktopSearchResultPage.verifyTableOfContentsView(),"TOC open/collapse not working.");
				
				ALRGLogger.log(_log, "verify Full screen view. '", EllieMaeLogLevel.reporter);
				sAssert.assertTrue(desktopSearchResultPage.verifyFullScreenView(),"Full screen view not working.");
								
				
				
				
		}
			else
			{
				ALRGLogger.log(_log, desktopSearchResultPage.getSearchResultCount()+" results found for '"+desktopSearchResultPage.getResultDisplayText(), EllieMaeLogLevel.reporter);
			}	
			
			sAssert.assertAll();
			//headerFooterPage.logout();		
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	
	
}

	

