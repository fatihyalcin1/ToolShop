package stepDefinitions;

import io.cucumber.java.en.Then;
import pages.LoginPage;
import pages.RegisterPage;
import utilities.BrowserUtils;

public class RegisterStepDefinitions {

    LoginPage loginPage= new LoginPage();
    RegisterPage registerPage = new RegisterPage();

    @Then("User clicks Register your account")
    public void user_clicks_register_your_account() {
        loginPage.registerYourAccount.click();

    }
    @Then("User should see page title starts as Register")
    public void user_should_see_page_title_starts_as_register() {
        BrowserUtils.waitFor(1);
        BrowserUtils.verifyTitleContains("Register");

    }
    @Then("User enters valid credentials")
    public void user_enters_valid_credentials() {

        registerPage.register();
    }


}



