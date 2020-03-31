package com.elliemae.alrg.manageusers;

import java.sql.Connection;
import java.util.HashMap;


import org.apache.http.impl.nio.conn.AsyncSchemeRegistryFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.config.ALRGSQLDBHelper;
import com.elliemae.alrg.consts.AGENCYUPDATESConsts;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.consts.MANAGEUSERSConsts;
import com.elliemae.alrg.consts.NAVConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Archana Joshi</b>
 */

public class DesktopManageUsersPage extends AbstractManageUsers {

    public static Logger _log = Logger.getLogger(DesktopManageUsersPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    

    @FindBy(xpath = NAVConsts.USERNAME_LINK)
    public WebElement userNameLink;

    @FindBy(xpath = MANAGEUSERSConsts.ADMINISTRATION_TASKS_LINK)
    public WebElement adminTasksLink;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_ACTIVE)
    public WebElement columnHeaderActive;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_FIRSTNAME)
    public WebElement columnHeaderFirstName;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_LASTNAME)
    public WebElement columnHeaderLastName;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_EMAIL)
    public WebElement columnHeaderEmail;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_USERGROUP)
    public WebElement columnHeaderUserGroup;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_ADMINROLE)
    public WebElement columnHeaderAdminRole;
    
    @FindBy(xpath = MANAGEUSERSConsts.COLUMN_HEADER_EDIT)
    public WebElement columnHeaderEdit ;
    
    @FindBy(xpath = MANAGEUSERSConsts.SELECT_USER_CHK_BOX)
    public WebElement selectUserChkBox ;
    
    @FindBy(xpath = MANAGEUSERSConsts.ACTIVATE_BTN)
    public WebElement activateBtn;

    @FindBy(xpath = MANAGEUSERSConsts.DEACTIVATE_BTN)
    public WebElement deactivateBtn ;
    
    @FindBy(xpath = MANAGEUSERSConsts.CHANGE_GROUP_BTN)
    public WebElement changeUserGroupBtn ;
    
    @FindBy(xpath = MANAGEUSERSConsts.SEARCH_USER_TXTBOX)
    public WebElement searchUserTxtBox ;
    
    @FindBy(xpath = MANAGEUSERSConsts.SEARCH_REMOVE_ICON)
    public WebElement searchIcon ;
    
    @FindBy(xpath = MANAGEUSERSConsts.ROW_FIRST_NAME)
    public WebElement rowFirstName ;
    
    @FindBy(xpath = MANAGEUSERSConsts.ROW_LAST_NAME)  
    public WebElement rowLastName ;
    
    @FindBy(xpath = MANAGEUSERSConsts.ROW_EMAIL)
    public WebElement rowEmail ;

    @FindBy(xpath = MANAGEUSERSConsts.ROW_USER_EDIT_ICON)
    public WebElement rowUserEditIcon ;
        
    @FindBy(xpath = MANAGEUSERSConsts.UPDATE_BTN)
    public WebElement updateBtn ;
   
    @FindBy(xpath = MANAGEUSERSConsts.SUCCESS_MSG_CONTAINER)
    public WebElement successMsgContainer ;
    
    @FindBy(xpath = MANAGEUSERSConsts.ERROR_MSG_CONTAINER)
    public WebElement errorMsgContainer ;
    
    
    @FindBy(xpath = MANAGEUSERSConsts.RESET_PASSWORD_BTN)
    public WebElement resetPasswordBtn ;
    
    @FindBy(xpath = MANAGEUSERSConsts.PASSWORD_RESET_OK_BUTTON)
    public WebElement resetPasswordOKBtn ;
      
    @FindBy(xpath = MANAGEUSERSConsts.EMAIL_TXTBOX)
    public WebElement emailTxtBox ;
    
    @FindBy(xpath = MANAGEUSERSConsts.FIRSTNAME_TXTBOX)
    public WebElement firstNameTxt ;
    
    @FindBy(xpath = MANAGEUSERSConsts.LASTNAME_TXTBOX)
    public WebElement lastNameTxt ;
    
    @FindBy(xpath = MANAGEUSERSConsts.EMAIL_TXTBOX)
    public WebElement emailTxt ;
    
    @FindBy(xpath = MANAGEUSERSConsts.VERIFY_EMAIL_TXTBOX)
    public WebElement verifyEmailTxt ;
    
    @FindBy(xpath = MANAGEUSERSConsts.USER_GROUP)
    public WebElement userGroup ;

    @FindBy(xpath = MANAGEUSERSConsts.USER_CATEGORY)
    public WebElement userCategory;
    
    @FindBy(xpath = MANAGEUSERSConsts.USER_RESPONSIBILITY)
    public WebElement userResponsibility ;
   
    @FindBy(xpath = MANAGEUSERSConsts.ROW_USER_DATA_CONTENT)
    public WebElement rowUserDataContent ;
    
    @FindBy(xpath = MANAGEUSERSConsts.ADD_USER_BTN)
    public WebElement addUserBtn ;
    
    @FindBy(xpath = MANAGEUSERSConsts.SAVE_USER_BTN)
    public WebElement saveUserBtn ;
   
    @FindBy(xpath = MANAGEUSERSConsts.ADD_EDIT_USER_TITLE)
    public WebElement AddEditUserTitle ;
    
    @FindBy(xpath = MANAGEUSERSConsts.IMPORT_USER_LINK)
    public WebElement importUsersLink ;
    
    @FindBy(xpath = MANAGEUSERSConsts.FILE_UPLOAD_TXT)
    public WebElement fileUploadTxt ;
    
    @FindBy(xpath = MANAGEUSERSConsts.IMPORT_USER_UPLOAD_BTN)
    public WebElement importUserBtn ;
    
    @FindBy(xpath = MANAGEUSERSConsts.CHOOSE_FILE_USER_UPLOAD_BTN)
    public WebElement choosefileBtn ;
    
    @FindBy(xpath = MANAGEUSERSConsts.IMPORT_USER_RESULT_MSG)
    public WebElement importUsersResultMsg ;
    
    public DesktopManageUsersPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

    /**
	 * <b>Name:</b> isColumnHeaderDisplayed<br>
	 * <b>Description:</b> This method checks if All column headers on Manage users screen are displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isColumnHeaderDisplayed() {
		boolean status=true;
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderActive))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'Active' not displayed .", EllieMaeLogLevel.reporter);
		}
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderFirstName))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'FirstName' not displayed .", EllieMaeLogLevel.reporter);
		}
		
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderLastName))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'LastName' not displayed .", EllieMaeLogLevel.reporter);
		};
		
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderAdminRole))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'AdminRole' not displayed .", EllieMaeLogLevel.reporter);
		}
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderEdit))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'Edit' not displayed .", EllieMaeLogLevel.reporter);
		}
		
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderUserGroup))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'UserGroup' not displayed .", EllieMaeLogLevel.reporter);
		}
		
		if(!objEllieMaeActions.alrg_isElementDisplayed(columnHeaderEmail))
		{
			status=false;
			ALRGLogger.log(_log, "Column header 'Email' not displayed .", EllieMaeLogLevel.reporter);
		}
		
		if (status==false)
			ALRGLogger.log(_log, "One of the column header not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "All column header displayed.", EllieMaeLogLevel.reporter);
		return status;

	}	
	
	/**
	 * <b>Name:</b> verifyFirstNameSearch<br>
	 * <b>Description:</b> This method checks if user could be searched using FirstName.
	 * @param firstName 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyFirstNameSearch(String firstName) {
		boolean status=true;
		setSearchText(firstName);
		
		status=objEllieMaeActions.alrg_isTextPresent(rowFirstName, firstName.toLowerCase());
		
		if (status==false)
			ALRGLogger.log(_log, "Could not find user by First Name.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "User search by first name successful.", EllieMaeLogLevel.reporter);
		return status;

	}	

	/**
	 * <b>Name:</b> verifyLastNameSearch<br>
	 * <b>Description:</b> This method checks if user could be searched using LastName.
	 * @param lastName 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyLastNameSearch(String lastName) {
		boolean status=true;
		
		setSearchText(lastName);
		
		status=objEllieMaeActions.alrg_isTextPresent(rowLastName, lastName.toLowerCase());
		if (status==false)
			ALRGLogger.log(_log, "Could not find user by last Name.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "User search by last name successful.", EllieMaeLogLevel.reporter);
		return status;

	}	
	
	/**
	 * <b>Name:</b> verifyResetSearch<br>
	 * <b>Description:</b> This method checks if click on remove search clears search text and returns all results..
	 * @param lastName 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyResetSearch() {
		boolean status=true;
		objEllieMaeActions.alrg_clickElement(searchIcon, "remove search");
		status=objEllieMaeActions.alrg_isTextPresent(searchUserTxtBox, "");
		if (status==false)
			ALRGLogger.log(_log, "Could not reset search text.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "search reset test successful.", EllieMaeLogLevel.reporter);
		return status;

	}	
	
	/**
	* <b>Name:</b> verifyEmailSearch<br>
	 * <b>Description:</b> This method checks if user could be searched Email.
	 * @param email 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyEmailSearch(String email) {
		boolean status=true;
		
		setSearchText(email);
	
		status=objEllieMaeActions.alrg_isTextPresent(rowEmail, email.toLowerCase());
		if (status==false)
			ALRGLogger.log(_log, "Could not find user by email address.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "User search by email successful.", EllieMaeLogLevel.reporter);
		return status;
	}	
	
	/**
	* <b>Name:</b> verifyUserEdit<br>
	 * <b>Description:</b> This method checks if click on Edit icon displays user edit screen
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyUserEditClick() {
		boolean status=true;
		objEllieMaeActions.alrg_clickElement(rowUserEditIcon, "User Edit Icon");
		status=verifyEditUserScreen();
		
		if (status==false)
			ALRGLogger.log(_log, "some problem with user edit screen.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "User edit screen loaded successfully", EllieMaeLogLevel.reporter);
		return status;

	}	
	
	/**
	* <b>Name:</b> updateUser<br>
	 * <b>Description:</b> This method updates user data and checks for success message.
	 * *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyUserUpdate(String firstName) {
		boolean status=true;
		setUpdateData(firstName);
		
		objEllieMaeActions.alrg_clickElementNoWaitNoScroll(updateBtn, "Update button");
		
		//pause for a moment
		objEllieMaeActions.alrg_pauseFor(600);
		
		status= objEllieMaeActions.alrg_isTextPresent(successMsgContainer, MANAGEUSERSConsts.SUCCESS_MSG_TXT.toLowerCase());
		
		if (status==false)
			ALRGLogger.log(_log, "failed to update user.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "User update successful.", EllieMaeLogLevel.reporter);
		
		return status;
		
	}	
	
	/**
	* <b>Name:</b> verifyResetPassword<br>
	 * <b>Description:</b> This method updates user data and checks for success message.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyResetPassword() {
		boolean status=true;

		objEllieMaeActions.alrg_clickElement(resetPasswordBtn, "Reset Password button");
		
		//wait for message to display.
		objEllieMaeActions.alrg_pauseFor(500);
		
		status=objEllieMaeActions.alrg_isElementDisplayed(resetPasswordOKBtn);
		
		if (status==false)
			ALRGLogger.log(_log, "verification message not received.", EllieMaeLogLevel.reporter);
		else
		{
			ALRGLogger.log(_log, "verification message received", EllieMaeLogLevel.reporter);
			objEllieMaeActions.alrg_clickElement(resetPasswordOKBtn, "OK button");
		}
		
		return status;
		
	}	
	
	/**
	* <b>Name:</b> setUpdateData<br>
	 * <b>Description:</b> This method types in data to be updated.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public void setUpdateData(String firstName)
	{
		//just update one field.
		objEllieMaeActions.alrg_simlyType(firstNameTxt, firstName, "FirstName Text box");
		//TODO: if needed can update more fields.
		
	}
	
	/**
	* <b>Name:</b> verifyEditScreen<br>
	 * <b>Description:</b> This method verifies if controls are editable on screen.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyEditUserScreen()
	{
		boolean status=false;
		status= objEllieMaeActions.alrg_isElementEnable(updateBtn, "update Button.");
		if (status==false)
			ALRGLogger.log(_log, "Update button is disabled.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Update button is enabled.", EllieMaeLogLevel.reporter);
		
		status= objEllieMaeActions.alrg_isElementEnable(resetPasswordBtn, "Reset password Button.");
		if (status==false)
			ALRGLogger.log(_log, "Reset password button is disabled.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Reset password button is enabled.", EllieMaeLogLevel.reporter);
		
		status= objEllieMaeActions.alrg_isElementEnable(firstNameTxt, "First name text box.");
		if (status==false)
			ALRGLogger.log(_log, "First name text box is disabled.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "First name text box is enabled.", EllieMaeLogLevel.reporter);
		
		status= objEllieMaeActions.alrg_isElementDisabled(emailTxtBox,"Email Id txt box");
		if (status==false)
			ALRGLogger.log(_log, "Email Id text box is enabled.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Email Id  text box is disabled.", EllieMaeLogLevel.reporter);
		
		
		return status;
	}
	
	public void setSearchText(String searchText)
	{
		objEllieMaeActions.explicitWait(searchUserTxtBox, 30);
		objEllieMaeActions.alrg_simlyType(searchUserTxtBox, searchText, "UserSearch Text box");
		objEllieMaeActions.alrg_clickElementNoWaitNoScroll(searchIcon, "Search Icon");

		// wait for rowData content to load
		objEllieMaeActions.explicitWait(rowUserDataContent, 30);
		
		//pause for a moment
		objEllieMaeActions.alrg_pauseFor(500);
	}
	
	 /**
	* <b>Name:</b> clickAdminTasksLink<br>
	 * <b>Description:</b> This method clicks on administration tasks link which appears under user name drop down.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public void clickAdminTasksLink() {
	        objEllieMaeActions.alrg_clickElement(userNameLink, "UserName link");      
	        objEllieMaeActions.alrg_explicitWait(By.xpath(MANAGEUSERSConsts.ADMINISTRATION_TASKS_LINK), 20);
	        objEllieMaeActions.alrg_clickElement(adminTasksLink, "Administration Tasks link");
	        objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);     
	}	
    
	public boolean areControlsEnabled() {

		boolean status=true;
		objEllieMaeActions.alrg_clickElement(selectUserChkBox, " Select node checkBox");
		
		if(!objEllieMaeActions.alrg_isElementEnable(activateBtn, "Activate Button"))
			status=false;
		
		if(!objEllieMaeActions.alrg_isElementEnable(deactivateBtn, "Deactivate Button"))
			status=false;
		
		if(!objEllieMaeActions.alrg_isElementEnable(changeUserGroupBtn, "Change user group Button"))
			status=false;
				
	if (status==false)
		ALRGLogger.log(_log, "One of the button is not enabled.", EllieMaeLogLevel.reporter);
	else
		ALRGLogger.log(_log, "All buttons are enabled.", EllieMaeLogLevel.reporter);
	return status;

	}

	/**
	* <b>Name:</b> verifySaveUser<br>
	 * <b>Description:</b> This method provides data for new user, and clicks on save.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  
	 */	
	public boolean verifySaveUser(HashMap<String ,String> userData) {
		boolean status=false;
	
		setUserData(userData);
		
		//click on Save button
		objEllieMaeActions.alrg_clickElement(saveUserBtn, "Save User Button.");
		
		//wait for some time to capture success /error text
		objEllieMaeActions.alrg_explicitWait(By.xpath(MANAGEUSERSConsts.SUCCESS_MSG_CONTAINER), 30);
		
		//pause for a moment
		objEllieMaeActions.alrg_pauseFor(500);
		
		//objEllieMaeActions.alrg_explicitWait(By.xpath(MANAGEUSERSConsts.SUCCESS_MSG_CONTAINER), 30);
		//status=objEllieMaeActions.alrg_isElementDisplayed(successMsgContainer);
		
		String successMsg=objEllieMaeActions.alrg_getText(successMsgContainer);
		//String errorMsg=objEllieMaeActions.alrg_getText(errorMsgContainer);
		//System.out.println("text received="+successMsg );
		//System.out.println("errorText received="+errorMsg );
		
		if (successMsg.equals("User added successfully!")) {
			System.out.println("text0= " + objEllieMaeActions.alrg_getText(successMsgContainer));
			status = true;
		} else {
			
			System.out.println("text1= " + objEllieMaeActions.alrg_getText(errorMsgContainer));
			status = false;
		}

		if (status)
				ALRGLogger.log(_log, "Save user successful." + objEllieMaeActions.getText(successMsgContainer),
						EllieMaeLogLevel.reporter);
			else
				ALRGLogger.log(_log, "some problem with save user. " + objEllieMaeActions.getText(errorMsgContainer),
						EllieMaeLogLevel.reporter);
					
		return status;

	}	
	
	/**
	* <b>Name:</b> setUserData<br>
	 * <b>Description:</b> This method provides input data for Add user screen.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param userData
	 */	
	public void setUserData(HashMap<String ,String> userData)
	{
		try
		{
		objEllieMaeActions.alrg_simlyType(firstNameTxt, userData.get("FirstName"), "FirstName Text box");
		objEllieMaeActions.alrg_simlyType(lastNameTxt, userData.get("LastName"), "LastName Text box");
		objEllieMaeActions.alrg_simlyType(emailTxt, userData.get("Email"), "Email Text box");
		objEllieMaeActions.alrg_simlyType(verifyEmailTxt, userData.get("VerifyEmail"), "VerifyEmail Text box");
		objEllieMaeActions.alrg_clickElement(userGroup, "User group Button.");
		objEllieMaeActions.alrg_selectListByVisibleTextIgnoreCase(userGroup, userData.get("UserGroup"), "User group dropdown");
		objEllieMaeActions.alrg_selectListUsingAValue(userCategory, userData.get("JobCategory"), "JobCategory dropdown");
		objEllieMaeActions.alrg_selectListUsingAValue(userResponsibility, userData.get("AreaOfResponsibility"), "AreaOfResponsibility dropdown");
		}
		catch(Exception e)
		{
			  ALRGLogger.log(_log, "Exception in setUserData method : " + e.getMessage(),EllieMaeLogLevel.debug);
		}
	}

	/**
	* <b>Name:</b> importUser<br>
	 * <b>Description:</b> This method clicks on import users link ,
	 * <br> provides template file path and clicks on ImportUser button, verifies Result. * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyImportUser(String fileName) {
		boolean status=true;
		
		objEllieMaeActions.alrg_clickElement(importUsersLink, "Import Users Link.");
		choosefileBtn.sendKeys(System.getProperty("user.dir")+fileName);
		//String s=fileUploadTxt.getText();
		objEllieMaeActions.alrg_clickElement(importUserBtn, "Import Users Button.");
		
		objEllieMaeActions.alrg_explicitWait(By.xpath(MANAGEUSERSConsts.IMPORT_USER_RESULT_MSG), 30);
		status=objEllieMaeActions.alrg_isElementDisplayed(importUsersResultMsg);
		boolean fileUploadStatus=objEllieMaeActions.alrg_isTextPresent(fileUploadTxt, MANAGEUSERSConsts.IMPORT_ERROR_MSG_TEXT);
	
		if (status || fileUploadStatus)
		{
			ALRGLogger.log(_log, "Import file upload msg received."+objEllieMaeActions.alrg_getText(importUsersResultMsg)+"  "+objEllieMaeActions.alrg_getText(fileUploadTxt) , EllieMaeLogLevel.reporter);
			return true;
		}
		else
		{
			ALRGLogger.log(_log, "Import file upload msg not received.", EllieMaeLogLevel.reporter);
			return false;
		}
		

	}	

	/**
	* <b>Name:</b> verifyAddUserClick<br>
	 * <b>Description:</b> This method checks if Add user screen is displayed or not.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  
	 */	
	public boolean verifyAddUserClick() {
		boolean status=true;
		
		objEllieMaeActions.alrg_clickElement(addUserBtn, "Add User Button.");
		status=verifyAddUserScreen();
		if (status==false)
			ALRGLogger.log(_log, "some problem with Add user screen.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Add user screen loaded successfully", EllieMaeLogLevel.reporter);
		
		return status;

	}	
	
	/**

	 * <b>Name:</b> verifyAddUserScreen<br>
	 * <b>Description:</b> This method verifies if controls are editable on screen.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyAddUserScreen()
	{
		boolean status=false;
		status= objEllieMaeActions.alrg_isElementEnable(saveUserBtn, "Save user Button.");
		if (status==false)
			ALRGLogger.log(_log, "Save user is disabled.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Save user is enabled.", EllieMaeLogLevel.reporter);
		
		status= objEllieMaeActions.alrg_isElementEnable(firstNameTxt, "First Name textbox.");
		if (status==false)
			ALRGLogger.log(_log, "First Name text box is disabled.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "First Name text box is enabled.", EllieMaeLogLevel.reporter);
		
		status= objEllieMaeActions.alrg_isTextPresent(AddEditUserTitle, "Add users");
		if (status==false)
			ALRGLogger.log(_log, "Add Users title not present", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Add Users title present", EllieMaeLogLevel.reporter);
		
		return status;
	}

	/**
	* <b>Name:</b> deleteUsers<br>
	 * <b>Description:</b> This method deletes users created by Add user script/import users script.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public int deleteUsers()throws Exception
	{
		
		//TODO: need to decide when to execute this method.
		//this should be run after 30 mins of import/users process.
		ALRGSQLDBHelper sqlDbHelper = ALRGSQLDBHelper.getInstance();
	        String query = "";
	        Connection conn = sqlDbHelper.getDBConnection();
	        

	        query = "";//TODO: waiting for script from David.
	        return sqlDbHelper.executeUpdate(conn, query);
	}
}
