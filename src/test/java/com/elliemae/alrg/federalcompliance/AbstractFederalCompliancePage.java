package com.elliemae.alrg.federalcompliance;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> FederalCompliancePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractFederalCompliancePage implements IFederalCompliancePage {

	public static Logger _log = Logger.getLogger(AbstractFederalCompliancePage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractFederalCompliancePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

}
