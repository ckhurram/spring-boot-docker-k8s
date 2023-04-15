package org.landg.lgrs.imagechannel.exception;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException() {

        super("No data found");
    }

    public NoDataFoundException(String pno12, String market, String modelYear) {

        super(String.format("No data found for pno12: %s, market: %s and modelYear:%s", pno12, market, modelYear));
    }
}
