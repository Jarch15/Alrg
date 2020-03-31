package com.elliemae.alrg.investorlibrary;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> InverterLibraryPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */

public abstract class AbstractInverterLibraryPage implements IInvestorLibraryPage{

	public static Logger _log = Logger.getLogger(AbstractInverterLibraryPage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractInverterLibraryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
}
