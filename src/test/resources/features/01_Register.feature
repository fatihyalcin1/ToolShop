Feature: Register


  @register @smoke
  Scenario: Register
    Given User is on the home page
    When User clicks Sign in button
    Then User clicks Register your account
    Then User should see page title starts as Register
    Then User enters valid credentials

