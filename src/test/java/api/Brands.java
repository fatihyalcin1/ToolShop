package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import pages.BasePage;
import pojo.Brand;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.Driver;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Brands {

    String url = (ConfigurationReader.getProperty("brandsApiUrl"));
    static String name;
    static String slug;
    static String brandID;
    static Brand brandPost ;
    static Brand brandPut;



    @Order(1)
    @org.junit.jupiter.api.Test
    public void GETAllBrands(){
        //send request to url and get response as Response interface
        Response response = RestAssured.get(url);

       //verify that status code is 200
        int actualStatusCode = response.statusCode();

        //assert that it is 200
        Assertions.assertEquals(200,actualStatusCode);

        //how to print json response body on console
        response.prettyPrint();

    }
    /**
     * This method sends a POST request to the brands API endpoint to create a new brand.
     * It uses the JavaFaker library to generate random names and slugs for the brand.
     * The method then verifies the response status code and extracts the brand ID from the response.
     * Finally, it prints the extracted brand ID.
     */
    @Order(2)
    @org.junit.jupiter.api.Test
    public void POST() {

        // Initialize JavaFaker for generating random names and slugs
        Faker faker = new Faker();

        // Generate random name and slug
        name = faker.name().firstName();
        slug = faker.name().lastName();

        // Create a new Brand object with the generated name and slug
        brandPost = new Brand();
        brandPost.setName(name);
        brandPost.setSlug(slug);

        // Send a POST request to the brands API endpoint with the new brand object
        // Extract the brand ID from the response and store it in the brandID variable
        brandID = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(brandPost)
                .when()
                .post(url)
                .then()
                .statusCode(201)
                .extract().jsonPath()
                .getString("id");

        // Print the extracted brand ID
        System.out.println("Extracted Brand ID: " + brandID);
    }

    /**
     * This method sends a GET request to the brands API endpoint to retrieve a specific brand by its ID.
     * It uses the RestAssured library to send the request and verify the response status code and body.
     *
     * @param brandID The ID of the brand to retrieve.
     * @return The response from the API endpoint.
     */
    @Order(3)
    @org.junit.jupiter.api.Test
    public void GET_01() {
        // Create a GET request with the specified brand ID
        Response response =
                given().accept(ContentType.JSON)
                        .pathParam("id", brandID).
                        when()
                        .get(url+"/{id}")
                        // Verify the response status code is 200
                        .then()
                        .statusCode(200)
                        // Verify the response body contains the expected brand name
                        .body("name", is(brandPost.getName()))
                        // Extract and return the response
                        .extract()
                        .response();

        // Print the response body to the console
        System.out.println("Response: " + response.asString());
    }

    /**
     * This method sends a PUT request to the brands API endpoint to update an existing brand.
     * It creates a new Brand object with updated name and slug, then sends a PUT request with the updated brand object.
     * The method verifies the response status code to ensure the update was successful.
     *
     * @param brandID The ID of the brand to update.
     * @param name The updated name of the brand.
     * @param slug The updated slug of the brand.
     * @return void (no return value)
     */
    @Order(4)
    @org.junit.jupiter.api.Test
    public void PUT() {

        // Create a new Brand object with updated name and slug
        brandPut = new Brand();
        brandPut.setName(name+"put");
        brandPut.setSlug(slug+"put");

        // Send a PUT request to the brands API endpoint with the updated brand object
        given()
                .contentType(ContentType.JSON)
                .pathParam("id", brandID)
                .body(brandPut)
                .when()
                .put(url+"/{id}")
                // Verify the response status code is 200
                .then()
                .statusCode(200);
    }

    /**
     * This method sends a GET request to the brands API endpoint to retrieve a specific brand by its ID,
     * after updating the brand with a PUT request.
     * It uses the RestAssured library to send the requests and verify the response status code and body.
     *
     * @param brandID The ID of the brand to retrieve. It is assumed that this ID corresponds to a brand that has been updated.
     * @return The response from the API endpoint.
     *
     * The method performs the following steps:
     * 1. Sends a GET request to the brands API endpoint with the specified brand ID.
     * 2. Verifies the response status code is 200.
     * 3. Verifies the response body contains the expected brand name, which is the updated name.
     * 4. Extracts and returns the response.
     * 5. Prints the response body to the console.
     * 6. Prints the updated brand name to the console.
     */
    @Order(5)
    @org.junit.jupiter.api.Test
    public void GET_02() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", brandID).
                when()
                .get(url+"/{id}").
                then()
                .statusCode(200)
                .body("name", is(brandPut.getName())).extract().response();

        System.out.println("Response: " + response.asString());
        System.out.println("Response: " + brandPut.getName());
    }

    /**
     * This test method is responsible for verifying the functionality of the UI by comparing the name of a brand
     * created and updated via the API with the name displayed on the UI.
     *
     * @order 6: This method is the sixth test to be executed in the test class, as indicated by the @Order annotation.
     *
     * @throws Exception: This method may throw an exception if any error occurs during the execution.
     */
    @Order(6)
    @org.junit.jupiter.api.Test
    public void GET_03_Ui() throws Exception {

        // Open the web application URL in the browser
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));

        // Create an instance of the BasePage class
        BasePage basePage = new BasePage();

        // Get the name of the brand created and updated via the API
        String expectedBrandPutNameApi = brandPut.getName();

        // Retrieve the name of the brand displayed on the UI
        String actualBrandPutNameUi = basePage.newBrandText(expectedBrandPutNameApi);

        // Click on the brand link on the UI
        basePage.newBrand(expectedBrandPutNameApi).click();

        // Wait for 3 seconds to ensure the page is fully loaded
        BrowserUtils.waitFor(3);

        // Assert that the name of the brand displayed on the UI matches the name of the brand created and updated via the API
        Assertions.assertEquals(expectedBrandPutNameApi, actualBrandPutNameUi);

        // Close the browser driver
        Driver.closeDriver();
    }
  /*@Order(2)
    @org.junit.jupiter.api.Test
    public void POST() {

        Faker faker = new Faker();

        name = faker.name().firstName();
        slug = faker.name().lastName();

        brandPost = new Brand();
        brandPost.setName(name);
        brandPost.setSlug(slug);

        brandID = given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(brandPost)
                .when()
                    .post(url)
                .then()
                    .statusCode(201)
                    .extract().jsonPath()
                    .getString("id");

        System.out.println("Extracted Brand ID: " + brandID);
    }*/
    /*@Order(3)
    @org.junit.jupiter.api.Test
    public void GET_01() {
        Response response =
                given().accept(ContentType.JSON)
                        .pathParam("id", brandID).
                        when()
                        .get(url+"/{id}")
                .then()
                .statusCode(200)
                .body("name", is(brandPost.getName())) // Compare with expected brand name
                .extract()
                .response();

        System.out.println("Response: " + response.asString());

    }*/
    /*@Order(4)
    @org.junit.jupiter.api.Test
    public void PUT() {

        brandPut = new Brand();
        brandPut.setName(name+"put");
        brandPut.setSlug(slug+"put");

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", brandID)
                .body(brandPut).
                when().put(url+"/{id}").
                then()
                .statusCode(200);
    }*/
    /*@Order(5)
    @org.junit.jupiter.api.Test
    public void GET_02() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", brandID).
                when()
                .get(url+"/{id}").
                then()
                .statusCode(200)
                .body("name", is(brandPut.getName())).extract().response();

        System.out.println("Response: " + response.asString());
        System.out.println("Response: " + brandPut.getName());


    }*/
    /*@Order(6)
    @org.junit.jupiter.api.Test
    public void GET_03_Ui() {

        Driver.getDriver().get(ConfigurationReader.getProperty("url"));

        BasePage basePage = new BasePage();
        String expectedBrandPutNameApi = brandPut.getName();
        String actualBrandPutNameUi = basePage.newBrandText(expectedBrandPutNameApi);

        basePage.newBrand(expectedBrandPutNameApi).click();
        BrowserUtils.waitFor(3);


        Assertions.assertEquals(expectedBrandPutNameApi, actualBrandPutNameUi);
        Driver.closeDriver();

    }*/
   /* @Order(7)
    @org.junit.jupiter.api.Test
    public void DELETE() {

        given()
                .pathParam("id", brandID)
                .when().delete(url+"/{id}")
                .then().statusCode(204);


    }
    @Order(8)
    @org.junit.jupiter.api.Test
    public void GET_03() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", brandID).
                when()
                .get(url+"/{id}").
                then()
                .statusCode(404).extract().response();

        System.out.println("Response: " + response.asString());


}*/
}