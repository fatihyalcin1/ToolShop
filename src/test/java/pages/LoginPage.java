package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy (id="email")
    public WebElement emailField;

    @FindBy (id="password")
    public WebElement passwordField;

    @FindBy (xpath = "//input[@value='Login']")
    public WebElement loginButton;

    @FindBy (xpath = "//a[@href='/auth/register']" )
    public WebElement registerYourAccount;


}
