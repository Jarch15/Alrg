package com.elliemae.alrg.nav;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.NAVConsts;
import com.elliemae.alrg.utils.ALRGLogger;


/**
 * <b>Name:</b> DesktopHeaderFooterPage</br>
 * <b>Description: </b>It extends AbstractHeaderFooterPage and covers Desktop's Header Footer functionality
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopHeaderFooterPage extends AbstractHeaderFooterPage {

    public static Logger _log = Logger.getLogger(DesktopHeaderFooterPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    

    public DesktopHeaderFooterPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

    @FindBy(xpath = NAVConsts.USERNAME_LINK)
    public WebElement userNameLink;

    @FindBy(xpath = NAVConsts.LOGOUT_LINK)
    public WebElement logoutLink;

    @FindBy(xpath = ALRGConsts.LOGGED_OFF_MSG)
    public WebElement loggedOffMsg;

    @FindBy(xpath = NAVConsts.FOOTER_ACCEPTABLE_USE_POLICY)
    public WebElement acceptableUsePolicy;
    
    @FindBy(xpath = NAVConsts.FOOTER_PRIVACY_POLICY)
    public WebElement privacyPolicy;

    @FindBy(xpath = NAVConsts.FOOTER_COPY_RIGHT)
    public WebElement copyRight;

    @FindBy(xpath = NAVConsts.FOOTER_VERSION)
    public WebElement versionNumber;
    
 
    /**
     * <b>Name:</b> logoutTheApp<br>
     * <b>Description:</b> Logout the App
     */
    @Override
    public void logout() {

        ALRGLogger.log(_log, "Logout from Desktop Web App", EllieMaeLogLevel.reporter);
        objEllieMaeActions.alrg_clickElement(userNameLink, "UserName link");
        objEllieMaeActions.alrg_explicitWait(By.xpath(NAVConsts.LOGOUT_LINK), 20);
        objEllieMaeActions.alrg_simplyClicksElementNoWaitForSpinner(logoutLink, "Logout link");
        objEllieMaeActions.isTextPresent("You are now logged off", By.xpath(ALRGConsts.LOGGED_OFF_MSG));
        
    }

    @Override
    public void logoutNoException() {
        try {
            logout();
        } catch (Exception e) {
            ALRGLogger.log(_log, "Logout from Desktop Web App, exception:" + e.getMessage(), EllieMaeLogLevel.warn);
        }
    }

    
    public boolean isAcceptableUsePolicyDisplayed() {
    	ALRGLogger.log(_log, "Verify if Acceptable use policy is displayed or not", EllieMaeLogLevel.reporter);
    	ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(acceptableUsePolicy), EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(acceptableUsePolicy);
        
    }
    
    public boolean isPrivacyPolicyDisplayed() {
    	ALRGLogger.log(_log, "Verify if Privacy Policy is displayed or not", EllieMaeLogLevel.reporter); ////updated LOg Message : AJ 
    	ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(privacyPolicy), EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(privacyPolicy);
    }

   
    public boolean isCopyRightDisplayed() {
    	ALRGLogger.log(_log, "Verify if CopyRight is displayed or not", EllieMaeLogLevel.reporter); //updated LOg Message : AJ 
    	ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(copyRight), EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(copyRight);
    } 

    public boolean isVersionDisplayed() {
    	ALRGLogger.log(_log, "Verify if Version is displayed or not.", EllieMaeLogLevel.reporter);
    	ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(versionNumber), EllieMaeLogLevel.reporter);
        return objEllieMaeActions.alrg_isElementDisplayed(versionNumber);
    } 
    
}
