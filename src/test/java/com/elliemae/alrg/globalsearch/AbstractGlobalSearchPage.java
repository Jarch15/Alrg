package com.elliemae.alrg.globalsearch;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> SearchContentPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Archana Joshi</b>
 */
public abstract class AbstractGlobalSearchPage implements IGlobalSearchPage {

	public static Logger _log = Logger.getLogger(AbstractGlobalSearchPage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
	public abstract boolean  isFiltersLinkPresent();
	public abstract boolean  isOptionsLinkPresent();
	//public abstract boolean  isClearAllLinkPresent();
	//public abstract void selectFilter(String filterText);
	
    public AbstractGlobalSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
   

}