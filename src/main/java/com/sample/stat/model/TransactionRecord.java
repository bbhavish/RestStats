package com.sample.stat.model;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.http.HttpStatus;

import com.sample.stat.util.StatResourceException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TransactionRecord")
public class TransactionRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7983014487085244980L;
	@ApiModelProperty(name = "timestamp", dataType = "Long",example = "1478192204000",notes=" transaction time in epoch in millis in UTC time zone (this is not current timestamp)")
	private Long timestamp;
	@ApiModelProperty(name = "amount", dataType = "Double",example = "10.3", notes = " transaction amount")
	private Double amount;

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransactionRecord [timestamp=" + timestamp + ", " + (amount != null ? "amount=" + amount : "") + "]";
	}

	public void validate() throws StatResourceException{
		if (this.getAmount() != null && this.getTimestamp() != null) {
			Calendar instance = Calendar.getInstance();
			long secondsInMilliSeconds = MILLISECONDS.convert(60, SECONDS);
			if ((instance.getTimeInMillis() - this.getTimestamp()) >= secondsInMilliSeconds) {
				throw new StatResourceException(HttpStatus.NO_CONTENT);
			}
		} else {
			throw new StatResourceException(HttpStatus.NO_CONTENT);
		}
	}
}
