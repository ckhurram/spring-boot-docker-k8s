package org.landg.lgrs.imagechannel.client;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.nio.charset.Charset;

public class FeignClientErrorDecoder implements ErrorDecoder {
    private static final Logger LOG =  LoggerFactory.getLogger(FeignClientErrorDecoder.class);
    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        Exception defaultException = defaultDecoder.decode(methodKey, response);
        try {
            LOG.error(
                    "Got {} response from {}, response body: {}",
                    response.status(),
                    methodKey,
                    IOUtils.toString(responseBody.asReader(Charset.defaultCharset()))
            );
        }
        catch (IOException e){
            LOG.error(
                    "Got {} response from {}, response body could not be read",
                    response.status(),
                    methodKey
            );
        }
        if (responseStatus.is4xxClientError()) {
            return new RestApiClientException(
                    "Client error " + response.status() + " from calling posts api",
                    defaultException
            );
        }
        else if (responseStatus.is5xxServerError()) {
            return new RetryableException(
                    responseStatus.value(),
                    "Server error",
                    response.request().httpMethod(),
                    null,
                    response.request()
            );
        }
        return defaultException;
    }
}

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class RestApiClientException extends Exception {

    public RestApiClientException(String message, Exception exception) {
        super(message, exception);
    }
}
