package com.elliemae.alrg.login;

import java.util.HashMap;

public interface ILoginPage {


	boolean isForgotPasswordLinkDisplayed();

	boolean isNeedToRegisterDisplayed();

	boolean isEyeIconDisplayed();
	boolean isRememberMeCheckBoxPresent();
	boolean isWelcomeMessageDisplayed();

	boolean isContinueToAllRegsBtnDisplayed();
	boolean isSignUpForAlertsBtnDisplayed();
	
	boolean isSubMenuDisplayed();
	
	boolean isAllRegIconDisplayed();

	boolean isPasswordFiledDisplayed();

	boolean isUserIdFiledDisplayed();

	String getLoginUrl();

	void goToThePage(String URL);

	void login(String url, String userID, String pwd);

	void setUserID(String userID);

	void setPassword(String password);

	void login(HashMap<String, String> testData);

	String getUserName(String testDataUserName);

	String getPassword(String testDataPassword, String userIdKey);

	void clickLoginBtn();
	void clickContinueToAllRegsBtn();
	void clickTakeMeToHomePageBtn();
	
	String getLoginErrorMessage();
	
	void clickRememberMe();

	

	boolean verifyLoginErrorMessages(String string);

	
}