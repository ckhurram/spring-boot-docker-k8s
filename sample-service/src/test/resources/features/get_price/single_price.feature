Feature: As a Price creation analyst
  I want to be able to see price for a pno12, market and modelYear
  So that I can review the single price

  Background:
    Given the price calculation service is up and running
    And price creation data store has prices for vehicles

  Scenario Outline: Get a price from the back-end service <scenario>
    When a GET call is made to get a single price with <pno12> and <modelYear> and <market>
    Then a response with <response_code> and <response_json> is received
    Examples:
      | scenario           | pno12        | market | modelYear | response_code | response_json                        |
      | For Single prices  | 256H5VC0D110 | SE     | 2023      | 200           | single_price_response.json           |
      | Price Doesnt exist | 256H5VC0D215 | SE     | 2023      | 404           | single_price_not_found_response.json |