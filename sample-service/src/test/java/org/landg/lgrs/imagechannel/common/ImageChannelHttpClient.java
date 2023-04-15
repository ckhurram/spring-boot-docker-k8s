package org.landg.lgrs.imagechannel.common;

import org.landg.lgrs.imagechannel.ImageChannelTestHelper;
import org.landg.lgrs.imagechannel.dto.ImageChannelResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@ContextConfiguration
public class ImageChannelHttpClient {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;


    private String priceCreationRestEndpoint(String endpoint) {
        String PRICE_CREATION_BASE_ENDPOINT = "/ss/api/v1/price-creation";
        String SERVER_URL = "http://localhost";
        return SERVER_URL + ":" + port + PRICE_CREATION_BASE_ENDPOINT + endpoint;
    }


    public ResponseEntity<ImageChannelResponseDTO> callGetAllPrices(String modelYear, String market) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> reqEntity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(this.priceCreationRestEndpoint("/prices"))
                .queryParam("modelYear", "{modelYear}")
                .queryParam("market", "{market}")
                .encode()
                .toUriString();

        Map<String, Object> params = new HashMap<>();
        params.put("market", market);
        params.put("modelYear", modelYear);

        try {
            return restTemplate.exchange(urlTemplate,
                    HttpMethod.GET,
                    reqEntity,
                    ImageChannelResponseDTO.class, params);
        } catch(HttpStatusCodeException e) {
            var response = ImageChannelResponseDTO.builder().message("No Data Found").build();
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(response);
        }
    }

    public ResponseEntity<ImageChannelResponseDTO> callSinglePrice(String pno12, String modelYear, String market) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> reqEntity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(this.priceCreationRestEndpoint("/price"))
                .queryParam("modelYear", "{modelYear}")
                .queryParam("market", "{market}")
                .queryParam("pno12", "{pno12}")
                .encode()
                .toUriString();

        Map<String, Object> params = new HashMap<>();
        params.put("market", market);
        params.put("modelYear", modelYear);
        params.put("pno12", pno12);

        try {
            return restTemplate.exchange(urlTemplate,
                    HttpMethod.GET,
                    reqEntity,
                    ImageChannelResponseDTO.class, params);
        } catch(HttpStatusCodeException e) {
            var response = ImageChannelResponseDTO.builder().message("No Data Found").build();
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(response);
        }
    }



    public ResponseEntity<ImageChannelResponseDTO> callPriceCreationPostEndpoint(String updatePriceJsonFileName, String endPoint) {
        String requestBody = new String(ImageChannelTestHelper.readFileIntoByteArray(updatePriceJsonFileName), StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> reqEntity = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.exchange(priceCreationRestEndpoint(endPoint),
                    HttpMethod.POST,
                    reqEntity,
                    ImageChannelResponseDTO.class);
        } catch(HttpStatusCodeException e) {
            var response = ImageChannelResponseDTO.builder().message("No Data Found").build();
            return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(response);
        }
    }
}
