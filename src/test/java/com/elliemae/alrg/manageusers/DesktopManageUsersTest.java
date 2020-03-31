package com.elliemae.alrg.manageusers;



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


public class DesktopManageUsersTest extends ALRGApplicationBase {
	
	protected ILoginPage loginPage;
	protected ALRGApplicationActions objEllieMaeActions;
	DesktopHeaderFooterPage headerFooterPage;
	DesktopManageUsersPage manageUsersPage;
	private HashMap<String, String> verificationDataMap;
	
    protected ILoginPage getLoginPage(WebDriver driver) {
        return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_WEB);
    }
	
	/**
	 * <b>Name:</b> verifyColumnHeaders <br>
	 * <b>Description:</b> 
	 *  This test case covers Jira ALRG-4216 (step 3,4)
	 *  1. Headers has Active(green or red) first name, last name, email, user group, admin role, edit).
	 *  2. Headers can be sorted.
	 *  3.Selecting a user can activate, deactivate, and change user group.
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyColumnHeaders(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue("Login failure",loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();
			
			manageUsersPage=new DesktopManageUsersPage(driver);
			manageUsersPage.clickAdminTasksLink();
			
			ALRGLogger.log(_log, "Verify if column headers are displayed or not.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.isColumnHeaderDisplayed(),"Column headers not displayed.");
			
			ALRGLogger.log(_log, "Verify if buttons on screen are enabled after user selection.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.areControlsEnabled(),"Buttons are not enabled.");
			
			
			sAssert.assertAll();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
          //Put details here like log out etc
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
		
	/**
	 * <b>Name:</b> verifyUserSearch <br>
	 * <b>Description:</b> 
	 *  This test case covers Jira ALRG-4216 (step 5,6,7,8)
	 * 1.Search for users by first, last name or email. 
	 * 2.Clicking red X resets search.
	 * 3.Clicking edit icon edits user
	 * 4.Clicking on reset password will send the user the reset password email.
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyUserSearch(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue("Login failure",loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();
			
			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));
			
			manageUsersPage=new DesktopManageUsersPage(driver);
			manageUsersPage.clickAdminTasksLink();
			
			ALRGLogger.log(_log, "Search user by first name.'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyFirstNameSearch(verificationDataMap.get("FirstName")),"Could not find user by First name.");
			
			ALRGLogger.log(_log, "Search user by last name'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyLastNameSearch(verificationDataMap.get("LastName")),"Could not find user by last name.");
			
			ALRGLogger.log(_log, "Search user by email'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyEmailSearch(verificationDataMap.get("Email")),"Could not find user by email.");
			
			ALRGLogger.log(_log, "Reset search text'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyResetSearch(),"Could reset search text.");
								
			ALRGLogger.log(_log, "verify User Edit'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyUserEditClick(),"Could not Edit user.");
			
			ALRGLogger.log(_log, "verify User Update'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyUserUpdate(verificationDataMap.get("FirstName")),"Could not update user.");
					
			ALRGLogger.log(_log, "verify Reset password'", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyResetPassword(),"Could not verify reset password.");
			
			sAssert.assertAll();
			
        } catch (Throwable e) {
          handleException(_log, testData, e);
        } finally {
        	headerFooterPage.logout();
        }   
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

	/**
	 * <b>Name:</b> verifyAddImportUser <br>
	 * <b>Description:</b> 
	 *  This test case covers Jira ALRG-4216 (step 11,12,13,14,15)
	 * 1.Click on "Add user" button opens add user screen
	 * 2.Click on import users at top of screen to import bulk users
	 * 3.User can be added after entering all relevant data
	 * 4.Import users by selecting updated template file
	 * 
	 * <b>@author: Archana Joshi</b>
	 */
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyAddImportUser(HashMap<String,String> testData) {
		
		_log = Logger.getLogger(CommonUtilityApplication.getCurrentClassAndMethodNameForLogger());
		LogHelper.logTestStart(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
		
		try{
			FrameworkConsts.JIRANUMBERTOUPDATE = testData.get(ALRGConsts.TESTDATA_JIRAID);
			loginPage = getLoginPage(driver);
			headerFooterPage = new DesktopHeaderFooterPage(driver);
			loginPage.login(testData);
			//stop executing scripts if login is not successful.
			Assert.assertTrue("Login failure",loginPage.isWelcomeMessageDisplayed()); 
			
			loginPage.clickContinueToAllRegsBtn();
			
			if (!testData.get("VerificationData").isEmpty())
				verificationDataMap = CommonUtilityApplication.extractTestDataToMap(testData.get("VerificationData"));
			
			manageUsersPage=new DesktopManageUsersPage(driver);
			manageUsersPage.clickAdminTasksLink();
			
			ALRGLogger.log(_log, "verify Add User Click", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyAddUserClick(),"Could not load add user screen.");
			
			ALRGLogger.log(_log, "verify Save User.", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifySaveUser(verificationDataMap),"Could not Save user.");
				
			ALRGLogger.log(_log, "Verify Import User", EllieMaeLogLevel.reporter);
			sAssert.assertTrue(manageUsersPage.verifyImportUser(verificationDataMap.get("FilePath")+verificationDataMap.get("FileName")),"Could not import users.");
			
			sAssert.assertAll();
			
		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
			headerFooterPage.logout();

		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}

	//TODO: test method for userDeletion.
	/**
	 * <b>Name:</b> deleteUsers<br>
	 * <b>Description:</b> This method deletes users created by Add user script/import users script.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	@ApplyRetryListener
	@Test(dataProvider="get-test-data-method")
	public void verifyDeleteUser(HashMap<String, String> testData) {
		try {
			
			
			sAssert.assertAll();

		} catch (Throwable e) {
			handleException(_log, testData, e);
		} finally {
			//headerFooterPage.logout();
		}
		LogHelper.logTestEnd(_log, testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION));
	}
	}
