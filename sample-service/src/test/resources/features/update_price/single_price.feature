Feature: As a Price creation analyst
  I want to be able to update the price for a pno12, market and modelYear
  So that I have the ability to override lease calculator prices

  Background:
    Given the price calculation service is up and running
    And price creation data store has prices for vehicles

  Scenario Outline: Update a price from the back-end service <scenario>
    When a POST call is made to update a single price with <request_body>
    Then a response with <response_code> and <response_json> is received
    Examples:
      | scenario          | request_body              | response_code | response_json              |
      | For Single prices | update_price_request.json | 200           | update_price_response.json |