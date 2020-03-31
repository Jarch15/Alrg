package com.elliemae.alrg.home;


import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.AGENCYUPDATESConsts;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public class DesktopHomePage extends AbstractHomePage {

    public static Logger _log = Logger.getLogger(DesktopHomePage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    
	
	@FindBy(xpath = HOMEConsts.PERSONALIZE_BTN)
	public WebElement personalizeBtn ;
	
	@FindBy(xpath = HOMEConsts.PREF_CLOSE_BTN)
	public WebElement prefCloseBtn ;
	
	@FindBy(xpath = HOMEConsts.AGENCY_UPDATES_PREF_BTN)
	public WebElement agencyUpdatesPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.INVESTOR_UPDATES_PREF_BTN)
	public WebElement investorUpdatesPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.FEDERAL_COMPLIANCE_PREF_BTN)
	public WebElement federalComplianceUpdatesPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.STATE_COMPLIANCE_PREF_BTN)
	public WebElement stateComplianceUpdatesPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.FAVORITES_PREF_BTN)
	public WebElement favoritesPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.PUBLIC_NOTES_PREF_BTN)
	public WebElement publicNotesPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.OUR_LIBRARY_ANNOUNCEMENTS_PREF_BTN)
	public WebElement ourLibraryAnnouncementPrefBtn ;
	
	@FindBy(xpath = HOMEConsts.OUR_LIBRARY_RECENT_UPDATES_PREF_BTN)
	public WebElement ourLibraryRecentUpdatesPrefBtn ;

	//Agency Updates
	@FindBy(xpath = HOMEConsts.AU_WIDGET_COLLAPSE_ICON)
	public WebElement AUwidgetCollapseIcon ;
	
	@FindBy(xpath = HOMEConsts.AU_WIDGET_EDIT_ICON)
	public WebElement AUwidgetEditIcon ;

	@FindBy(xpath = HOMEConsts.AU_WIDGET_SEARCH_PAST_UPDATES_LINK)
	public WebElement AUpastUpdatesLink ;

	//Investor Updates
	@FindBy(xpath = HOMEConsts.IU_WIDGET_COLLAPSE_ICON)
	public WebElement IUwidgetCollapseIcon ;
	
	@FindBy(xpath = HOMEConsts.IU_WIDGET_EDIT_ICON)
	public WebElement IUwidgetEditIcon ;
	
	@FindBy(xpath = HOMEConsts.IU_WIDGET_SEARCH_PAST_UPDATES_LINK)
	public WebElement IUpastUpdatesLink ;
	
	//State Compliance Updates
	@FindBy(xpath = HOMEConsts.SCU_WIDGET_COLLAPSE_ICON)
	public WebElement SCUwidgetCollapseIcon ;
	
	@FindBy(xpath = HOMEConsts.SCU_WIDGET_EDIT_ICON)
	public WebElement SCUwidgetEditIcon ;
	
	@FindBy(xpath = HOMEConsts.SCU_WIDGET_SEARCH_PAST_UPDATES_LINK)
	public WebElement SCUpastUpdatesLink ;
	
	//Federal Compliance Updates
	@FindBy(xpath = HOMEConsts.SCU_WIDGET_COLLAPSE_ICON)
	public WebElement FCUwidgetCollapseIcon ;
	
	@FindBy(xpath = HOMEConsts.SCU_WIDGET_EDIT_ICON)
	public WebElement FCUwidgetEditIcon ;
	
	@FindBy(xpath = HOMEConsts.SCU_WIDGET_SEARCH_PAST_UPDATES_LINK)
	public WebElement FCUpastUpdatesLink ;
	
	//favorites
	@FindBy(xpath = HOMEConsts.FAVORITES_VIEW_ALL_LINK)
	public WebElement favoritesViewAllLink ;
	
	@FindBy(xpath = HOMEConsts.FAVORITES_WIDGET_COLLAPSE_ICON)
	public WebElement favoritesWidgetCollapseIcon ;
	
	//public Notes
	@FindBy(xpath = HOMEConsts.PUBLIC_NOTES_VIEW_ALL_LINK)
	public WebElement publicNotesViewAllLink ;
	
	@FindBy(xpath = HOMEConsts.PUBLIC_NOTES_WIDGET_COLLAPSE_ICON)
	public WebElement publicNotesWidgetCollapseIcon ;
	
	//recently viewed
	@FindBy(xpath = HOMEConsts.RECENTLY_VIEWED_VIEW_ALL_LINK)
	public WebElement recentlyViewedViewAllLink ;
	
	@FindBy(xpath = HOMEConsts.RECENTLY_VIEWED_WIDGET_COLLAPSE_ICON)
	public WebElement recentlyViewedWidgetCollapseIcon ;
	

    public DesktopHomePage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

	public boolean verifyWidget(By btnLocator, By titleLocator,String elementDescription,WebElement btnElement) {
		//wait for some time and fetch button text.
		objEllieMaeActions.alrg_pauseFor(300);
		String addRemoveAction=objEllieMaeActions.alrg_getText(btnElement);
		
		if(addRemoveAction.equals(""))
		{
		clickPersonalizeBtn();
		addRemoveAction=objEllieMaeActions.alrg_getText(btnElement);
		}
	
		boolean status= false;
		

		switch(addRemoveAction)
		{
			case "Add":
			objEllieMaeActions.alrg_clickElement(btnLocator,elementDescription);
			ALRGLogger.log(_log, " Verify "+ elementDescription +" Add Widget." ,EllieMaeLogLevel.reporter);
			status= objEllieMaeActions.isElementPresent(titleLocator);
			break;
			
			case "Remove":
			
			objEllieMaeActions.alrg_pauseFor(300);	
			objEllieMaeActions.alrg_clickElement(btnLocator,elementDescription);
			
			objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);	
			ALRGLogger.log(_log, " Verify "+ elementDescription +" Remove Widget." ,EllieMaeLogLevel.reporter);
			
			objEllieMaeActions.alrg_pauseFor(300);	
			status= !objEllieMaeActions.isElementPresent(titleLocator);
			break;
			
			default:

			ALRGLogger.log(_log, " Add/Remove operation not received." ,EllieMaeLogLevel.reporter);
			status=false;
			break;
		}
		
		
		if(status)
			ALRGLogger.log(_log, addRemoveAction+" wideget "+ elementDescription +" Successful." ,EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, addRemoveAction+" wideget "+ elementDescription +" Failed ***" ,EllieMaeLogLevel.reporter);
	
		return status;	
		
		//objEllieMaeActions.alrg_clickElement(btnLocator,elementDescription);	
		//objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		//return objEllieMaeActions.isElementPresent(titleLocator);
		
		
	}	
	
	public boolean verifyAddRemoveWidgets() {
		boolean status =true;
		//click on Personalize button
		clickPersonalizeBtn();
	
		objEllieMaeActions.alrg_pauseFor(300);	
		
		status = verifyWidget(By.xpath(HOMEConsts.AGENCY_UPDATES_PREF_BTN), By.xpath(HOMEConsts.TITLE_AGENCY_UPDATE),
				"Agency Updates", agencyUpdatesPrefBtn);

		status = verifyWidget(By.xpath(HOMEConsts.INVESTOR_UPDATES_PREF_BTN),
				By.xpath(HOMEConsts.TITLE_INVESTOR_UPDATE), "Investor Updates", investorUpdatesPrefBtn);

		status = verifyWidget(By.xpath(HOMEConsts.FEDERAL_COMPLIANCE_PREF_BTN),
				By.xpath(HOMEConsts.TITLE_FEDERAL_COMPLIANCE_UPDATE), "Federal Compliance updates",federalComplianceUpdatesPrefBtn);
			
		status = verifyWidget(By.xpath(HOMEConsts.STATE_COMPLIANCE_PREF_BTN),
				By.xpath(HOMEConsts.TITLE_STATE_COMPLIANCE_UPDATE), "State Compliance Updates",stateComplianceUpdatesPrefBtn);
		
		status = verifyWidget(By.xpath(HOMEConsts.PUBLIC_NOTES_PREF_BTN), By.xpath(HOMEConsts.TITLE_PUBLIC_NOTES),
				"Public Notes",publicNotesPrefBtn);
		
		status = verifyWidget(By.xpath(HOMEConsts.FAVORITES_PREF_BTN), By.xpath(HOMEConsts.TITLE_FAVORITES),
				"Public Notes",favoritesPrefBtn);

		status = verifyWidget(By.xpath(HOMEConsts.OUR_LIBRARY_ANNOUNCEMENTS_PREF_BTN), By.xpath(HOMEConsts.TITLE_OUR_LIBRARY_ANNOUNCEMENTS),
				"Our Library Company Announcements",ourLibraryAnnouncementPrefBtn);
		
		status = verifyWidget(By.xpath(HOMEConsts.OUR_LIBRARY_RECENT_UPDATES_PREF_BTN), By.xpath(HOMEConsts.TITLE_OUR_LIBRARY_RECENT_UPDATES),
				"Our Library Recent Updates",ourLibraryRecentUpdatesPrefBtn);
		
		
		return status;
	
	}	
 	
	 /**
	 * <b>Name:</b> clickPersonalizeBtn<br>
	 * <b>Description:</b> This method clicks on Personalize button present on home screen.
	 *  <b>@author: Archana Joshi</b>
		 */	
	 public void clickPersonalizeBtn()
	    {

		objEllieMaeActions.alrg_clickElement(personalizeBtn, "Personalize Button");
		objEllieMaeActions.alrg_pauseFor(300);
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
	    }
	
	 /**
	 * <b>Name:</b> clickPrefCloseBtn<br>
	 * <b>Description:</b> This method clicks on close Preferences button present on Personalize window..
	 *  <b>@author: Archana Joshi</b>
	 */	
	 public void clickPrefCloseBtn()
	    {
	    	objEllieMaeActions.alrg_clickElement(prefCloseBtn, "close Button");
	    	objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);	
	    }
	 
	 /**
	* <b>Name:</b> addAllWidgets<br>
		 * <b>Description:</b> This method click on personalize button and adds widgets to home screen if not already added.
		 *  <b>@author: Archana Joshi</b>
		 */	
	public void addAllWidgets()
		{
			//click on personalize button.
			clickPersonalizeBtn();
			
			if (objEllieMaeActions.alrg_getText(agencyUpdatesPrefBtn).equals("Add"))
			objEllieMaeActions.alrg_clickElement(agencyUpdatesPrefBtn, "Agency Updates Add");
	
			if (objEllieMaeActions.alrg_getText(investorUpdatesPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(investorUpdatesPrefBtn, "Investor Updates Add");
		
			if (objEllieMaeActions.alrg_getText(federalComplianceUpdatesPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(federalComplianceUpdatesPrefBtn, "Federal compliance Updates Add");
		
			if (objEllieMaeActions.alrg_getText(stateComplianceUpdatesPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(stateComplianceUpdatesPrefBtn, "State compliance Updates Add");
			
			if (objEllieMaeActions.alrg_getText(ourLibraryAnnouncementPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(ourLibraryAnnouncementPrefBtn, "OL Announcements Updates Add");
			
			if (objEllieMaeActions.alrg_getText(ourLibraryRecentUpdatesPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(ourLibraryRecentUpdatesPrefBtn, "OL Recent Updates Add");
			
			if (objEllieMaeActions.alrg_getText(publicNotesPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(publicNotesPrefBtn, "Public Notes Add");
		
			if (objEllieMaeActions.alrg_getText(favoritesPrefBtn).equals("Add"))
				objEllieMaeActions.alrg_clickElement(favoritesPrefBtn, "Favorites Add");
		
			
			clickPrefCloseBtn();
		}
		 
	 /**
	 * <b>Name:</b> verifyWidgetHeader<br>
	 * <b>Description:</b> This method verifies elements present on header for the widget title provided.
	 * makes a call to checkHeaderElements method providing correct webelement for verification.
	 *  <b>@author: Archana Joshi</b>
	 *  @param widgetTitle
	 */	
	public boolean verifyWidgetHeader(String widgetTitle) {
		boolean status=false;
	
		switch (widgetTitle) {
		case HOMEConsts.AGENCY_UPDATES_TEXT:
			
			status=checkHeaderElements(AUpastUpdatesLink, AUwidgetEditIcon, AUwidgetCollapseIcon);
			break;

		case HOMEConsts.INVESTOR_UPDATES_TEXT:
			status=checkHeaderElements(IUpastUpdatesLink, IUwidgetEditIcon, IUwidgetCollapseIcon);
			break;
		
		case HOMEConsts.FEDERAL_COMPLIANCE_UPDATES_TEXT:
			status=checkHeaderElements(FCUpastUpdatesLink, FCUwidgetEditIcon, FCUwidgetCollapseIcon);		
			break;
			
		case HOMEConsts.STATE_COMPLIANCE_UPDATES_TEXT:
			status=checkHeaderElements(SCUpastUpdatesLink, SCUwidgetEditIcon, SCUwidgetCollapseIcon);
	
			//Right hand side widgets
			//call overload method which verifies View all link and collapse icon.
			break;
		case HOMEConsts.FAVORITES_TEXT:
			status=checkHeaderElements(favoritesViewAllLink, favoritesWidgetCollapseIcon);
			break;
		
		case HOMEConsts.PUBLIC_NOTES_TEXT:
			status=checkHeaderElements(publicNotesViewAllLink, publicNotesWidgetCollapseIcon);
			break;
		
		case HOMEConsts.RECENTLY_VIEWED_TEXT:
			status=checkHeaderElements(recentlyViewedViewAllLink, recentlyViewedWidgetCollapseIcon);
			break;
			
		}
	return status;

	}
	
	/**
	 * <b>Name:</b> checkHeaderElements<br>
	 * <b>Description:</b> This method verifies if specific elements are displayed on widget header or no.
	 *  <b>@author: Archana Joshi</b>
	 *  @param searchPastUpdatesLink
	 *  @param editIcon
	 *  @param collapseIcon
	 */	
	public boolean checkHeaderElements(WebElement searchPastUpdatesLink,WebElement editIcon,WebElement collapseIcon)
	{
		boolean editIconStatus = false, collapseIconStatus = false, pastUpdatesLinkStatus = false;
		
		if (objEllieMaeActions.alrg_isElementDisplayed(searchPastUpdatesLink)) {
			pastUpdatesLinkStatus = true;
			ALRGLogger.log(_log, "Search Past Updates link displayed. ", EllieMaeLogLevel.reporter);
		} else
			ALRGLogger.log(_log, "Search Past Updates link not found.** ", EllieMaeLogLevel.reporter);

		if (objEllieMaeActions.alrg_isElementDisplayed(editIcon)) {
			editIconStatus = true;
			ALRGLogger.log(_log, "Edit Icon displayed. ", EllieMaeLogLevel.reporter);
		} else
			ALRGLogger.log(_log, "Edit Icon not found.** ", EllieMaeLogLevel.reporter);

		if (objEllieMaeActions.alrg_isElementDisplayed(collapseIcon)) {
			collapseIconStatus = true;
			ALRGLogger.log(_log, "Collapse Widget Icon displayed. ", EllieMaeLogLevel.reporter);
		} else
			ALRGLogger.log(_log, "Collapse Widget Icon not found.** ", EllieMaeLogLevel.reporter);
		
		if (collapseIconStatus && editIconStatus && pastUpdatesLinkStatus)
			return true;
		else
			return false;
	}
	
	/**
	 * <b>Name:</b> checkHeaderElements<br>
	 * <b>Description:</b> This is a overload method for Right hand side widgets, 
	 * verifies if specific elements are displayed on widget header or no.
	 *  <b>@author: Archana Joshi</b>
	 *  @param viewAllLink
	 *	@param collapseIcon
	 */	
	public boolean checkHeaderElements(WebElement viewAllLink,WebElement collapseIcon)
	{
		boolean viewAllLinkStatus = false, collapseIconStatus = false;

		if (objEllieMaeActions.alrg_isElementDisplayed(viewAllLink)) {
			viewAllLinkStatus = true;
			ALRGLogger.log(_log, "View All link is displayed. ", EllieMaeLogLevel.reporter);
		} else
			ALRGLogger.log(_log, "View All link not found.** ", EllieMaeLogLevel.reporter);

		if (objEllieMaeActions.alrg_isElementDisplayed(collapseIcon)) {
			collapseIconStatus = true;
			ALRGLogger.log(_log, "Collapse Widget Icon displayed. ", EllieMaeLogLevel.reporter);
		} else
			ALRGLogger.log(_log, "Collapse Widget Icon not found.** ", EllieMaeLogLevel.reporter);
		
		if (collapseIconStatus && viewAllLinkStatus )
			return true;
		else
			return false;
	}
	
}

