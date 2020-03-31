package com.elliemae.alrg.ealerts;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractEAlertsPage implements IEAlertsPage {

	public static Logger _log = Logger.getLogger(AbstractEAlertsPage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractEAlertsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

}