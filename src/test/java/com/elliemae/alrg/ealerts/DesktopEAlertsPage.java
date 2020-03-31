package com.elliemae.alrg.ealerts;


import java.util.List;

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
import com.elliemae.alrg.consts.EALERTConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.consts.NAVConsts;
import com.elliemae.alrg.consts.USERACCOUNTConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Archana joshi</b>
 */

public class DesktopEAlertsPage extends AbstractEAlertsPage {

    public static Logger _log = Logger.getLogger(DesktopEAlertsPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
        
    @FindBy(xpath = NAVConsts.USERNAME_LINK)
    public WebElement userNameLink;

    @FindBy(xpath = EALERTConsts.E_ALERTS_LINK)
    public WebElement eAlertsLink;

    @FindBy(xpath = EALERTConsts.STATE_COMPLIANCE_CHK_COLUMN)
    public WebElement chkBoxColumn;
    
    @FindBy(xpath = EALERTConsts.SAVE_E_ALERTS_BTN)
    public WebElement saveEAlertsBtn;
    
    @FindBy(xpath = EALERTConsts.CANCEL_E_ALERTS_BTN)
    public WebElement cancelEAlertsBtn;
   
    @FindBy(xpath = EALERTConsts.ALERT_MSGBOX_MODAL)
    public WebElement alertMsgBoxModal;
    
    @FindBy(xpath = EALERTConsts.CONFIRM_BUTTON_YES)
    public WebElement confirmBtnYes;
    
    @FindBy(xpath = EALERTConsts.CONFIRM_BUTTON_NO)
    public WebElement confirmBtnNo;

    @FindBy(xpath = EALERTConsts.MERS_CHK_BOX)
    public WebElement mersChkBox;

    @FindBy(xpath = EALERTConsts.ALABAMA_CHK_BOX)
    public WebElement alabamaChkBox;
    
    public DesktopEAlertsPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }
	
	 /**
	 * <b>Name:</b> clickEAlertsLink<br>
	 * <b>Description:</b> This method clicks on E-Alerts link which appears under user name drop down.
	 *  <b>@author: Archana Joshi</b>
	 * 
	 */	
	public void clickEAlertsLink() {
		objEllieMaeActions.alrg_clickElement(userNameLink, "UserName link"); 
		objEllieMaeActions.alrg_clickElement(eAlertsLink,"E-Alerts link.");
		
		if(objEllieMaeActions.alrg_isElementDisplayed(alertMsgBoxModal))
			objEllieMaeActions.clickElement(confirmBtnYes, "Leave button");
		
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		
		//check if modal confirmation box is present
		
	
	}	

	 /**
	 * <b>Name:</b> verifyIfAlertsClickable<br>
			 * <b>Description:</b> This method checks if Alert check boxes are clickable.
			 *  <b>@author: Archana Joshi</b>
			 * 
			 */		
	public boolean verifyIfAlertsClickable() {
		
		boolean status=true;

		List<WebElement> childs = driver.findElements(By.xpath(EALERTConsts.ALERT_DIV));
		String eleText ="";
		try {
			for (WebElement e : childs) {
				eleText = e.getText();
				if (! objEllieMaeActions.alrg_isChkBoxClickable(e,eleText))
				{
					ALRGLogger.log(_log,eleText + " E-alert Check box is not clickable ",EllieMaeLogLevel.reporter);
					return status;
				}
				else
					status=true;
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "Fail to select the element: " + eleText + "  " + e.getMessage(),EllieMaeLogLevel.debug);
		}	
		
		clickOnCancelButton();
		
	return status;
	}	
	
	 /**
	* <b>Name:</b> verifyCancelChanges<br>
	 * <b>Description:</b> This method clicks on cancel button and selects NO for save changes confirmation message.
	 * <b>@author: Archana Joshi</b>
	 * 
	 */		
	public boolean verifyCancelChanges() {
		boolean status=true;
		List<WebElement> childs = driver.findElements(By.xpath(EALERTConsts.ALERT_DIV));
		String eleText ="";
		try {
			for (WebElement e : childs) {
				eleText = e.getText();
				objEllieMaeActions.alrg_toggleChkBoxSelection(e,eleText);
				
				//if (! objEllieMaeActions.alrg_selectCheckBox(e))
				//{
				//	ALRGLogger.log(_log,eleText + "Could not check "+ eleText +" e-Alert checkbox. " ,EllieMaeLogLevel.reporter);
				//	return status;
				//}
				//else
				//	status=true;
				break; // make only one change and break
				
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "Fail to select the element: " + eleText + "  " + e.getMessage(),EllieMaeLogLevel.debug);
		}	
			
		//click on Cancel  button
		clickOnCancelButton();
			
		return status;
	}	

	/**
	* <b>Name:</b> verifySaveChanges<br>
	 * <b>Description:</b> This method clicks on save button and revisits E-alerts page for verification.
	 *  <b>@author: Archana Joshi</b>
	 * 
	 */		
	 public boolean verifySaveChanges() {
		
		//uncheck any alerts if already selected. 	
		ALRGLogger.log(_log,"uncheck any alerts if already selected." ,EllieMaeLogLevel.reporter);
		clearAllCheckBoxex();
		 
		boolean status=true;
		List<WebElement> childs = driver.findElements(By.xpath(EALERTConsts.ALERT_DIV));
		String eleText ="";
		try {
			for (WebElement e : childs) {
				eleText = e.getText();
				objEllieMaeActions.alrg_selectCheckBox(e);		
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "Fail to select the element: " + eleText + "  " + e.getMessage(),EllieMaeLogLevel.debug);
		}	
		
		clickOnSaveButton();
		
		ALRGLogger.log(_log,"Checked all alert boxes and saved." ,EllieMaeLogLevel.reporter);
		
		 //verify if Save is successful or not.
		status=VerifySavedEAlerts();
		if (status)
			ALRGLogger.log(_log, "Found all saved alerts checked." ,EllieMaeLogLevel.reporter);
	
	return status;

	}	
	 
	/**
	* <b>Name:</b> VerifySavedEAlerts<br>
				 * <b>Description:</b> This method verifies if checkbox selection is retained for saved alerts
				 *  <b>@author: Archana Joshi</b>
				 * 
				 */		
	public boolean VerifySavedEAlerts() {
			boolean status=false;
			//revisit E-alerts screen
			ALRGLogger.log(_log, "revisit E-alerts screen",EllieMaeLogLevel.reporter);
			clickEAlertsLink();
			
			List<WebElement> childs = driver.findElements(By.xpath(EALERTConsts.ALERT_DIV));
			String eleText ="";
			try {
				for (WebElement e : childs) {
					eleText = e.getText();
					
					if (!objEllieMaeActions.alrg_getCheckBoxStatus(e,eleText))
						{
						ALRGLogger.log(_log, "found  "+ eleText +"  unchecked.",EllieMaeLogLevel.reporter);
						return false;
						
						}
					
					else
						status=true;
						//System.out.println("selected element "+eleText);
				}
			} catch (Exception e) {
				ALRGLogger.log(_log,  e.getMessage(),EllieMaeLogLevel.debug);

			}	
			return status;
		}	
	 
	 public void clearAllCheckBoxex() {
			List<WebElement> childs = objEllieMaeActions.alrg_findElements(EALERTConsts.ALERT_DIV);
			String eleText ="";
			try {
				for (WebElement e : childs) {
					eleText = e.getText();
					//System.out.println("selected element "+eleText);	
					objEllieMaeActions.alrg_deSelectCheckBox(e);
						
				}
			} catch (Exception e) {
				ALRGLogger.log(_log, "Fail to select the element: " + eleText + "  " + e.getMessage(),EllieMaeLogLevel.debug);
			}	
			
			clickOnSaveButton();
			ALRGLogger.log(_log, "All alert checkboxes in unchecked state." ,EllieMaeLogLevel.reporter);

	 }	

	 public void selectAllCheckBoxex() {
			List<WebElement> childs = objEllieMaeActions.alrg_findElements(EALERTConsts.ALERT_DIV);
			String eleText ="";
			try {
				for (WebElement e : childs) {
					eleText = e.getText();
					//System.out.println("selected element "+eleText);	
					objEllieMaeActions.alrg_selectCheckBox(e);				
				}
			} catch (Exception e) {
				ALRGLogger.log(_log, "Fail to select the element: " + eleText + "  " + e.getMessage(),EllieMaeLogLevel.debug);
			}	
			
			clickOnSaveButton();
			
			ALRGLogger.log(_log, "All alert checkboxes in checked state." ,EllieMaeLogLevel.reporter);


		}	

	 /**
	* <b>Name:</b> verifyUnCheckAlertChanges<br>
	 * <b>Description:</b> This method verifies Uncheck alert changes.
	 *  <b>@author: Archana Joshi</b>
	 * 
	 */		 
	 public boolean verifyUnCheckAlertChanges()
	 {
		 //check all e-Alert check boxes if not already checked.
		 ALRGLogger.log(_log,"check/select any alerts if not selected." ,EllieMaeLogLevel.reporter);
		 selectAllCheckBoxex();
		 
		 boolean status=true;
			List<WebElement> childs = driver.findElements(By.xpath(EALERTConsts.ALERT_DIV));
			String eleText ="";
			try {
				for (WebElement e : childs) {
					eleText = e.getText();
					if (!objEllieMaeActions.alrg_deSelectCheckBox(e))
					{
						ALRGLogger.log(_log, eleText + "   e-Alert checkbox Could not be unchecked "  ,EllieMaeLogLevel.reporter);
						status=false;
					}
					else
						status=true;
				}
			} catch (Exception e) {
				ALRGLogger.log(_log, "Fail to select the element: " + eleText + "  " + e.getMessage(),EllieMaeLogLevel.debug);
			}	
			 
			clickOnSaveButton();
			
			ALRGLogger.log(_log, " unChecked all alert boxes and saved."  ,EllieMaeLogLevel.reporter);
					
			//verify if Save is successful or not.
			status=verifyUnCheckedEAlerts();
			if (!status)
				ALRGLogger.log(_log, "Found all alerts unchecked." ,EllieMaeLogLevel.reporter);
			
			return status;
	 }

	/**
	* <b>Name:</b> verifyUnCheckedEAlerts<br>
					 * <b>Description:</b> This method revisits e-Alerts screen and verifies check boxes are unchecked.
					 *  <b>@author: Archana Joshi</b>
					 * 
					 */		
	 public boolean verifyUnCheckedEAlerts() {
			boolean status=false;
			//revisit E-alerts screen
			clickEAlertsLink();
			List<WebElement> childs = driver.findElements(By.xpath(EALERTConsts.ALERT_DIV));
			String eleText ="";
			try {
				for (WebElement e : childs) {
					eleText = e.getText();
					
					if (objEllieMaeActions.alrg_getCheckBoxStatus(e,eleText))		
						{
						ALRGLogger.log(_log,"found  "+eleText +"  selected"  ,EllieMaeLogLevel.reporter);
						return true;
						}					 
					else
						status=false;

				}
			} catch (Exception e) {
				ALRGLogger.log(_log,  e.getMessage(),EllieMaeLogLevel.debug);

			}	
			return status;
		}	

	/**
	* <b>Name:</b> clickOnSaveButton<br>
	 * <b>Description:</b> This method Clicks on Save button if enabled.
	 *  <b>@author: Archana Joshi</b>
	 * 
	 */		
	 public void clickOnSaveButton()
	 {
		 	//click on Save  button if there are changes on form .
			if(objEllieMaeActions.alrg_isElementEnable(saveEAlertsBtn, "Save Button"))
			{
				objEllieMaeActions.click(saveEAlertsBtn, "Save Alerts button ");
				objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
				//objEllieMaeActions.explicitWait(, 40);
				objEllieMaeActions.waitForElementDisabled(saveEAlertsBtn,"save Button");
				
			}
	 }
	
	 /**
	 * <b>Name:</b> clickOnCancelButton<br>
	 * <b>Description:</b> This method Clicks on Cancel button.
	 *  <b>@author: Archana Joshi</b>
	 * 
	 */		
	 public void clickOnCancelButton()

	 { boolean status;
		//click on Cancel  button
			objEllieMaeActions.click(cancelEAlertsBtn, "Cancel Alerts button ");
			
			//verify if confirmation message is received or not
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
		 
	 }

	 public boolean verifySingleAlert()
		{
		 //select Alabama  state housing finance check box
		 objEllieMaeActions.alrg_selectCheckBox(alabamaChkBox);
		 //click on save
		 clickOnSaveButton();
		 //revisit e alerts screen
		 clickEAlertsLink();
		 //check status for Alabama checkbox.
		 return objEllieMaeActions.alrg_getCheckBoxStatus(alabamaChkBox);
		
		}
}
