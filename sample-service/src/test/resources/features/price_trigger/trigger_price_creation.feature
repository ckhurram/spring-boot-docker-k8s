Feature: As a Client of Price creation service
  I want to be able to trigger price creation process by providing PNO12, market, modelYear and discount
  So that the price calculator recommends prices for a provided PNO12

  Scenario Outline: Trigger price calculation at the back-end  <scenario>
    Given the price calculation service is up and running
    And the RV service returns as <rv_response_body> response when called with <pno12> and <market> and <duration> and <mileage> and <modelYear>
#    And the call to GraphQL to get MSRP will return <graphQL response> as response
    When a POST call is made to trigger price creation endpoint with <payload>
#    Then I should receive a <response_code> success response with body <response_json>
#    And a calculated price being stored in the Price Creation datastore
    Examples:
      | scenario      | market | payload              | pno12        | modelYear  | rv_response_body         | duration | mileage | response_code | response_json          |
      | For UK market | SE     | trigger_request.json | 256H5VC0D110 | 2023-06-28 | rv_success_response.json | 12       | 10000   | 404           | no_data_found_404.json |


