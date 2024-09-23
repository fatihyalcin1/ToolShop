Feature: Login


  @login
  Scenario: Login
    Given User is on the home page
    When User clicks Sign in button
    Then User should see page title starts as Login
    Then User enters emailAddress
    Then User enters password
    Then User clicks Login button
    Then User should see page title starts as Overview
