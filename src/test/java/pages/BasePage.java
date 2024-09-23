package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;


public class BasePage {


    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }


    @FindBy(xpath = "//a[@data-test='nav-home']")
    public WebElement home;

    @FindBy(xpath = "//a[@data-test='nav-categories']")
    public WebElement categories;

    @FindBy(xpath = "//a[@data-test='nav-contact']")
    public WebElement contact;

   @FindBy(xpath = "//a[@href='/auth/login']")
    public WebElement signIn;

    //button[@id ='language']
    @FindBy(id = "language")
    public WebElement language;

    public String newBrandText(String moduleName){

        return Driver.getDriver().findElement(By.xpath("//label[text()=' "+moduleName+"']")).getText();
    }
    public WebElement newBrand(String moduleName){

        return Driver.getDriver().findElement(By.xpath("//label[text()=' "+moduleName+"']"));
    }




}
