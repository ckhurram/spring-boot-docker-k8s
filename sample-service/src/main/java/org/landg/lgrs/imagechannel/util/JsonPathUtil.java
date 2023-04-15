package org.landg.lgrs.imagechannel.util;


import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

public class JsonPathUtil {

    private JsonPathUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static int extractPriceFromResponse(String responseBody) {
        return JsonPath.read(responseBody, "$.data.carsByMarket.cars.groupBy[0].groupBy[0].groupBy[0].groupBy[0].groupBy[0].groupBy[0].groupBy[0].groupBy[0].first.configuration.defaultCar.priceSummary.price.amount");
    }

    public static double extractRvPercentageFromResponse(String rvResponse) {
        JSONArray jsonValue = JsonPath.read(rvResponse, "$.residualValues[0].residualValueFactors[?(@.itemType=='baseCar')].factor");
        return (Double)jsonValue.get(0);
    }

    public static String extractDescriptionFromRvResponse(String rvResponse) {
        return JsonPath.read(rvResponse, "$.residualValues[0].lookUpTableName");
    }
}
