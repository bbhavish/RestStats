package com.sample.stat.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TransactionStatus")
public class TransactionStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name = "sum", dataType = "Double",example = "1000",notes="sum is a double specifying the total sum of transaction value in the last 60 seconds")
	private Double sum;
	@ApiModelProperty(name = "avg", dataType = "Double",example = "100",notes="avg is a double specifying the average amount of transaction value in the last 60 seconds")
	private Double avg;
	@ApiModelProperty(name = "max", dataType = "Double",example = "200",notes="max is a double specifying single highest transaction value in the last 60 seconds")
    private Double max;
	@ApiModelProperty(name = "min", dataType = "Double",example = "50",notes="min is a double specifying single lowest transaction value in the last 60 seconds")
	private Double min;
	@ApiModelProperty(name = "min", dataType = "Long",example = "10",notes="count is a long specifying the total number of transactions happened in the last 60 seconds")
	private Long count;
	/**
	 * @return the sum
	 */
	public Double getSum() {
		return sum;
	}
	/**
	 * @param sum the sum to set
	 */
	public void setSum(Double sum) {
		this.sum = sum;
	}
	/**
	 * @return the avg
	 */
	public Double getAvg() {
		return avg;
	}
	/**
	 * @param avg the avg to set
	 */
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	/**
	 * @return the max
	 */
	public Double getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Double max) {
		this.max = max;
	}
	/**
	 * @return the min
	 */
	public Double getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(Double min) {
		this.min = min;
	}
	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}
    
    
}
