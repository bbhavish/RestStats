package com.sample.stat.service;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sample.stat.model.TransactionRecord;
import com.sample.stat.model.TransactionStatus;

@Service
public class StatService {

	private static Logger logger = LoggerFactory.getLogger(StatService.class);

	private List<TransactionRecord> storage = new ArrayList<>();
	private TransactionStatus ts = new TransactionStatus();

	public void saveTransactionRecord(TransactionRecord tr) {
		synchronized (storage) {
			storage.add(tr);
		}
	}

	public TransactionStatus getStatistics() {
		synchronized (ts) {
			return ts;
		}
	}

	/**
	 * Pre compute the Statistics of every minute, so that GET calls will always
	 * respond consistently irrespective of the records.
	 * 
	 */
	public void refreshStatistics() {
		logger.info("Refreshing storage and stats");
		Double sum = 0d;
		Double avg = 0d;
		Double max = 0d;
		Double min = 0d;
		Long count = 0l;

		synchronized (storage) {
			logger.info("Computing for record size : {}", storage.size());
			long sixtySecondsInMilliSeconds = MILLISECONDS.convert(60, SECONDS);
			long initTimeMillis = System.currentTimeMillis() - sixtySecondsInMilliSeconds;
			logger.info("Filtering based on time : {}" + new Date(initTimeMillis));

			DoubleSummaryStatistics summaryStatistics = storage.stream()
					.filter(tr -> tr.getTimestamp() >= initTimeMillis).mapToDouble(TransactionRecord::getAmount)
					.summaryStatistics();
			if(summaryStatistics.getCount() > 0) {
				sum = summaryStatistics.getSum();
				avg = summaryStatistics.getAverage();
				max = summaryStatistics.getMax();
				min = summaryStatistics.getMin();
				count = summaryStatistics.getCount();
			}
			storage.clear();
		}
		synchronized (ts) {
			ts = new TransactionStatus();
			ts.setAvg(avg);
			ts.setCount(count);
			ts.setMax(max);
			ts.setMin(min);
			ts.setSum(sum);
		}
	}
}
