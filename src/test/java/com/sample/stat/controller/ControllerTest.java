package com.sample.stat.controller;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sample.stat.RestStatsApplicationTests;
import com.sample.stat.model.TransactionRecord;
import com.sample.stat.model.TransactionStatus;
import com.sample.stat.service.StatService;
import com.sample.stat.util.StatResourceException;

public class ControllerTest extends RestStatsApplicationTests {

	private MockMvc mockMvcTransaction;
	
	private MockMvc mockMvcStatistics;
	
	@InjectMocks
	StatusController statusController;

	@InjectMocks
	TransactionController transactionController;

	@Mock
	StatService statService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvcTransaction = MockMvcBuilders.standaloneSetup(transactionController).build();
		this.mockMvcStatistics = MockMvcBuilders.standaloneSetup(statusController).build();
	}

	@After
	public void validate() {
		validateMockitoUsage();
	}

	@Test
	public void updateTransactionsTest1() {
		TransactionRecord tr = new TransactionRecord();
		tr.setAmount(10.0d);
		long currentTimeMillis = System.currentTimeMillis();
		tr.setTimestamp(currentTimeMillis);

		String request = "{ \"amount\" : 10.0, \"timestamp\": " + currentTimeMillis + "}";
		try {
			mockMvcTransaction.perform(post("/transactions").accept(MediaType.APPLICATION_JSON_VALUE)
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(request)).andExpect(status().isAccepted())
					.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateTransactionsTest2() {
		TransactionRecord tr = new TransactionRecord();
		tr.setAmount(10.0d);
		long secondsInMilliSeconds = MILLISECONDS.convert(70, SECONDS);
		long currentTimeMillis = System.currentTimeMillis() - secondsInMilliSeconds;
		tr.setTimestamp(currentTimeMillis);

		try {
			transactionController.updateTransactions(tr);
		} catch (StatResourceException e) {
			assertEquals(e.getHttpStatus(), HttpStatus.NO_CONTENT);
		}

	}

	@Test
	public void fetchStats() {
		TransactionStatus mockedTs = new TransactionStatus();
		mockedTs.setAvg(10d);
		mockedTs.setCount(10l);
		mockedTs.setMax(10d);
		mockedTs.setMin(10d);
		mockedTs.setSum(100d);

		when(statService.getStatistics()).thenReturn(mockedTs);

		try {
			MvcResult mvcResult = mockMvcStatistics
					.perform(get("/statistics").accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().is2xxSuccessful()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
