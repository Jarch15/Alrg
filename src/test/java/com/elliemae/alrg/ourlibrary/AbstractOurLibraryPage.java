package com.elliemae.alrg.ourlibrary;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> OurLibraryPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractOurLibraryPage implements IOurLibraryPage {

	public static Logger _log = Logger.getLogger(AbstractOurLibraryPage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractOurLibraryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

}