package com.elliemae.alrg.mortgagementor;

import com.elliemae.alrg.actions.ALRGApplicationActions;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> MortgageMentorPage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractMortgageMentorPage implements IMortgageMentorPage {

	public static Logger _log = Logger.getLogger(AbstractMortgageMentorPage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractMortgageMentorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

}
