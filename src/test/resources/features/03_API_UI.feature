@smoke
Feature: Brands API Management

  Scenario: Get all brands
    Given the API is up and running
    When I send a GET request to the brands endpoint
    Then I should receive a 200 status code


  Scenario: Create a new brand
    Given the API is up and running
    When I send a POST request to the brands endpoint with valid brand data
    Then I should receive a 201 status code
    And the response should contain the brand's name and slug

  Scenario: Update an existing brand
    Given a brand exists with brandId
    When I send a PUT request to the brands endpoint with updated brand data
    Then I should receive a 200 status code
    When I send a GET request to the url+brandID
    Then the response should contain the updated brand's name and slug

    Scenario: Check new brand from UI
      Given the API is up and running
      When I navigate to the home page and should see the new brand in the list

