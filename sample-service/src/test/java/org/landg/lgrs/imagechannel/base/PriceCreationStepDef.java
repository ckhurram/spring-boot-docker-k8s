package org.landg.lgrs.imagechannel.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.landg.lgrs.imagechannel.ImageChannelTestHelper;
import org.landg.lgrs.imagechannel.common.ImageChannelHttpClient;
import org.landg.lgrs.imagechannel.dto.ImageChannelResponseDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Scope(SCOPE_CUCUMBER_GLUE)
@ContextConfiguration
public class PriceCreationStepDef {

    @Autowired
    ImageChannelHttpClient imageChannelHttpClient;


    ResponseEntity<ImageChannelResponseDTO> response;

    private final ObjectMapper om = new ObjectMapper();



    @And("price creation data store has prices for vehicles")
    public void priceCreationDataStoreHasPricesForVehicles() throws JsonProcessingException {

    }


    @When("a GET call is made to get All prices with (.*?) and (.*?)$")
    public void aGETCallIsMadeToGetAllPricesWithModelYearAndMarket(String modelYear, String market) {
        this.response = this.imageChannelHttpClient.callGetAllPrices(modelYear, market);
    }

    @When("a GET call is made to get a single price with (.*?) and (.*?) and (.*?)$")
    public void aGETCallIsMadeToGetASinglePriceWithPnoAndModelYearAndMarket(String pno12,
                                                                            String modelYear,
                                                                            String market) {
        this.response = this.imageChannelHttpClient.callSinglePrice(pno12, modelYear, market);
    }


    @Then("a response with (\\d+) and (.*?) is received$")
    public void aResponseWithResponse_codeAndResponse_jsonIsReceived(int responseCode, String responseJsonFile) throws JsonProcessingException, JSONException {
        assertEquals(responseCode, response.getStatusCodeValue());
        String expectedJson = new String(ImageChannelTestHelper.readFileIntoByteArray(responseJsonFile), StandardCharsets.UTF_8);

        String actualJson = om.writeValueAsString(Objects.requireNonNull(response.getBody()));

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);


    }


    @When("a POST call is made to update a single price with (.*?)$")
    public void aPOSTCallIsMadeToUpdateASinglePriceWithRequest_body(String requestFileName) {
        this.response = this.imageChannelHttpClient.callPriceCreationPostEndpoint(requestFileName, "/updatePrice");

    }
}
