package com.damageddream.MedicalClinicProxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class MedicalClinicProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicProxyApplication.class, args);
	}

}
