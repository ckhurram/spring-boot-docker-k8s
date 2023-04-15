package org.landg.lgrs.imagechannel.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.landg.lgrs.imagechannel.ImageChannelTestHelper;
import org.landg.lgrs.imagechannel.common.ImageChannelHttpClient;
import org.landg.lgrs.imagechannel.dto.ImageChannelResponseDTO;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Scope(SCOPE_CUCUMBER_GLUE)
@ContextConfiguration
public class TriggerPriceCreationStepDef {

    @Autowired
    ImageChannelHttpClient imageChannelHttpClient;


    WireMockServer wm;

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        wm = new WireMockServer(options().port(8090));
        wm.start();
    }

    @After
    public void teardown() {
        wm.shutdown();
    }

    @Given("the price calculation service is up and running")
    public void thePriceCalculationServiceIsUpAndRunning() {
    }

    @And("the RV service returns as (.*?) response when called with (.*?) and (.*?) and (\\d+) and (\\d+) and (.*?)$")
    public void theRVServiceReturnsAsRv_response_bodyResponseWhenCalledWithPnoAndMarketAndDurationAndMileageAndModelYear(String responseFileName,
                                                                                                                         String pno12,
                                                                                                                         String market,
                                                                                                                         int duration,
                                                                                                                         int mileage,
                                                                                                                         String modelYear) throws URISyntaxException {
        byte[] rvSuccessResponse = ImageChannelTestHelper.readFileIntoByteArray(responseFileName);
        String rvResponseJson = new String(rvSuccessResponse, StandardCharsets.UTF_8);


        wm.stubFor(get(urlPathMatching("/api/v1/residual-values?.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(rvResponseJson)));



    }

    @When("a POST call is made to trigger price creation endpoint with (.*?)$")
    public void aPOSTCallIsMadeToTriggerPriceCreationEndpointWithPayload(String payloadFileName) {
        ResponseEntity<ImageChannelResponseDTO> response = this.imageChannelHttpClient.callPriceCreationPostEndpoint(payloadFileName, "/trigger");

        assertTrue(response.getStatusCode().is2xxSuccessful());

//        wm.verify(getRequestedFor(urlPathMatching("/api/v1/residual-values?.*"))
//                .withHeader("authtoken", equalTo("residual-value-api-aE8wY7mS8xH1pY7d")));
    }

    @Then("I should receive a (\\d+) success response with body (.*?)$")
    public void iShouldReceiveASuccessResponseWithBodyResponse_json(int responseCode, String responseJsonFileName) throws JsonProcessingException, JSONException {


//        byte[] rvData = new RVTestHelper().readFileIntoByteArray(responseJsonFileName);
//
//        JSONAssert.assertEquals(new String(rvData),
//                new ObjectMapper().writeValueAsString(rvRetrievalResponseResponseEntity.getBody()),
//                JSONCompareMode.LENIENT);





    }

}
