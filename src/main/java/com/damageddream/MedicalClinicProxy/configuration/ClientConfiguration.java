package com.damageddream.MedicalClinicProxy.configuration;

import com.damageddream.MedicalClinicProxy.errordecoder.RetreiveMessageErrorDecoder;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfiguration {
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 5);
    }
}
