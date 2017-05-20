package com.sample.stat.simulate;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CallStatisticsProgram {


	public static void main(String[] args) throws Exception {
		
		long tenMinutesInMilliSeconds = MILLISECONDS.convert(10, TimeUnit.MINUTES);
		long ten = System.currentTimeMillis()+tenMinutesInMilliSeconds;
		while (System.currentTimeMillis() < ten) {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/statistics", String.class);
			System.out.println(responseEntity.getStatusCodeValue());
			Thread.sleep(100);
		}
	}
}
