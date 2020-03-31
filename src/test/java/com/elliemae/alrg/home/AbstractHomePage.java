package com.elliemae.alrg.home;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.consts.HOMEConsts;
import com.elliemae.alrg.consts.MANAGEUSERSConsts;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * <b>Name:</b> HomePage</br>
 * <b>Description: </b>
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public abstract class AbstractHomePage implements IHomePage {

	public static Logger _log = Logger.getLogger(AbstractHomePage.class);

	protected WebDriver driver;
	protected ALRGApplicationActions objEllieMaeActions;
	protected WebDriverWait wait;

    public AbstractHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
   
    
}