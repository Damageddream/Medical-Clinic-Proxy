package com.damageddream.MedicalClinicProxy.errordecoder;

import com.damageddream.MedicalClinicProxy.exception.*;
import com.damageddream.MedicalClinicProxy.utils.JsonMessageExtractor;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);
        String errorBody = feignException.getMessage();
        String errorMessage = JsonMessageExtractor.extractMessage(errorBody);
        log.error("exception intercepted from medical-clinic-client");
        if(response.status() >= 500){
            return new RetryableException(
                    response.status(),
                    feignException.getMessage(),
                    response.request().httpMethod(),
                    feignException,
                    System.currentTimeMillis(),
                    response.request());
        }
            switch (response.status()) {
                case 403:
                    return new ForbiddenException(
                            errorBody != null ?
                                    errorBody : "Forbidden");
                case 404:
                    return new NotFoundException(
                            errorMessage != null ?
                                    errorMessage : "Not found");
                case 409:
                    return new ConflictException(
                            errorBody != null ?
                                    errorBody : "Conflict");
                case 500:
                    return new InternalServerErrorException("Sorry!, We have some problems and we're working on it!");
                case 503:
                    return new ServiceUnavailableException("This service is unavailable this time, try again later.");
                default:
                    return errorDecoder.decode(methodKey, response);
            }
    }
}
