package com.elliemae.alrg.useraccount;

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
public abstract class AbstractUserAccount implements IUserAccount {

	public static Logger _log = Logger.getLogger(AbstractUserAccount.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractUserAccount(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

}