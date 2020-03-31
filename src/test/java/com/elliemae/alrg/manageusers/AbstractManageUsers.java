package com.elliemae.alrg.manageusers;

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
public abstract class AbstractManageUsers implements IManageUsers {

	public static Logger _log = Logger.getLogger(AbstractManageUsers.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;
		
    public AbstractManageUsers(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

}