package com.elliemae.alrg.globalsearch;

public interface IGlobalSearchPage {
		
	boolean verifySearchContentFilter(String filterName,String filtersubscription);

	void clickOnSearchLabel();

	boolean isFiltersLinkPresent();

	boolean isOptionsLinkPresent();

	void clickOnClearAllLink();
	
	boolean isFindSynonymsChkBoxChecked();
	
	boolean isMatchAllWordsOptBtnSelected();
	void closeFilterOptions();
}
