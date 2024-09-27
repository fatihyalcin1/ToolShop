package stepDefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import pages.BasePage;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.Driver;


public class BrandsStepDefinitions {

    String url = ConfigurationReader.getProperty("brandsApiUrl");
    static String brandID;
    static String name;
    static String slug;
    static Response response;

    @Given("the API is up and running")
    public void the_api_is_up_and_running() {
        response = given().get(url);
        Assertions.assertEquals(200, response.getStatusCode(), "API is not up and running");

    }

    @When("I send a GET request to the brands endpoint")
    public void i_send_a_get_request_to_the_endpoint() {
        response = given().get(url);
       // response.prettyPrint();
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_a_status_code(int statusCode) {
        response.then().statusCode(statusCode);
        Assert.assertEquals(response.statusCode(),statusCode);
    }

    @When("I send a POST request to the brands endpoint with valid brand data")
    public void i_send_a_post_request_to_the_endpoint_with_valid_brand_data() {
        Faker faker = new Faker();
        name = faker.name().firstName();
        slug = faker.name().lastName();
        String jsonBody = "{ \"name\": \""+name+"\", \"slug\": \""+slug+"\" }";
        response.prettyPrint();
        response = given().contentType("application/json").body(jsonBody).post(url);
    response.prettyPrint();
        brandID = response
                .then()
                .statusCode(201)
                .extract().jsonPath()
                .getString("id");

        //System.out.println("brandID = " + brandID);


    }

    @Then("the response should contain the brand's name and slug")
    public void the_response_should_contain_the_brands_name_and_slug() {
        response.then().body("name", is(name)).body("slug", is(slug));
        Assertions.assertEquals(name, response.path("name"));

    }

    @Given("a brand exists with brandId")
    public void a_brand_exists_with_id() {

        // This step would verify or create a brand with the given ID
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", brandID).
                when()
                .get(url+"/{id}").
                then()
                .statusCode(200)
                .extract().response();

        Assertions.assertEquals(200, response.getStatusCode());
    }

    @When("I send a PUT request to the brands endpoint with updated brand data")
    public void i_send_a_put_request_to_the_endpoint_with_updated_brand_data() {

        // Create a new Brand object with updated name and slug

        name = name+"put";
        slug = slug+"put";


        String jsonBody = "{ \"name\": \"" + name + "\", \"slug\": \"" + slug + "\" }";
        System.out.println("inside put brandID = " + brandID);
        // Send a PUT request to the brands API endpoint with the updated brand object
       response= given()
                .contentType(ContentType.JSON)
                //.pathParam("id", brandID)
                .body(jsonBody)
                .when()
                .put(url+"/"+brandID)
                // Verify the response status code is 200
                .then()
                .statusCode(200).extract().response();



    }


    @When("I send a GET request to the url+brandID")
    public void i_send_a_get_request_to_the_url_brandID() {
        response = given().get(url+"/" + brandID);
        System.out.println("response.path(\"name\") = " + response.path("name"));
    }

    @Then("the response should contain the updated brand's name and slug")
    public void the_response_should_contain_the_updated_brands_name_and_slug() {

        String actualName = response.path("name");
        String actualSlug = response.path("slug");


        Assert.assertTrue("Name does not match!", actualName.equals(name));
        Assert.assertTrue("Slug does not match!", actualSlug.equals(slug));
    }
    @When("I navigate to the home page and should see the new brand in the list")
    public void i_navigate_to_the_brands_page_and_should_see_the_new_brand_in_the_list() {
        // Open the web application URL in the browser
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));

        // Create an instance of the BasePage class
        BasePage basePage = new BasePage();

        // Get the name of the brand created and updated via the API
        String expectedBrandPutNameApi = name;

        // Retrieve the name of the brand displayed on the UI
        String actualBrandPutNameUi = basePage.newBrandText(expectedBrandPutNameApi);

        // Click on the brand link on the UI
        basePage.newBrand(expectedBrandPutNameApi).click();

        // Wait for 3 seconds to ensure the page is fully loaded
        BrowserUtils.waitFor(3);
        System.out.println("basePage.newBrand(expectedBrandPutNameApi).getText() = " + basePage.newBrand(expectedBrandPutNameApi).getText());
        // Assert that the name of the brand displayed on the UI matches the name of the brand created and updated via the API
        Assertions.assertEquals(expectedBrandPutNameApi, actualBrandPutNameUi);


    }

}

