package pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utilities.ConfigurationReader;
import utilities.Driver;


public class RegisterPage extends BasePage{

    @FindBy(id="first_name")
    public WebElement firstName;

    @FindBy (id="last_name")
    public WebElement lastName;

    @FindBy (id="dob")
    public WebElement dob;

    @FindBy (id="address")
    public WebElement address;

    @FindBy (id="postcode")
    public WebElement postCode;

    @FindBy(id="city")
    public WebElement city;

    @FindBy (id="state")
    public WebElement state;

    @FindBy (id="country")
    public WebElement country;

    @FindBy (id="phone")
    public WebElement phone;

    @FindBy (id="email")
    public WebElement email;

    @FindBy (id="password")
    public WebElement password;

    @FindBy (xpath = "//button[@type='submit']" )
    public WebElement register;

    @FindBy (xpath = "//div[text()='A customer with this email address already exists.']" )
    public WebElement registerErrorMsg;




        public void register(){
            Faker faker = new Faker();
            firstName.sendKeys(faker.name().firstName());
            lastName.sendKeys(faker.name().lastName());
            dob.sendKeys("01/01/1990");
            address.sendKeys(faker.address().fullAddress());
            postCode.sendKeys(faker.address().zipCode());
            city.sendKeys(faker.address().city());
            state.sendKeys(faker.address().state());
            country.sendKeys(faker.address().country());
            phone.sendKeys("01231231212");
            email.sendKeys(ConfigurationReader.getProperty("emailAddress"));

            Actions actions = new Actions(Driver.getDriver());
            actions.moveToElement(password).click().sendKeys(ConfigurationReader.getProperty("password")).build().perform();
            register.click();



        }


}
