package Tests;

import org.testng.annotations.Test;

import com.PageObjects.LoginPage;
import com.Utilities.BaseClass;


public class LoginTest extends BaseClass{
	
	@Test
	public static void DoLogin(){
		LoginPage.Login("", "");
		LoginPage.verifyErrorMessage();
	}

}
