package com.elliemae.alrg.investorlibrary;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.INVESTORLIBRARYConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

/**
 * <b>Name:</b> InverterLibraryPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopInvestorLibraryPage extends AbstractInverterLibraryPage {

    public static Logger _log = Logger.getLogger(DesktopInvestorLibraryPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    

    public DesktopInvestorLibraryPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

	public void isPutNameHereDisplayed() {
		
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(INVESTORLIBRARYConsts.NAMEOFCONSTANTHERE)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuAgencyGuides);

	}	
	
	public void clickPutNameHere() {
		objEllieMaeActions.alrg_clickElement(By.xpath(INVESTORLIBRARYConsts.NAMEOFCONSTANTHERE), "Navigate to Agency Guides page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	
}
