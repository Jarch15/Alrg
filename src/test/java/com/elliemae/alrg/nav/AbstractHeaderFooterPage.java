package com.elliemae.alrg.nav;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> AbstractHeaderFooterPage</br>
 * <b>Description: </b>This page object class is for Header Footer functionality
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractHeaderFooterPage implements IHeaderFooterPage {

	public static Logger _log = Logger.getLogger(AbstractHeaderFooterPage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractHeaderFooterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @Override
    public abstract void logout();	
	
    @Override
    public abstract void logoutNoException();	
}
