package com.elliemae.alrg.actions;

import org.openqa.selenium.WebDriver;

import com.elliemae.alrg.actions.ALRGApplicationActions;
import com.elliemae.alrg.consts.ALRGConsts;

/**
 * Page factory for getting a page object for specific device 
 *
 */
public class ALRGApplicationActionsFactory {

	public static ALRGApplicationActions getActions(WebDriver driver, String device) {
		
		if (ALRGConsts.DEVICE_MOBILE.equals(device)) {
			return new ALRGMobileApplicationActions(driver);
			
		} else if (ALRGConsts.DEVICE_WEB.equals(device)) {
			return new ALRGDesktopApplicationActions(driver);
		}
		return null;
	}

}
