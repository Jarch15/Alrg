package com.elliemae.alrg.smoke;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.SMOKEConsts;
import com.elliemae.alrg.nav.AbstractHeaderFooterPage;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

//First Edit
//It is just for Demo, please do not follow Aditya Shrivastava
public class DesktopSmokePage {
	
	public static Logger _log = Logger.getLogger(AbstractHeaderFooterPage.class);
    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    

    public DesktopSmokePage(WebDriver driver) {
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }
    
    
    @FindBy(xpath = SMOKEConsts.MENU_HOME)
    public WebElement menuHome;

    @FindBy(xpath = SMOKEConsts.MENU_AGENCYGUIDES)
    public WebElement menuAgencyGuides;

    @FindBy(xpath = SMOKEConsts.MENU_INVESTORLIBRARY)
    public WebElement menuInverterLibrary;

    @FindBy(xpath = SMOKEConsts.MENU_OURLIBRARY)
    public WebElement menuOurLibrary;

    @FindBy(xpath = SMOKEConsts.MENU_FEDERALCOMPLIANCE)
    public WebElement menuFederalCompliance;

    @FindBy(xpath = SMOKEConsts.MENU_STATECOMPLIANCE)
    public WebElement menuStateCompliance;

    @FindBy(xpath = SMOKEConsts.MENU_MORTGAGEMENTOR)
    public WebElement menuMortgageMentor;

    @FindBy(xpath = SMOKEConsts.HOME_PERSONALIZE_BTN)
    public WebElement homePersonalizeBtn;
    
    @FindBy(xpath = SMOKEConsts.FEDERALCOMPLIANCE_FEDERALACTS_TAB_LINK)
    public WebElement federalcomplianceFedralActsLnk;
    
    @FindBy(xpath = SMOKEConsts.COMMON_TABLEOFCONTENTS_TAB_LINK)
    public WebElement commonTableOfContentsLnk;
    
    


	public void isFedralActsDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.FEDERALCOMPLIANCE_FEDERALACTS_TAB_LINK)), EllieMaeLogLevel.reporter);
	}
	
	public void isTableOfContentsDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.COMMON_TABLEOFCONTENTS_TAB_LINK)), EllieMaeLogLevel.reporter);
	}
	
    
	public void isMenuHomeDisplayed() {
		
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_HOME)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuHome);

	}
	
	public void isPersonalizeBtnDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.HOME_PERSONALIZE_BTN)), EllieMaeLogLevel.reporter);
	}
	
	public void clickMenuHome() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_HOME), "Navigate to Home page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}


	public void isMenuAgencyGuidesDisplayed() {
		
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_AGENCYGUIDES)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuAgencyGuides);

	}	
	
	public void clickMenuAgencyGuides() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_AGENCYGUIDES), "Navigate to Agency Guides page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	

	public void isMenuInverterLibraryDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_INVESTORLIBRARY)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuInverterLibrary);

	}	
	
	public void clickMenuInverterLibrary() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_INVESTORLIBRARY), "Navigate to Inverter Library page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	
	
	
	public void isMenuOurLibraryDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_OURLIBRARY)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuOurLibrary);

	}	
	
	public void clickMenuOurLibrary() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_OURLIBRARY), "Navigate to Our Library page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	
		
	public void isMenuFederalComplianceDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_FEDERALCOMPLIANCE)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuFederalCompliance);

	}	
	
	public void clickMenuFederalCompliance() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_FEDERALCOMPLIANCE), "Navigate to Federal Compliance page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}		
	
	public void isMenuStateComplianceDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_STATECOMPLIANCE)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuStateCompliance);

	}	
	
	public void clickMenuStateCompliance() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_STATECOMPLIANCE), "Navigate to State Compliance page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}	
		
	
	public void isMenuMortgageMentorDisplayed() {
		ALRGLogger.log(_log, "Text present is : " + objEllieMaeActions.alrg_getText(By.xpath(SMOKEConsts.MENU_MORTGAGEMENTOR)), EllieMaeLogLevel.reporter);
		//objEllieMaeActions.alrg_isElementDisplayed(menuMortgageMentor);

	}	
	
	public void clickMortgageMentor() {
		objEllieMaeActions.alrg_clickElement(By.xpath(SMOKEConsts.MENU_MORTGAGEMENTOR), "Navigate to Mortgage Mentor page");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	}		
	
	
	public void verifyHomePage() {
		isMenuHomeDisplayed();
		clickMenuHome();		
		isPersonalizeBtnDisplayed();
	}
	
	public void verifyAgencyGuidesPage() {
		isMenuAgencyGuidesDisplayed();
		clickMenuAgencyGuides();
		isTableOfContentsDisplayed();
	}
	
	public void verifyInverterLibraryPage() {
		isMenuInverterLibraryDisplayed();
		clickMenuInverterLibrary();
		isTableOfContentsDisplayed();
	}
	
	public void verifyOurLibraryPage() {
		isMenuOurLibraryDisplayed();
		clickMenuOurLibrary();
		isTableOfContentsDisplayed();
	}
	
	public void verifyFederalCompliancePage() {
		isMenuFederalComplianceDisplayed();
		clickMenuFederalCompliance();
		isFedralActsDisplayed();
	}
	
	public void verifyStateCompliancePage() {
		isMenuStateComplianceDisplayed();
		clickMenuStateCompliance();
		isTableOfContentsDisplayed();
	}
	
	public void verifyMortgageMentorPage() {
		isMenuMortgageMentorDisplayed();
		clickMortgageMentor();
		isTableOfContentsDisplayed();
	}	
	
}
