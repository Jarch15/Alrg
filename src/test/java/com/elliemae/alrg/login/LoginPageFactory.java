package com.elliemae.alrg.login;

import com.elliemae.alrg.login.MobileLoginPage;
import com.elliemae.alrg.consts.ALRGConsts;

import org.openqa.selenium.WebDriver;

/**
 * Page factory for getting a page object for specific device 
 *<b>@author: Aditya Shrivastava</b>
 */
public class LoginPageFactory {

	public ILoginPage getLoginPageObject(WebDriver driver, String device) {
		if (ALRGConsts.DEVICE_MOBILE.equals(device))
		{
			return new MobileLoginPage(driver);
		}else
		{
			return new DesktopLoginPage(driver);
		}
	}

}
