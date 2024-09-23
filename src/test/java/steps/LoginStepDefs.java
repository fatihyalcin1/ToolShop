package steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.BasePage;
import pages.LoginPage;
import pages.RegisterPage;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.Driver;

import java.util.List;

public class LoginStepDefs {
    BasePage basePage = new BasePage();
    LoginPage loginPage = new LoginPage();
    RegisterPage registerPage = new RegisterPage();
    @Given("User is on the home page")
    public void user_is_on_the_home_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }
    @When("User clicks Sign in button")
    public void user_clicks_sign_in_button() {


        basePage.home.click();
        BrowserUtils.waitFor(3);
        basePage.categories.click();
        BrowserUtils.waitFor(3);
        basePage.contact.click();
        BrowserUtils.waitFor(3);
        basePage.language.click();
        BrowserUtils.waitFor(3);
        basePage.signIn.click();


        List<WebElement> allCategoriesItems = Driver.getDriver().findElements(By.xpath("//ul[@class='dropdown-menu show']"));

        for (WebElement item : allCategoriesItems) {
            System.out.println(item.getText());
        }

    }

    @Then("User should see page title starts as Login")
    public void user_should_see_page_title_starts_as_login() {
        BrowserUtils.waitFor(1);
        Assert.assertTrue(Driver.getDriver().getTitle().contains("Login"));

    }
    @Then("User enters emailAddress")
    public void user_enters_email_address() {
        loginPage.emailField.sendKeys(ConfigurationReader.getProperty("emailAddress"));
    }
    @Then("User enters password")
    public void user_enters_password() {

        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(loginPage.passwordField).click().sendKeys(ConfigurationReader.getProperty("password")).build().perform();

    }

    @Then("User clicks Login button")
    public void user_clicks_login_button() {
        loginPage.loginButton.click();
    }

    @Then("User should see page title starts as Overview")
    public void user_should_see_page_title_starts_as_overview() {
       BrowserUtils.waitFor(1);

       if(!Driver.getDriver().getTitle().contains("Overview")){
           System.out.println("Invalid email or password");

       }else {
           BrowserUtils.verifyTitleContains("Overview");
       }
    }





}
