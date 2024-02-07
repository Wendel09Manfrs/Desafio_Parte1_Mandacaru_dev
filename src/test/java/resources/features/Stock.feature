
  @User_adding_a_product
  Scenario: Adding a stock in my home_broker
    Given The home broker has fewer than 1 stocks
    And The home broker has fewer than 2 stocks
    When The user is going to add a stock to the home broker
    Then The stock must be added successfully
