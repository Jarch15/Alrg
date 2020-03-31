package com.elliemae.alrg.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class ALRGDesktopApplicationActions extends ALRGApplicationActions {

    public static Logger _log = Logger.getLogger(ALRGDesktopApplicationActions.class);
    
	public ALRGDesktopApplicationActions(WebDriver driver) {
		super(driver); 
	}

	@Override
	public void alrg_waitForSpinnerToBeGone(int nSecs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean alrg_waitForCalcSpinnerToBeGone() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
