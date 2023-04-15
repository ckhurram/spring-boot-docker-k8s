Feature: As a Price creation analyst
  I want to be able to see all prices which are available to me
  So that I can review those prices and adjust them if required

  Scenario Outline: Get All prices from the back-end service <scenario>
    Given the price calculation service is up and running
    And price creation data store has prices for vehicles
    When a GET call is made to get All prices with <modelYear> and <market>
    Then a response with <response_code> and <response_json> is received
    Examples:
      | scenario       | market | modelYear | response_code | response_json            |
      | For All prices | SE     | 2023      | 200           | all_prices_response.json |