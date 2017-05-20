package com.sample.stat.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApiSwaggerConfiguration {

	@Bean
	public Docket api() {
		  List<ResponseMessage> getResponseList = new ArrayList<>();
		  List<ResponseMessage> postResponseList = new ArrayList<>();
		  
		  postResponseList.add(new ResponseMessageBuilder().code(201).message("Created").build());
		  postResponseList.add(new ResponseMessageBuilder().code(204).message("No Content").build());
		  getResponseList.add(new ResponseMessageBuilder().code(200).message("Success response").build());
		return new Docket(DocumentationType.SWAGGER_2)
		          .select()                                  
		          .apis(RequestHandlerSelectors.basePackage("com.sample.stat.controller"))            
		          .paths(PathSelectors.any())                     
		          .build().apiInfo(metadata())
		          .useDefaultResponseMessages(false)                                   
		          .globalResponseMessage(RequestMethod.GET, getResponseList)
		          .globalResponseMessage(RequestMethod.POST, postResponseList);
		          
	}
	
	private ApiInfo metadata() {
		ApiInfo apiInfo = new ApiInfo("Statistics", "API specification for Statistics and Transactions", 
				"1.0.0", "", new Contact("Bhavish", null, "bbhavish@gmail.com"), "", "");
		return apiInfo;

	}
}
