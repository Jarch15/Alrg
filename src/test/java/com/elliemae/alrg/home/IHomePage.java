package com.elliemae.alrg.home;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IHomePage {
	public boolean verifyWidget(By btnLocator, By titleLocator,String elementDescription,WebElement btnElement) ;
}
