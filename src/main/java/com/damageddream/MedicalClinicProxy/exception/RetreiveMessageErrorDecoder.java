package com.damageddream.MedicalClinicProxy.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;

import java.io.IOException;
import java.io.InputStream;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        switch (response.status()) {
            case 400:
                return new BadRequestException(message.getMessage() != null ? message.getMessage() : "Bad Request");
            case 404:
                return new PatientNotFoundException(message.getMessage() != null ? message.getMessage() : "Not found");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }

//    @Override
//    public Exception decode(String methodKey, Response response) {
//        FeignException feignException = FeignException.errorStatus(methodKey, response);
//        String errorBody = feignException.getMessage();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode rootNode;
//        try {
//            rootNode = mapper.readTree(errorBody);
//            JsonNode messageNode = rootNode.path("message");
//            String errorMessage = messageNode.asText();
//            switch (response.status()) {
//                case 404:
//                    return new PatientNotFoundException(
//                            errorBody != null ?
//                                    errorBody : "Not found");
//                case 500:
//                    return new BadRequestException(
//                            errorMessage != null ?
//                                    errorMessage : "Internal server error");
//                case 503:
//                    return new BadRequestException(
//                            errorMessage != null ?
//                                    errorMessage : "Service unavailable");
//                default:
//                    return errorDecoder.decode(methodKey, response);
//            }
//        } catch (IOException e) {
//            return new Exception("Failed to parse error message");
//        }
//
//    }
}
