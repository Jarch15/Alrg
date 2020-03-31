package com.elliemae.alrg.globalsearch;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.GLOBALSEARCHConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.asserts.Assert;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import org.testng.asserts.SoftAssert;

/**
 * @author archana joshi
 *
 */

public class DesktopGlobalSearchPage extends AbstractGlobalSearchPage {

	public static Logger _log = Logger.getLogger(DesktopGlobalSearchPage.class);

	WebDriver driver;
	ALRGApplicationActions objEllieMaeActions;
	WebDriverWait wait;
	@FindBy(xpath = GLOBALSEARCHConsts.SEARCHCONTENT_LABEL)
	public WebElement searchContentLabel;

	@FindBy(xpath = GLOBALSEARCHConsts.OPTIONS_LINK)
	public WebElement optionslink;

	@FindBy(xpath = GLOBALSEARCHConsts.FILTER_LINK)
	public WebElement filterlink;

	@FindBy(xpath = GLOBALSEARCHConsts.CLEAR_ALL_LINK)
	public WebElement clearAllLink;

	@FindBy(xpath = GLOBALSEARCHConsts.SEARCH_TEXT)
	public WebElement searchtext;

	@FindBy(xpath = GLOBALSEARCHConsts.FINDSYNONYMS_CHKBOX)
	public WebElement findSynonymsChkBox;

	@FindBy(xpath = GLOBALSEARCHConsts.MATCHALLWORDS_OPTBTN)
	public WebElement matchAllWordsOptBtn;

	@FindBy(xpath = GLOBALSEARCHConsts.SEARCHCONTENT_CLOSE_BTN)
	public WebElement searchContentCloseBtn;

	@FindBy(xpath = GLOBALSEARCHConsts.SEARCH_INDICATOR)
	public WebElement searchIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.SELECT_ALL_CHKBOX)
	public WebElement selectAllChkBox;
	
	@FindBy(xpath = GLOBALSEARCHConsts.NODE_CHKBOX)
	public WebElement nodeChkBox;
	
	@FindBy(xpath = GLOBALSEARCHConsts.SELECT_ALL_CHKBOX_LABEL)
	public WebElement selectAllChkBoxLabel;
	
	@FindBy(xpath = GLOBALSEARCHConsts.AGENCY_GUIDES_FILTER)
	public WebElement agencyGuidesFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.AGENCY_GUIDES_FILTER_INDICATOR)
	public WebElement agencyGuidesFilterIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.INVESTOR_LIBRARY_FILTER)
	public WebElement investorLiabraryFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.INVESTOR_LIBRARY_FILTER_INDICATOR)
	public WebElement investorLiabraryFilterIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.OUR_LIBRARY_FILTER)
	public WebElement ourLibraryFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.OUR_LIBRARY_FILTER_INDICATOR)
	public WebElement ourLibraryFilterIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.FEDERAL_COMPLIANCE_FILTER)
	public WebElement federalComplianceFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.FEDERAL_COMPLIANCE_INDICATOR)
	public WebElement federalComplianceFilterIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.STATE_COMPLIANCE_FILTER)
	public WebElement stateComplianceFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.STATE_COMPLIANCE_INDICATOR)
	public WebElement stateComplianceFilterIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.REGULATORY_AGENCY_FILTER)
	public WebElement regulatoryAgencyFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.REGULATORY_AGENCY_INDICATOR)
	public WebElement regulatoryAgencyFilterIndicator;

	@FindBy(xpath = GLOBALSEARCHConsts.MORTGAGE_MENTOR_FILTER)
	public WebElement mortgageMentorFilter;
	@FindBy(xpath = GLOBALSEARCHConsts.MORTGAGE_MENTOR_INDICATOR)
	public WebElement mortgageMentorFilterIndicator;

	public DesktopGlobalSearchPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
	}

	/**
	 * <b>Name:</b> isFiltersLinkPresent<br>
	 * <b>Description:</b> This method checks if filter link is enabled or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isFiltersLinkPresent() {
		clickOnSearchLabel();
		// checking for enabled instead of displayed.
		return objEllieMaeActions.alrg_isElementEnable(filterlink, "filter link");
	}

	/**
	 * <b>Name:</b> isOptionsLinkPresent<br>
	 * <b>Description:</b> This method checks if options link is enabled or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isOptionsLinkPresent() {
		boolean status = false;
		clickOnSearchLabel();
		status =objEllieMaeActions.alrg_isElementEnable(optionslink, " Options Link");
	
		// checking for enabled instead of displayed.
		// objEllieMaeActions.alrg_isElementDisplayed(optionslink);
		
		return status;
	}
	
	/**
	 * <b>Name:</b> isFindSynonymsChkBoxChecked<br>
	 * <b>Description:</b> This method checks if find synonyms check box is selected or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isFindSynonymsChkBoxChecked() {
		boolean status = false;
		clickOnSearchLabel();
		objEllieMaeActions.alrg_clickElementNoWait(optionslink, "Options link.");
		//closeFilterOptions();  

		status = objEllieMaeActions.alrg_getCheckBoxStatus(findSynonymsChkBox);
		return status;

	}

	/**
	 * <b>Name:</b> isMatchAllWordsOptBtnSelected<br>
	 * <b>Description:</b> This method checks if match all words radio button is selected or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isMatchAllWordsOptBtnSelected() {
		boolean status = false;
		clickOnSearchLabel();
		objEllieMaeActions.alrg_clickElementNoWait(optionslink, "Options link.");
		//closeFilterOptions();

		status = objEllieMaeActions.alrg_getOptionButtonStatus(matchAllWordsOptBtn, "Match all words option button");

		return status;

	}

	/**
	 * <b>Name:</b> isSearchIndicatorDisplayed<br>
	 * <b>Description:</b> This method checks if search indicator is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 */
	public boolean isSearchIndicatorDisplayed() {
		return objEllieMaeActions.alrg_isElementDisplayed(searchIndicator);
	}

	/**
	 * <b>Name:</b> clickOnClearAllLink<br>
	 * <b>Description:</b> Click on clear All link to clear all filters .
	 *  <b>@author: Archana Joshi</b>
	 */
	public void clickOnClearAllLink() {

		clickOnSearchLabel();
		objEllieMaeActions.alrg_clickElementNoWait(clearAllLink, "Clear All Link.");
	}

	/**
	 * <b>Name:</b> clickOnSearchLabel<br>
	 * <b>Description:</b> Click on search label to open filter options.
	 *  <b>@author: Archana Joshi</b>
	 */
	public void clickOnSearchLabel() {
		// ALRGLogger.log(_log, "Click on search label to open filter options",
		// EllieMaeLogLevel.reporter);
		objEllieMaeActions.explicitWait(searchContentLabel, 10);
		objEllieMaeActions.alrg_clickElement(searchContentLabel, objEllieMaeActions.getText(searchContentLabel));
	}

	/**
	 * <b>Name:</b> searchText<br>
	 * <b>Description:</b> Search for text from testData.
	 *  <b>@author: Archana Joshi</b>
	 *  @param TestData
	 */
	public void searchText(HashMap<String, String> testData) {
		String textToSearch = testData.get("TextToSearch");
		objEllieMaeActions.alrg_simlyType(searchtext, textToSearch, "Enter Value Mortgage");
		// ToDo: click on search icon/button pending
	}

	/**
	 * <b>Name:</b> getSearchLabelTxt<br>
	 * <b>Description:</b> This method wil get text for searchLabel.
	 *  <b>@author: Archana Joshi</b>
	 */
	public String getSearchLabelTxt() {
		return objEllieMaeActions.alrg_getText(searchContentLabel);
	}

	/**
	 * <b>Name:</b> verifySearchContentFilter<br>
	 * <b>Description:</b> verify filter indicator/no subscription based on User
	 * access level
	 *  <b>@author: Archana Joshi</b>
	 * @param filterName
	 * @param filterSubscription
	 *
	 */
	public boolean verifySearchContentFilter(String filterName, String filterSubscription) {
		boolean filterStatus = false;
		switch (filterName) {
		case GLOBALSEARCHConsts.AGENCY_GUIDES_TEXT:

			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(agencyGuidesFilter, agencyGuidesFilterIndicator,
						GLOBALSEARCHConsts.AGENCY_GUIDES_TEXT);
			else
				filterStatus = verifyNOSubscripion(agencyGuidesFilter, GLOBALSEARCHConsts.AGENCY_GUIDES_TEXT);
			break;

		case GLOBALSEARCHConsts.INVESTOR_LIBRARY_TEXT:
			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(investorLiabraryFilter, investorLiabraryFilterIndicator,
						GLOBALSEARCHConsts.INVESTOR_LIBRARY_TEXT);
			else
				filterStatus = verifyNOSubscripion(investorLiabraryFilter,GLOBALSEARCHConsts.INVESTOR_LIBRARY_TEXT);

			break;
		case GLOBALSEARCHConsts.OUR_LIBRARY_TEXT:
			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(ourLibraryFilter, ourLibraryFilterIndicator,
						GLOBALSEARCHConsts.OUR_LIBRARY_TEXT);
			else
				filterStatus =verifyNOSubscripion(ourLibraryFilter,GLOBALSEARCHConsts.OUR_LIBRARY_TEXT);
			
			break;
		case GLOBALSEARCHConsts.FEDERAL_COMPLIANCE_TEXT:
			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(federalComplianceFilter, federalComplianceFilterIndicator,
						GLOBALSEARCHConsts.FEDERAL_COMPLIANCE_TEXT);
			else
				filterStatus = verifyNOSubscripion(federalComplianceFilter, GLOBALSEARCHConsts.FEDERAL_COMPLIANCE_TEXT);

			break;
		case GLOBALSEARCHConsts.STATE_COMPLIANCE_TEXT:
			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(stateComplianceFilter, stateComplianceFilterIndicator,
						GLOBALSEARCHConsts.STATE_COMPLIANCE_TEXT);
			else
				filterStatus = verifyNOSubscripion(stateComplianceFilter,GLOBALSEARCHConsts.STATE_COMPLIANCE_TEXT);

			break;
		case GLOBALSEARCHConsts.MORTGAGE_MENTOR_TEXT:
			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(mortgageMentorFilter, mortgageMentorFilterIndicator,
						GLOBALSEARCHConsts.MORTGAGE_MENTOR_TEXT);
			else
				filterStatus = verifyNOSubscripion(mortgageMentorFilter,GLOBALSEARCHConsts.MORTGAGE_MENTOR_TEXT);

			break;
		case GLOBALSEARCHConsts.REGULATORY_AGENCY_TEXT:
			if (filterSubscription.toLowerCase().equals("yes"))
				filterStatus = verifySearchContentFilter(regulatoryAgencyFilter, regulatoryAgencyFilterIndicator,
						GLOBALSEARCHConsts.REGULATORY_AGENCY_TEXT);
			else
				filterStatus = verifyNOSubscripion(regulatoryAgencyFilter,GLOBALSEARCHConsts.REGULATORY_AGENCY_TEXT);
			break;

		default:
			ALRGLogger.log(_log, "Please verify testdata.", EllieMaeLogLevel.reporter);
			break;

		}
		return filterStatus;

	}

	/**
	 * <b>Name:</b> verifySearchContentFilter<br>
	 * <b>Description:</b> overload method which will check if filter indicator is displayed or not.
	 * click on filter, click on select all check box and check for filter Indicator.
	 * <b>@author: Archana Joshi</b>
	 * @param filterElement
	 * @param filterIndicator
	 * @param description
	 * 
	 */
	private boolean verifySearchContentFilter(WebElement filterElement, WebElement filterIndicator,
			String description) {
		boolean isIndicatorPresent = false;

		clickOnSearchLabel();

		objEllieMaeActions.explicitWait(filterElement, 10);
		objEllieMaeActions.alrg_clickElement(filterElement, description);

		objEllieMaeActions.explicitWait(nodeChkBox, 10);
		objEllieMaeActions.alrg_clickElement(nodeChkBox, " Select node checkBox");

		ALRGLogger.log(_log, "Verify if yellow * (filter indicator) is displayed or not", EllieMaeLogLevel.reporter);
		isIndicatorPresent = objEllieMaeActions.alrg_isElementDisplayed(filterIndicator);

		//closeFilterOptions(); // close when done.

		return isIndicatorPresent;
	}

	/**
	 * <b>Name:</b> closeFilterOptions<br>
	 * <b>Description:</b> Click on close button(X) to close filter options.
	 *  <b>@author: Archana Joshi</b>
	 */
	public void closeFilterOptions() {
		// ALRGLogger.log(_log, "Click on close button(X) to close filter
		// options.", EllieMaeLogLevel.reporter);
		objEllieMaeActions.alrg_clickElementNoWait(searchContentCloseBtn, "close button.");
	}

	/**
	 * <b>Name:</b> verifyNOSubscripion<br>
	 * <b>Description:</b>This method checks if Select All check box displayed or not for given filter.
	 * <b>@author: Archana Joshi</b>
	 * @param filterElement
	 * @param filterIndicator
	 * @param description
	 */
	public boolean verifyNOSubscripion(WebElement filterElement, String description) {
		boolean status= false;
		clickOnSearchLabel();
		objEllieMaeActions.explicitWait(filterElement, 10);
		objEllieMaeActions.alrg_clickElementNoWait(filterElement, description);

		objEllieMaeActions.explicitWait(selectAllChkBox, 10);
		ALRGLogger.log(_log, "Verify if Select All checkbox is displayed or not", EllieMaeLogLevel.reporter);
		
		
		// checking for select all label not Check box.
			status = objEllieMaeActions.alrg_isTextPresent(selectAllChkBoxLabel,"Select All");
		
		
		// As discussed and reported to David/Saguna.
		// Special treatment for OL : overwrite status flag to check if child
		// nodes for Select all are present or not.: 10/10/19 AJ
		if (description.equals(GLOBALSEARCHConsts.OUR_LIBRARY_TEXT)) {
			status = objEllieMaeActions.alrg_isElementDisplayed(nodeChkBox);
		}

		ALRGLogger.log(_log, "****User has no Subscription for " + description, EllieMaeLogLevel.reporter);
	//	closeFilterOptions();
		return status;
	}

}
