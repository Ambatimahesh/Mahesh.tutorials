package com.PageObjects;

import org.openqa.selenium.By;

import com.Utilities.BaseClass;

public class LoginPage extends BaseClass{
	
	private static By getEmailTxt = By.id("login_email_id1");
	private static By getPasswordTxt = By.id("login_password1");
	private static By getLoginBtn = By.cssSelector("button.btn.btn-blue");
	private static By getErrorMsg = By.id("login_tiny_help_message1");

	public static void Login(String Email, String Password){
		textbox(driver, getEmailTxt, "enter", Email);
		textbox(driver, getPasswordTxt, "enter", Password);
		button(driver, getLoginBtn, "Click");
	}
	
	public static boolean verifyErrorMessage(){
		boolean status = elementPresent(driver, getErrorMsg);
		return status;
	}
}
