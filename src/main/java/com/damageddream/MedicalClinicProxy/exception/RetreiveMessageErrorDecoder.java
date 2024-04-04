package com.damageddream.MedicalClinicProxy.exception;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);

        switch (response.status()) {
            case 500:
                return new BadRequestException(
                        feignException.getMessage() != null ?
                                feignException.getMessage() : "Internal server error");
            case 503:
                return new BadRequestException(
                        feignException.getMessage() != null?
                                feignException.getMessage() : "Service unavailable");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
