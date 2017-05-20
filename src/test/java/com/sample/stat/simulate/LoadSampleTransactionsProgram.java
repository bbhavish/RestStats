package com.sample.stat.simulate;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LoadSampleTransactionsProgram {

	private static Random rnd = new Random();
	static HttpHeaders headers = new HttpHeaders() {
		public void setAccept(java.util.List<MediaType> acceptableMediaTypes) {
			Arrays.asList(MediaType.APPLICATION_JSON_VALUE);
		};

		public void setContentType(MediaType mediaType) {
			Arrays.asList(MediaType.APPLICATION_JSON_VALUE);
		};
	};

	public static void main(String[] args) throws Exception {
		
		long tenMinutesInMilliSeconds = MILLISECONDS.convert(10, TimeUnit.MINUTES);
		long ten = System.currentTimeMillis()+tenMinutesInMilliSeconds;
		while (System.currentTimeMillis() < ten) {
			Calendar calendar = Calendar.getInstance();
			double amt = rnd.nextDouble() + 100;

			RestTemplate restTemplate = new RestTemplate();
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("amount", amt);
			jsonMap.put("timestamp", calendar.getTime());
			HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(jsonMap, headers);
			ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8080/transactions", HttpMethod.POST,
					httpEntity, String.class);
			System.out.println(responseEntity.getStatusCodeValue());
			Thread.sleep(100);
		}
	}
}
