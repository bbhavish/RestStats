package com.sample.stat.service;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sample.stat.RestStatsApplicationTests;
import com.sample.stat.model.TransactionRecord;
import com.sample.stat.model.TransactionStatus;

public class StatServiceTest extends RestStatsApplicationTests {

	@Autowired
	StatService statService;

	@Test
	public void testCompleteFlow() {
		TransactionRecord tr = new TransactionRecord();

		for (int i = 0; i < 10; i++) {
			tr = new TransactionRecord();
			tr.setAmount(10.0d);
			tr.setTimestamp(System.currentTimeMillis());
			statService.saveTransactionRecord(tr);
		}

		statService.refreshStatistics();

		TransactionStatus statistics = statService.getStatistics();
		assertEquals(10, statistics.getCount().longValue());
	}

	@Test
	public void testInvalidTimestamps() {
		TransactionRecord tr = new TransactionRecord();
		long seventySecondsInMilliSeconds = MILLISECONDS.convert(70, SECONDS);
		for (int i = 0; i < 10; i++) {
			tr = new TransactionRecord();
			tr.setAmount(10.0d);
			tr.setTimestamp(System.currentTimeMillis()-seventySecondsInMilliSeconds);
			statService.saveTransactionRecord(tr);
		}

		statService.refreshStatistics();

		TransactionStatus statistics = statService.getStatistics();
		assertEquals(0, statistics.getCount().longValue());
	}
}
