package com.elliemae.alrg.consts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MANAGEUSERSConsts {
	
	 public static final String ADMINISTRATION_TASKS_LINK = "//*[@id='UserMenuOptionAdmin']/a/label";

	 public static final String COLUMN_HEADER_ACTIVE ="//*[@id='usergroup-list-div']/div/div[1]/div[1]/div/div[2]";
	 public static final String COLUMN_HEADER_FIRSTNAME ="//*[@id='usergroup-list-div']/div/div[1]/div[2]/label";
	 public static final String COLUMN_HEADER_LASTNAME ="//*[@id='usergroup-list-div']/div/div[1]/div[3]/label";
	 public static final String COLUMN_HEADER_EMAIL ="//*[@id='usergroup-list-div']/div/div[1]/div[4]/label";
	 public static final String COLUMN_HEADER_USERGROUP ="//*[@id='usergroup-list-div']/div/div[1]/div[5]/label";
	 public static final String COLUMN_HEADER_ADMINROLE ="//*[@id='usergroup-list-div']/div/div[1]/div[6]/label";
	 public static final String COLUMN_HEADER_EDIT ="//*[@id='usergroup-list-div']/div/div[1]/div[7]";
	 
	 public static final String ACTIVATE_BTN ="//*[@id='btnUser_Activate']";
	 public static final String DEACTIVATE_BTN ="//*[@id='btnUser_Deactivate']";
	 public static final String CHANGE_GROUP_BTN ="//*[@id='btnUser_ChangeGroup']";
	 
	 public static final String SELECT_USER_CHK_BOX ="*//input[@class='checkBoxUserFilter']";

	 public static final String ADMINROLE_SORT_ARROW ="//*[@id='usergroup-list-div']/div/div[1]/div[6]/span";
	 public static final String USERGROUP_SORT_ARROW ="//*[@id='usergroup-list-div']/div/div[1]/div[5]/span";
	 public static final String EMAIL_SORT_ARROW ="//*[@id='usergroup-list-div']/div/div[1]/div[4]/span";
	 public static final String LASTNAME_SORT_ARROW ="//*[@id='usergroup-list-div']/div/div[1]/div[3]/span";
	 public static final String FIRSTNAME_SORT_ARROW ="//*[@id='usergroup-list-div']/div/div[1]/div[2]/span";
	 public static final String ACTIVE_SORT_ARROW ="//*[@id='usergroup-list-div']/div/div[1]/div[1]/span";

	 
	 public static final String SEARCH_USER_TXTBOX ="//*[@id='searchUserInputText']";
	 public static final String SEARCH_REMOVE_ICON ="//*[@id='search-user']";
	 
	 public static final String ROW_USER_DATA_CONTENT="//*[@id='usergroup-list-div']/div/div[2]";
	 public static final String ROW_FIRST_NAME="//*[@id='usergroup-list-div']/div/div[2]/div[1]/div[2]";
	 public static final String ROW_LAST_NAME="//*[@id='usergroup-list-div']/div/div[2]/div[1]/div[3]";
	 public static final String ROW_EMAIL="//*[@id='usergroup-list-div']/div/div[2]/div[1]/div[4]";
	 public static final String ROW_USER_EDIT_ICON="//*[@id='usergroup-list-div']/div/div[2]/div[1]/div[7]/span";
	 
	 public static final String FIRSTNAME_TXTBOX="//*[@id='firstNameTxt']";
	 public static final String LASTNAME_TXTBOX="//*[@id='lastNameTxt']";
	 public static final String EMAIL_TXTBOX="//*[@id='emailTxt']";
	 public static final String VERIFY_EMAIL_TXTBOX="//*[@id='cEmailTxt']";
	 public static final String USER_CATEGORY="//*[@id='User_Category']";
	 public static final String USER_GROUP="//*[@id='User_UserGroupID']";
	 public static final String USER_RESPONSIBILITY="//*[@id='User_Responsibility']";
	 public static final String USER_ADMINISTRATOR="//*[@id='IsNotAdministrator']";
	 public static final String USER_NOTES_ADMINISTRATOR="//*[@id='IsNotesAdministrator']";
	 
	//*[@id="addEditUser"]/form/div[7]/div[1]/div
	
	
	 
	 public static final String UPDATE_BTN="//*[@id='updateUserBtn']";
	 public static final String RESET_PASSWORD_BTN="//*[@id='resetPasswordBtn']";
	 public static final String SUCCESS_MSG_CONTAINER="//*[@id='successMsgContainer']";
	 public static final String ERROR_MSG_CONTAINER="//*[@id='errorMsgContainer']";
	 
	 public static final String SUCCESS_MSG_TXT= "User updated successfully!";
	
	 public static final String ADD_USER_BTN= "//*[@id='btnUser_AddUser']";
	 public static final String ADD_EDIT_USER_TITLE="//*[@id='addEditUser']/form/div[1]/div[1]/h3";
	 public static final String SAVE_USER_BTN="//*[@id='addUserBtn']";
	 public static final String IMPORT_USER_LINK="//*[@id='importUsers']";
	 public static final String CHOOSE_FILE_USER_UPLOAD_BTN="//*[@id='importUsersUpload']";
	 public static final String IMPORT_USER_UPLOAD_BTN="//*[@id='uploadUsersBtn']";
	 public static final String SEND_EMAIL_CHKBOX="//*[@id='sendEmailChkBox']";
	 public static final String IMPORT_USER_RESULT_MSG="//*[@id='importUsersResult']";
	
	 public static final String FILE_UPLOAD_TXT="//*[@id='fileUploadTxt']";

	 public static final String IMPORT_ERROR_MSG_TEXT="Another user import is being processed";

	 ///html/body/div[8]/div/div
	 public static final String PASSWORD_RESET_OK_BUTTON=" /html/body/div[8]/div/div/div[2]/button";
	 
	 
	
	
	
	 
	


	

	
	 
	
	 
	 
	
	
	


}