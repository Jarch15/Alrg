package com.elliemae.alrg.ourlibrary;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.OURLIBRARYConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

/**
 * <b>Name:</b> OurLibraryPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopOurLibraryPage extends AbstractOurLibraryPage {

    public static Logger _log = Logger.getLogger(DesktopOurLibraryPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    

    public DesktopOurLibraryPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

	public void isPutNameHereDisplayed() {
		
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(OURLIBRARYConsts.NAMEOFCONSTANTHERE)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuAgencyGuides);

	}	
	
	public void clickPutNameHere() {
		objEllieMaeActions.alrg_clickElement(By.xpath(OURLIBRARYConsts.NAMEOFCONSTANTHERE), "Navigate to Agency Guides page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	
}
