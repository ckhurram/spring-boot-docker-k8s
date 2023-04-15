package org.landg.lgrs.imagechannel.util;

import java.util.Map;

public class ParsePNO12Util {

    private ParsePNO12Util() {
        throw new IllegalStateException("Utility class");
    }

    private static Map<String, String> localeMap = Map.of("SE", "sv-SE", "UK" , "en-GB", "DE", "de-DE");

    public static GpeCarKeyRequestDTO parsePNO12ToCarKey(String pno12, String market, String modelYear) {


        PriceConfigDTO priceConfigDTO = PriceConfigDTO.builder()
                .name("default")
                .locale(localeMap.get(market))
                .build();

        return GpeCarKeyRequestDTO.builder()
                .market(market)
                .modelYear(modelYear.substring(0,4))
                .carType(pno12.substring(0,3))
                .engine(pno12.substring(3,5))
                .salesVersion(pno12.substring(5,7))
                .body(pno12.substring(7,8))
                .gearbox(pno12.substring(8,9))
                .steering(pno12.substring(9,10))
                .marketingCode(pno12.substring(10,12))
                .priceConfigInput(priceConfigDTO)
                .build();

    }
}
