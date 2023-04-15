package org.landg.lgrs.imagechannel.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonPathUtilTest {

    public static final String RV_RESPONSE = "{\n" +
            "  \"message\": \"SUCCESS\",\n" +
            "  \"residualValues\": [\n" +
            "    {\n" +
            "      \"id\": \"536K7170F110-20200628-10000\",\n" +
            "      \"country\": \"SE\",\n" +
            "      \"modelYear\": \"2024\",\n" +
            "      \"pno12\": \"536K7170F110\",\n" +
            "      \"carline\": \"XC40\",\n" +
            "      \"fuel\": \"Petrol\",\n" +
            "      \"lookUpTableName\": \"XC40 Core B3p FWD AT MY23\",\n" +
            "      \"validFrom\": 20200628,\n" +
            "      \"duration\": 12,\n" +
            "      \"distance\": 10000,\n" +
            "      \"residualValueFactors\": [\n" +
            "        {\n" +
            "          \"itemType\": \"option\",\n" +
            "          \"factor\": 33.5\n" +
            "        },\n" +
            "        {\n" +
            "          \"itemType\": \"accessory\",\n" +
            "          \"factor\": 33.5\n" +
            "        },\n" +
            "        {\n" +
            "          \"itemType\": \"baseCar\",\n" +
            "          \"factor\": 67.0\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    void should_extract_price_from_gpe_response() {
        String jsonString = "{\"data\":{\"carsByMarket\":{\"cars\":{\"groupBy\":[{\"groupBy\":[{\"groupBy\":[{\"groupBy\":[{\"groupBy\":[{\"groupBy\":[{\"groupBy\":[{\"groupBy\":[{\"first\":{\"configuration\":{\"defaultCar\":{\"pno\":{\"pno34PlusOptions\":\"256H5VC0D11072800RA0000      00000000273000854001220\"},\"priceSummary\":{\"price\":{\"amount\":935000}}}}}}]}]}]}]}]}]}]}]}}}}";
        int actual = JsonPathUtil.extractPriceFromResponse(jsonString);
        assertEquals(935000, actual);
    }

    @Test
    void should_extract_rv_percentage_from_rv_response() {
        double actual = JsonPathUtil.extractRvPercentageFromResponse(RV_RESPONSE);
        assertEquals(67.0, actual);
    }

    @Test
    void should_extract_car_description_from_rv_response() {
        String actual = JsonPathUtil.extractDescriptionFromRvResponse(RV_RESPONSE);
        assertEquals("XC40 Core B3p FWD AT MY23", actual);
    }
}