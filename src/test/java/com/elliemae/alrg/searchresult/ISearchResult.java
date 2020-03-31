package com.elliemae.alrg.searchresult;

import java.util.HashMap;

public interface ISearchResult {
	boolean selectSearchText(HashMap<String, String> testData );
	boolean selectTextUsingActions();
	
	public void clickSearchIcon() ;
	public void selectSearchTextUsingDownArrow(HashMap<String, String> testData);
	public boolean isTypeAheadDisplayed() ;
	
	public void setSearchText(HashMap<String, String> testData);
	public String getResultDisplayText();
	public boolean isSearchResultDisplayed(String expectedText);
	public boolean isKeyWordSearchResultDisplayed();
	public String getSearchResultCount();
	public boolean verifyNoOfSearchResults();
	public boolean isPageControlDisplayed();
	
	public boolean verifySeacrchResultsDisplay();
	public boolean isSearchTextHighlighted(String searchText);
	public boolean isTOCDisplayed();
	public boolean isBackToResultsLinkDisplayed();
	public boolean isDocumentToolBarDisplayed() ;
	
	
}
