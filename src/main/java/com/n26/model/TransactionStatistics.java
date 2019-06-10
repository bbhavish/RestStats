package com.n26.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Data;

@Data
public class TransactionStatistics {

  private BigDecimal sum, avg, max, min;

  private long count;

  public TransactionStatistics() {
    reset();
  }

  public void reset() {
    this.sum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    this.avg = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    this.max = new BigDecimal(Long.MIN_VALUE).setScale(2, RoundingMode.HALF_UP);
    this.min = new BigDecimal(Long.MAX_VALUE).setScale(2, RoundingMode.HALF_UP);
    this.count = 0;
  }

  public BigDecimal getSum() {
    return sum;
  }

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getMax() {
    return max;
  }

  public BigDecimal getMin() {
    return min;
  }
}
