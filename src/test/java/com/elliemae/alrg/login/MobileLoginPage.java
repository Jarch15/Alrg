package com.elliemae.alrg.login;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.elliemae.alrg.actions.ALRGApplicationActionsFactory;
import com.elliemae.alrg.consts.ALRGConsts;

import com.elliemae.alrg.login.AbstractLoginPage;

/**
 * <b>Name:</b> LoginPage</br>
 * <b>Description: </b>This page object class is used to working with Login
 * page.</br>
 * <b>@author: Aditya Shrivastava</b>
 */
public class MobileLoginPage extends AbstractLoginPage {

	public static Logger _log = Logger.getLogger(MobileLoginPage.class);

	public MobileLoginPage(WebDriver driver) {
		super(driver);
		objEllieMaeActions = ALRGApplicationActionsFactory.getActions(this.driver, ALRGConsts.DEVICE_MOBILE);
	}

	/**
	 * <b>Name:</b> clickLoginBtn<br>
	 * <b>Description:</b> This method is used to click on the Login button
	 * 
	 * @param device
	 *            - it is optional parameter. If set any string, it will use
	 *            locator for a tablet
	 */
	public void clickLoginBtn() {
		// will implement once we start working with Mobile
	}

	@Override
	public boolean isForgotPasswordLinkDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNeedToRegisterDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEyeIconDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLoginUrl() {
		String url = getLoginUrl("MobileURL");
		if (!url.toLowerCase().contains("?device=mobile")) {
			url = url + "?device=mobile";
		}
		return url;
	}

	@Override
	public void clickContinueToAllRegsBtn() {
		// TODO Auto-generated method stub
		// will implement once we start working with Mobile

	}
	
	@Override
	public void clickTakeMeToHomePageBtn() {
		// TODO Auto-generated method stub
		// will implement once we start working with Mobile

	}


	@Override
	public boolean isAllRegIconDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPasswordFiledDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserIdFiledDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
