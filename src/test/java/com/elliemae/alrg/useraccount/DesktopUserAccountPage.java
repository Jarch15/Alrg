package com.elliemae.alrg.useraccount;


import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.AGENCYUPDATESConsts;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.consts.MANAGEUSERSConsts;
import com.elliemae.alrg.consts.NAVConsts;
import com.elliemae.alrg.consts.USERACCOUNTConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopUserAccountPage extends AbstractUserAccount {

    public static Logger _log = Logger.getLogger(DesktopUserAccountPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    
    @FindBy(xpath = NAVConsts.USERNAME_LINK)
    public WebElement userNameLink;

    @FindBy(xpath = MANAGEUSERSConsts.ADMINISTRATION_TASKS_LINK)
    public WebElement adminTasksLink;
    
    @FindBy(xpath = USERACCOUNTConsts.ACCOUNT_LINK)
    public WebElement accountLink;
    
    @FindBy(xpath = USERACCOUNTConsts.EMAIL_TXT)
    public WebElement emailTxt;
    
    @FindBy(xpath = USERACCOUNTConsts.FIRST_NAME_TXT)
    public WebElement firstNameTxt;
   
    @FindBy(xpath = USERACCOUNTConsts.LAST_NAME_TXT)
    public WebElement lastNameTxt;
  
    @FindBy(xpath = USERACCOUNTConsts.AREA_OF_RESPONSIBILITY)
    public WebElement areaOfResponsibility;
    
    @FindBy(xpath = USERACCOUNTConsts.Job_CATEGORY)
    public WebElement jobCategory;
    
    @FindBy(xpath = USERACCOUNTConsts.CANCEL_BTN)
    public WebElement cancelButton;
    
    @FindBy(xpath = USERACCOUNTConsts.SAVE_BTN)
    public WebElement saveButton;
    
    @FindBy(xpath = USERACCOUNTConsts.ALERT_MSGBOX_MODAL)
    public WebElement alertMsgBoxModal;
    
    @FindBy(xpath = USERACCOUNTConsts.CONFIRM_BUTTON_YES)
    public WebElement confirmBtnYes;
    
    @FindBy(xpath = USERACCOUNTConsts.CONFIRM_BUTTON_NO)
    public WebElement confirmBtnNo;
    
    @FindBy(xpath = USERACCOUNTConsts.FIRST_NAME_ERROR_MSG)
    public WebElement firstNameErrorMsg;
    
    @FindBy(xpath = USERACCOUNTConsts.LAST_NAME_ERROR_MSG)
    public WebElement lastNameErrorMsg;
    
    
    public DesktopUserAccountPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

    /**
   	 * <b>Name:</b> verifyUIElements<br>
	 * <b>Description:</b> This method verified UI elements on Account screen.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyUIElements() {
		
		boolean status=true;
				
		if(!objEllieMaeActions.alrg_isElementEnable(firstNameTxt, "First Name Text box"))
			status=false;
		
		if(!objEllieMaeActions.alrg_isElementEnable(lastNameTxt, "Last Name Text box"))
			status=false;
		
		if(!objEllieMaeActions.alrg_isElementEnable(areaOfResponsibility, "Area of responsibility"))
			status=false;
		
		if(!objEllieMaeActions.alrg_isElementEnable(jobCategory, "Job Categoty"))
			status=false;
		
		if(!objEllieMaeActions.alrg_isElementEnable(cancelButton, "Cancel button"))
			status=false;
		

		if(!objEllieMaeActions.alrg_isElementDisabled(saveButton,"Save button"))
			status=false;
			
		if(!objEllieMaeActions.alrg_isElementDisabled(emailTxt,"Email Id txt box"))
			status=false;
		
		
	if (status==false)
		ALRGLogger.log(_log, "One of the UI element is not editable.", EllieMaeLogLevel.reporter);
	else
		ALRGLogger.log(_log, "All UI elements are editable . EmailID field is disabled.", EllieMaeLogLevel.reporter);
	return status;

	}	
	
	 /**
	 * <b>Name:</b> clickAccountLink<br>
		 * <b>Description:</b> This method clicks on Account link which appears under user name drop down.
		 *  <b>@author: Archana Joshi</b>
		 * 
		 */	
	public void clickAccountLink() {
		objEllieMaeActions.alrg_clickElement(userNameLink, "UserName link"); 
		objEllieMaeActions.alrg_clickElement(accountLink,"Account link");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	
  
	/**
	 * <b>Name:</b> updateAccount<br>
		 * <b>Description:</b> This method updates editable fileds, clicks on cancel and says no for confirmation message.
		 *  <b>@author: Archana Joshi</b>
		 * 
		 */	
	public boolean verifyUpdateAccountCancel(HashMap<String ,String> userData)
	{
		boolean status=false;
		
		setAccountData(userData.get("FirstName"),userData.get("LastName"));
		
		objEllieMaeActions.clickElement(cancelButton, "Cancel button");
		ALRGLogger.log(_log, "Verify if Confirmation message is displayed", EllieMaeLogLevel.reporter);
		
		objEllieMaeActions.explicitWait(alertMsgBoxModal, 40);
		status=objEllieMaeActions.alrg_isElementDisplayed(alertMsgBoxModal);
		
		if (status) {
			objEllieMaeActions.explicitWait(confirmBtnNo, 40);
			objEllieMaeActions.clickElement(confirmBtnNo, "No button");
			ALRGLogger.log(_log, "selected NO for confirmation message.", EllieMaeLogLevel.reporter);
			
		} else {
			ALRGLogger.log(_log, "Confirmation message not received.", EllieMaeLogLevel.reporter);
		}
	
	return status;
		
	}

	/**
	 * <b>Name:</b> updateAccount<br>
		 * <b>Description:</b> This method updates editable fields, clicks on Save button.
		 *  <b>@author: Archana Joshi</b>
		 * 
		 */	
	public boolean verifyUpdateAccountSave(HashMap<String ,String> userData)
	{
		boolean status=false;
		
		//retain old values to be retained after save success verification.   --to avoid next run failures.
		String firstNameBeforeSave=objEllieMaeActions.alrg_getTextBoxValue(firstNameTxt);
		String lastNameBeforeSave=objEllieMaeActions.alrg_getTextBoxValue(lastNameTxt);

		//save values from test data.
		setAccountData(userData.get("FirstName"),userData.get("LastName"));
		
		//save button does not become enabled unless there is some change on form data.
		if(objEllieMaeActions.alrg_isElementEnable(saveButton,"saveButton"))
		{
			objEllieMaeActions.clickElement(saveButton, "save button");
			//objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		}
		else
			ALRGLogger.log(_log, "Could not click Save button.", EllieMaeLogLevel.reporter);
		
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		//revisit Accounts page for verification.
		clickAccountLink();
		
		status=verifyUpdatedValues(userData);
		//Save old data for user --to avoid next run failures.
		saveOldData(firstNameBeforeSave,lastNameBeforeSave);
	
	return status;
		
	}
	
	/**
	 *<b>Name:</b> setAccountData<br>
	 * <b>Description:</b> This method sets values for firstname and last name.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param userData
	 */	
	public void setAccountData(String firstName,String lastName)
	{
		try {
			objEllieMaeActions.explicitWait(firstNameTxt, 20);
			objEllieMaeActions.alrg_simlyType(firstNameTxt, firstName, "FirstName Text box");

			objEllieMaeActions.explicitWait(lastNameTxt, 20);
			objEllieMaeActions.alrg_simlyType(lastNameTxt, lastName, "LastName Text box");

		} catch (Exception ex) {
			ALRGLogger.log(_log, "Error while setAccountData. ** " + ex.getMessage(), EllieMaeLogLevel.reporter);

		}
		
	}

	/**
	 *<b>Name:</b> verifyBlankName<br>
	 * <b>Description:</b> This method verifies error message for Blank first name and last name.
	 * clicks on cancel button to retain original data.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  
	 */	
	public boolean verifyBlankName(){
		boolean fNameStatus=false,lNameStatus=false;
		// provide blank input
		objEllieMaeActions.explicitWait(firstNameTxt, 30);
		objEllieMaeActions.alrg_simlyType(firstNameTxt, "", "FirstName Text box");

		objEllieMaeActions.explicitWait(lastNameTxt, 30);
		objEllieMaeActions.alrg_simlyType(lastNameTxt, "", "LastName Text box");

		// click on save button
		objEllieMaeActions.clickElement(saveButton, "save button");

		// vefify error message
		fNameStatus = objEllieMaeActions.alrg_isTextPresent(firstNameErrorMsg,
				USERACCOUNTConsts.FIRST_NAME_ERROR_MSG_TEXT);
		lNameStatus = objEllieMaeActions.alrg_isTextPresent(lastNameErrorMsg,
				USERACCOUNTConsts.LAST_NAME_ERROR_MSG_TEXT);
		
		//click on cancel button to have old data retained.
		objEllieMaeActions.clickElement(cancelButton, "Cancel button");
		ALRGLogger.log(_log, "Verify if Confirmation message is displayed", EllieMaeLogLevel.reporter);
		
		objEllieMaeActions.explicitWait(confirmBtnNo, 40);
		objEllieMaeActions.clickElement(confirmBtnNo, "No button");
		ALRGLogger.log(_log, "selected NO for confirmation message.", EllieMaeLogLevel.reporter);
			
				
		if (fNameStatus && lNameStatus)
			return true;
		else
			return false;
		
	}

	/**
	 *<b>Name:</b> saveOldData<br>
	 * <b>Description:</b> This method Saves old data.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param userData
	 */	
	public boolean saveOldData(String firstName,String lastName){
		boolean status=true;
		setAccountData(firstName,lastName);
		//save button does not become enabled unless there is some change on form data.
		if(objEllieMaeActions.alrg_isElementEnable(saveButton,"saveButton"))
		{	ALRGLogger.log(_log, "Saving old data.", EllieMaeLogLevel.reporter);
			objEllieMaeActions.clickElement(saveButton, "save button");
			objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		}
		else
			ALRGLogger.log(_log, "Could not click Save button.", EllieMaeLogLevel.reporter);

		status=objEllieMaeActions.alrg_isElementEnable(saveButton,"saveButton");
		if(status)
			ALRGLogger.log(_log, "Failed to save old data.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Saved old data successfully", EllieMaeLogLevel.reporter);
		return status;
	}
	
	/**
	 *<b>Name:</b> verifyUpdatedValues<br>
	 * <b>Description:</b> This method verifies saved values.
	 * 
	 *  <b>@author: Archana Joshi</b>
	 *  @param userData
	 */	
	public boolean verifyUpdatedValues(HashMap<String ,String> userData)
	{
		String firstName = objEllieMaeActions.alrg_getTextBoxValue(firstNameTxt);
		String lastName = objEllieMaeActions.alrg_getTextBoxValue(lastNameTxt);

		if (userData.get("FirstName").toLowerCase().trim().equals(firstName.toLowerCase().trim())
				&& userData.get("LastName").toLowerCase().trim().equals(lastName.toLowerCase().trim())) {
			ALRGLogger.log(_log, "Save data successful.", EllieMaeLogLevel.reporter);
			
			return true;
		} else {
		
			ALRGLogger.log(_log, "Save data failed.", EllieMaeLogLevel.reporter);
			return false;
		}
		
			
			
		
	}
}
