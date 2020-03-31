package com.elliemae.alrg.searchresult;


import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.AGENCYUPDATESConsts;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.GLOBALSEARCHConsts;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.consts.SEARCHRESULTConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

import io.appium.java_client.PressesKeyCode;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Archana Joshi</b>
 */

	public class DesktopSearchResultPage extends AbstractSearchResultPage {

    public static Logger _log = Logger.getLogger(DesktopSearchResultPage.class);

    WebDriver driver;
    ALRGApplicationActions objEllieMaeActions;
    WebDriverWait wait;
    
    @FindBy(xpath = SEARCHRESULTConsts.SEARCH_TEXT)
	public WebElement searchtext;
    
    @FindBy(xpath = SEARCHRESULTConsts.TYPE_AHEAD_SEARCH_OPTIONS)
   	public WebElement typeaheadSearchOptions;
   
    @FindBy(xpath = SEARCHRESULTConsts.SEARCH_ICON_BTN)
  	public WebElement searchiconBtn;
    
    @FindBy(xpath = SEARCHRESULTConsts.SEARCH_RESULT_COUNT)
  	public WebElement searchresultcount; 
    
    @FindBy(xpath = SEARCHRESULTConsts.RESULT_DISPLAY_TXT)
  	public WebElement resultdisplaytxt;
   
    @FindBy(xpath = SEARCHRESULTConsts.SEARCH_LEFT_PANEL_FILTER)
  	public WebElement searchleftpanelfilter;
    
    @FindBy(xpath = SEARCHRESULTConsts.PAGECONTROL)
  	public WebElement pagecontrol;
    
    
    @FindBy(xpath = SEARCHRESULTConsts.KEYWORD_LINK)
  	public WebElement keywordlink;

    @FindBy(xpath = SEARCHRESULTConsts.SEARCH_RESULT_ALL)
  	public WebElement searchresultall;
    
    @FindBy(xpath = SEARCHRESULTConsts.TOC_SIDE_BAR)
  	public WebElement tocsidebar;
    
    @FindBy(xpath = SEARCHRESULTConsts.TOC_COLLAPSED)
  	public WebElement toccollapsed;
    
    @FindBy(xpath = SEARCHRESULTConsts.BACK_TO_SEARCH_RESULTS_LINK)
   	public WebElement backtosearchresultslink;
     
    @FindBy(xpath = SEARCHRESULTConsts.DOCUMENT_TOOLBAR)
  	public WebElement documenttoolbar;
 
    @FindBy(xpath = SEARCHRESULTConsts.FULLSCREEN_VIEWER_ICON)
  	public WebElement fullscreenviewicon;
    
    @FindBy(xpath = SEARCHRESULTConsts.FULLSCREEN_RETURN_ICON)
  	public WebElement fullscreenreturnicon;
    
    @FindBy(xpath = SEARCHRESULTConsts.RESULT_CONTENT)
  	public WebElement resultcontent;
    
    @FindBy(xpath = SEARCHRESULTConsts.HEADER_BREADCRUMB)
  	public WebElement headerbreadcrumb;
    
    @FindBy(xpath = SEARCHRESULTConsts.HIT_SEARCH_TERM_BOX)
  	public WebElement hitsearchtermbox;
    
    public DesktopSearchResultPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;        
        objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_WEB);
    }

	public boolean verifyNoOfSearchResults(){
	
		int divDisplayCount=0;
		
		ALRGLogger.log(_log, "Check no of results displayed per page.", EllieMaeLogLevel.reporter);
		divDisplayCount=objEllieMaeActions.alrg_getSearchResultDivCount(searchresultall);
		if (divDisplayCount==2*SEARCHRESULTConsts.NO_OF_SEARCH_RESULTS_PERPAGE)
			return true;
		else
			return false;
		
	}	
	public boolean verifyDocumentIcon(){
		
		int iconDisplayCount=0;
		
		ALRGLogger.log(_log, "Check no of document icons displayed per page.", EllieMaeLogLevel.reporter);
		iconDisplayCount=objEllieMaeActions.alrg_getDocumentIconCount(searchresultall);
		if (iconDisplayCount==SEARCHRESULTConsts.NO_OF_SEARCH_RESULTS_PERPAGE)
			return true;
		else
			return false;
	}	
	
	/**
	 * <b>Name:</b> verifySeacrchResultsDisplay<br>
	 * <b>Description:</b> This method checks no of results returned,and verifies search results.
	 * if page control is displayed, check is done on first 3 pages.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifySeacrchResultsDisplay(){
		boolean status = false;
		boolean allResultStatus = false;
		
		ALRGLogger.log(_log, "Found  "+getSearchResultCount() +"  results for  "+getResultDisplayText(), EllieMaeLogLevel.reporter);
		status = objEllieMaeActions.alrg_validateResultDisplay(searchresultall);

		if (isPageControlDisplayed()) {
			allResultStatus = objEllieMaeActions.checkAllPageResults(searchresultall);
		} else {
			allResultStatus = true;// All search results are displayed on single/first page							
		}

		if (status && allResultStatus)
			return true;
		else
			return false;
	}
	
	/**
	 * <b>Name:</b> goToFirstResult<br>
	 * <b>Description:</b> This method clicks on first result on page.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	
	public void goToFirstResult()
	{
		ALRGLogger.log(_log, "Clicking on first available result.", EllieMaeLogLevel.reporter);
		objEllieMaeActions.alrg_clickOnFirstResult(searchresultall);
	}
	
	/**
	 * <b>Name:</b> verifyBackToResultsClick<br>
	 * <b>Description:</b> This method checks if Back to search results link is taking user back to all results..
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyBackToResultsClick() {
		boolean status = false;
		objEllieMaeActions.alrg_clickElement(backtosearchresultslink, "back to search results link");
		status=objEllieMaeActions.alrg_isElementDisplayed(searchresultcount);
		if (status==false)
			ALRGLogger.log(_log, "Could not reach back to results display.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Back to results display.", EllieMaeLogLevel.reporter);
		
		
		return status;

	}

	/**
	 * <b>Name:</b> verifyFullScreenView<br>
	 * <b>Description:</b> This method checks if clicked on full screen arrow, makes screen appear full screen.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyFullScreenView() {
		boolean status = false;
		objEllieMaeActions.alrg_clickElement(fullscreenviewicon, "FullScreen arrow icon.");
		status=objEllieMaeActions.alrg_isElementDisplayed(fullscreenreturnicon);
		if (status==false)
			ALRGLogger.log(_log, "Could not get to full-screen mode.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Screen is appearing in full-screen mode.", EllieMaeLogLevel.reporter);
		//going back to normal view, so that log out is possible.
		objEllieMaeActions.alrg_clickElement(fullscreenreturnicon, "FullScreen Return icon.");
		
		return status;

	}

	/**

	 * <b>Name:</b> verifyTableOfContentsView<br>
	 * <b>Description:</b> This method checks if clicked on full screen arrow, makes screen appear full screen.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean verifyTableOfContentsView() {
		boolean status = false;
		objEllieMaeActions.alrg_clickElement(tocsidebar, "TOC side bar.");
		status=objEllieMaeActions.alrg_isElementDisplayed(toccollapsed);
		if (status==false)
			ALRGLogger.log(_log, "Could not open table of contents view.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Table of contents view open.", EllieMaeLogLevel.reporter);
		return status;

	}
	
	/**
	 * <b>Name:</b> isTOCDisplayed<br>
	 * <b>Description:</b> This method checks if table of contents side bar is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isTOCDisplayed() {
		boolean status = false;
		status = objEllieMaeActions.alrg_isElementDisplayed(tocsidebar);
		if (status==false)
			ALRGLogger.log(_log, "Table of contents not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Table of contents displayed.", EllieMaeLogLevel.reporter);
		return status;

	}
	
	/**
	 * <b>Name:</b> isBackToResultsLinkDisplayed<br>
	 * <b>Description:</b> This method checks if Back to search Results link is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isBackToResultsLinkDisplayed(){
		boolean status=false;
		status=objEllieMaeActions.alrg_isElementDisplayed(backtosearchresultslink);
		
		if (status==false)
			ALRGLogger.log(_log, "Back to search results link not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Back to search results link displayed.", EllieMaeLogLevel.reporter);
		
		return status; 
	}
		
	
	/**
	 * <b>Name:</b> isDocToolBarDisplayed<br>
	 * <b>Description:</b> This method checks if document tool bar is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isDocumentToolBarDisplayed() {
		boolean status = false;
		status = objEllieMaeActions.alrg_isElementDisplayed(documenttoolbar);
		if (status==false)
			ALRGLogger.log(_log, "Document toolbar not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Document toolbar displayed.", EllieMaeLogLevel.reporter);
		return status;

	}

	/**
	 * <b>Name:</b> isFullScreenArrowDisplayed<br>
	 * <b>Description:</b> This method checks if full screen arrow  is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isFullScreenArrowDisplayed() {
		boolean status = false;
		status = objEllieMaeActions.alrg_isElementDisplayed(fullscreenviewicon);
		if (status==false)
			ALRGLogger.log(_log, "Full screen viewer arrow not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "Full screen viewer arrow displayed.", EllieMaeLogLevel.reporter);
		return status;

	}

	/**
	 * <b>Name:</b> isBreadCrumbDisplayed<br>
	 * <b>Description:</b> This method checks if header bread crumb is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isBreadCrumbDisplayed() {
		boolean status = false;
		status = objEllieMaeActions.alrg_isElementDisplayed(headerbreadcrumb);
		if (status==false)
			ALRGLogger.log(_log, "header bread crumb is not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "header bread crumb is displayed.", EllieMaeLogLevel.reporter);
		return status;

	}

	/**
	 * <b>Name:</b> isHitSearchTermDisplayed<br>
	 * <b>Description:</b> This method checks if hit search term box is displayed or not.
	 *  <b>@author: Archana Joshi</b>
	 *  @param 
	 */	
	public boolean isHitSearchTermDisplayed() {
		boolean status = false;
		status = objEllieMaeActions.alrg_isElementDisplayed(hitsearchtermbox);
		if (status==false)
			ALRGLogger.log(_log, "hit search term box is not displayed.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "hit search term box displayed.", EllieMaeLogLevel.reporter);
		return status;

	}

	
	/**
	 * <b>Name:</b> isSearchTextHighlighted<br>
	 * <b>Description:</b> This method checks if given text is highlighted or nott.
	 *  <b>@author: Archana Joshi</b>
	 *  @param searchText
	 */	
	public boolean isSearchTextHighlighted(String searchText)
	{
		boolean status=false;
		status=objEllieMaeActions.alrg_isTextHighlighted(searchresultall,searchText);
		if (status == false)
			ALRGLogger.log(_log, "search text "+searchText +" is not highlighted.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "search text "+searchText +" is highlighted.", EllieMaeLogLevel.reporter);
		return status;
		
		
	}
	
	/**
	 * <b>Name:</b> isResultTextHighlighted<br>
	 * <b>Description:</b> This method checks if given text is highlighted or not on a given page.
	 *  <b>@author: Archana Joshi</b>
	 *  @param element
	 *  @param searchText
	 */		
	public boolean isResultTextHighlighted(String searchText) {
		boolean status = false;

		status = objEllieMaeActions.alrg_isTextHighlighted(resultcontent, searchText);
		if (status == false)
			ALRGLogger.log(_log, "search text "+searchText +" is not highlighted.", EllieMaeLogLevel.reporter);
		else
			ALRGLogger.log(_log, "search text "+searchText +" is highlighted.", EllieMaeLogLevel.reporter);
		return status;
	}
	
	/**
	 * <b>Name:</b> clickSearchIcon<br>
	 * <b>Description:</b> Click on magnifying glass icon to perform search.
	 *  <b>@author: Archana Joshi</b>
	 *  @param TestData
	 */	
	public void clickSearchIcon() {
		objEllieMaeActions.alrg_clickElement(searchiconBtn, "search button");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		objEllieMaeActions.alrg_waitForSpinnerToBeGone();
	}	

	/**
	 * <b>Name:</b> selectSearchTextUsingDownArrow<br>
	 * <b>Description:</b> This method clicks on search text box and clicks on first option available in Type ahead using down arrow key.
	 * verify test result displayed , beased on result displaytxt.
	 * <b>@author: Archana Joshi</b>
	 * @param TestData
	 */	
	public void selectSearchTextUsingDownArrow(HashMap<String, String> testData) {
		
		objEllieMaeActions.alrg_clickElement(searchtext, "");
		objEllieMaeActions.pressKey(searchtext, Keys.ARROW_DOWN);
		ALRGLogger.log(_log, "perform search using Enter key.'", EllieMaeLogLevel.reporter);
		objEllieMaeActions.pressKey(searchtext, Keys.ENTER);
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		ALRGLogger.log(_log, "search results for "+ objEllieMaeActions.getText(resultdisplaytxt), EllieMaeLogLevel.reporter);
		
	}	
	
	/**
	 * <b>Name:</b> selectSearchText<br>
	 * <b>Description:</b> This method clicks on search text box and selects option(provided in test data) from type ahead options.
	 * 
	 * <b>@author: Archana Joshi</b>
	 * @param TestData
	 */	
	public boolean selectSearchText(HashMap<String, String> testData) {

		String textToSearch = testData.get("TextToSearch");
		objEllieMaeActions.alrg_simlyType(searchtext, textToSearch, "Search Text box");
		objEllieMaeActions.explicitWait(typeaheadSearchOptions, 10);
		return objEllieMaeActions.alrg_selectAutoCompleteOptionWithText(SEARCHRESULTConsts.AUTOSELECT_OPTIONS,testData.get("TextToVerify").toLowerCase());

	}

	/**
	 * <b>Name:</b> selectTextUsingActions<br>
	 * <b>Description:</b> This method clicks on search text box and selects option(provided in test data) suing mouse actions.
	 * 
	 * <b>@author: Archana Joshi</b>
	 * @param TestData
	 */	
	public boolean selectTextUsingActions()
	{
		//String textToSearch = testData.get("TextToSearch");
		//objEllieMaeActions.alrg_simlyType(searchtext, textToSearch, "Search Text box");
		return objEllieMaeActions.alrg_selectElementUsingActions(typeaheadSearchOptions, "search textbox");
	}
	
	/**
	 * <b>Name:</b> isTypeAheadDisplayed<br>
	 * <b>Description:</b> This method verifies if Type ahead options are displayed or not.
	 * <b>@author:Archana Joshi</b>
 	 */
	public boolean isTypeAheadDisplayed() {

		objEllieMaeActions.explicitWait(typeaheadSearchOptions, 60);
		return objEllieMaeActions.alrg_isTypeAheadDisplayed(SEARCHRESULTConsts.AUTOSELECT_OPTIONS);
	}

	/**
	 * <b>Name:</b> setSearchText<br>
	 * <b>Description:</b> set text to be searched from testData. <b>@author:
	 * Archana Joshi</b>
	 * 
	 * @param TestData
	 */
	public void setSearchText(HashMap<String, String> testData) {
		String textToSearch = testData.get("TextToSearch");
		objEllieMaeActions.alrg_simlyType(searchtext, textToSearch, "Search Text box");
	}
	
	/**
	* <b>Name:</b> isSearchResultDisplayed<br>
	 * <b>Description:</b> This method checks if search results are displayed
	 * only for search text entered.
	 * 
	 * @param expectedText  --search text entered by user.
	 * <b>@author:Archana Joshi</b>
	 * 
	 */
	public boolean isSearchResultDisplayed(String expectedText) {
		boolean leftPanelStatus=false,resultTextStatus=false;
		
		leftPanelStatus=objEllieMaeActions.alrg_isElementDisplayed(searchleftpanelfilter);
		resultTextStatus=objEllieMaeActions.alrg_isTextPresent(resultdisplaytxt,expectedText);
		
		if(leftPanelStatus && resultTextStatus )
			return true;
		else 
			return false;		
		
	}

	/**
	 * <b>Name:</b> isPageControlDisplayed<br>
	 * <b>Description:</b> This method verifies if pagination control is displayed or not.
	 * <b>@author:Archana Joshi</b>
	 */
	public boolean isPageControlDisplayed() {
		return objEllieMaeActions.alrg_isElementDisplayed(pagecontrol);
	}
	
	/**
	* <b>Name:</b> clickKeywordLink<br>
	 * <b>Description:</b> This method clicks on first keyword from filtered contents 
	 * returned by search results.
	 * <b>@author:Archana Joshi</b>
	 * 
	 */
	public void clickKeywordLink() {

		ALRGLogger.log(_log, "Click on keyword link from filtered content.", EllieMaeLogLevel.reporter);
		//--TODO: check if element desc can be fetched in actions class?
		objEllieMaeActions.alrg_clickElement(keywordlink, objEllieMaeActions.alrg_getText(keywordlink)); 
		
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		objEllieMaeActions.alrg_waitForSpinnerToBeGone();		
	}	

	/**
	* <b>Name:</b> clickKeywordSearchCheckBox<br>
	 * <b>Description:</b> This method selects one of the check box from available list.
	 * <b>@author:Archana Joshi</b>
	 * 
	 */
	public void clickKeywordCheckBox() {
	
		ALRGLogger.log(_log, "Click on first checkbox option from list of check boxes  displayed.", EllieMaeLogLevel.reporter);
		objEllieMaeActions.alrg_selectCheckBoxFromList(keywordlink, "Keyword Check box link.");
		objEllieMaeActions.waitForPageToLoad(FrameworkConsts.PAGETOLOAD);
		objEllieMaeActions.alrg_waitForSpinnerToBeGone();
	
	}	

	/**
	* <b>Name:</b> isKeyWordSearchResultDisplayed<br>
	 * <b>Description:</b> This method checks if no of search results change or not, upon check box selection.
	 * 
	 *  <b>@author:Archana Joshi</b>
	 */
	public boolean isKeyWordSearchResultDisplayed() {
		boolean status=false;
		String searchResutCountTxt, keywordSearchResultCountTxt;
		searchResutCountTxt=objEllieMaeActions.alrg_getText(searchresultcount);
		if (Integer.parseInt(searchResutCountTxt)>0)
		{
			clickKeywordLink();
			
			clickKeywordCheckBox();

			// fetch result count again after keyword check box selection
			keywordSearchResultCountTxt = objEllieMaeActions.alrg_getText(searchresultcount);

			// after keyword check box selection check if result count has
			// decreased or not.
			if (Integer.parseInt(keywordSearchResultCountTxt)<=Integer.parseInt(searchResutCountTxt))
				status = true;
			else
				status = false;
		}else
		{
			status=false;
		}
		return status;
	}
	
	/**
	* <b>Name:</b> getSearchLabelTxt<br>
	 * <b>Description:</b> This method will fetch text for which, search results are displayed.
	 *  <b>@author: Archana Joshi</b>
	 */
	public String getResultDisplayText() {
		return objEllieMaeActions.alrg_getText(resultdisplaytxt);
	}
	
	/**
	* <b>Name:</b> getSearchResultCount<br>
	 * <b>Description:</b> This method will get no of search result displayed.
	 *  <b>@author: Archana Joshi</b>
	 */
	public String getSearchResultCount() {
		return objEllieMaeActions.alrg_getText(searchresultcount);
	}

}
	
