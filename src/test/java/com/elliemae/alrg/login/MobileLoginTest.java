package com.elliemae.alrg.login;

import org.openqa.selenium.WebDriver;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;

/**
 * <b>Name:</b> LoginTest<br>
 * <b>Description:</b>This testcase class includes the following JIRA:<br>
 * 1. JIRA#: Verify user can login with valid credentials
 * <b>@author: Aditya Shrivastava</b>
 */
public class MobileLoginTest extends AbstractLoginTest
{		
	
	@Override
	public ILoginPage getLoginPage(WebDriver driver) {
		return new LoginPageFactory().getLoginPageObject(driver, ALRGConsts.DEVICE_MOBILE);
	}	

	@Override
	protected ALRGApplicationActions getActions(WebDriver driver) {
		return ALRGApplicationActionsFactory.getActions(driver, ALRGConsts.DEVICE_MOBILE);
	}
		
}
