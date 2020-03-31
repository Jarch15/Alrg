package com.elliemae.alrg.agencyupdates;

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
import com.elliemae.alrg.consts.SMOKEConsts;
import com.elliemae.alrg.home.DesktopHomePage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;



/**
 * <b>Name:</b> AgencyGuidesPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopAgencyUpdatesPage extends AbstractAgencyUpdatesPage {

	public static Logger _log = Logger.getLogger(DesktopAgencyUpdatesPage.class);
	 
    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    DesktopHomePage homePage;
    
    @FindBy(xpath = AGENCYUPDATESConsts.AU_WIDGET_CONTENT)
    public WebElement widgetContent ;
    
    @FindBy(xpath = AGENCYUPDATESConsts.SHOW_MORE_LINK)
    public WebElement showMoreLink ;
    
    public DesktopAgencyUpdatesPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
        
    }
   
   
    
	public boolean validateUpdates() {
		boolean status = false;
		 status=objEllieMaeActions.alrg_validateUpdateDisplay(widgetContent);
		 return status;

	}

	public boolean  validateShowMoreLink() {
		boolean status = false;
		
		 status=objEllieMaeActions.alrg_isElementDisplayed(showMoreLink);
		 return status;
	}	
	
	public int getUpdatesCount()
	{
		return objEllieMaeActions.alrg_getUpdatesCount(widgetContent);
	}
}
