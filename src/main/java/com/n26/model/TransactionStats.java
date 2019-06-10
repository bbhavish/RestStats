package com.n26.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Data;

@Data
public class TransactionStats {

  private String sum, avg, max, min;

  private long count;

  public TransactionStats(TransactionStatistics transactionStatistics) {
    this.sum = transactionStatistics.getSum().setScale(2, RoundingMode.HALF_UP).toString();
    this.avg = transactionStatistics.getAvg().setScale(2, RoundingMode.HALF_UP).toString();
    if (transactionStatistics.getMax()
        .equals(new BigDecimal(Long.MIN_VALUE).setScale(2, RoundingMode.HALF_UP))) {
      this.max = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).toString();
    } else {
      this.max = transactionStatistics.getMax().setScale(2, RoundingMode.HALF_UP).toString();
    }
    if (transactionStatistics.getMin()
        .equals(new BigDecimal(Long.MAX_VALUE).setScale(2, RoundingMode.HALF_UP))) {
      this.min = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).toString();
    } else {
      this.min = transactionStatistics.getMin().setScale(2, RoundingMode.HALF_UP).toString();
    }
    this.count = transactionStatistics.getCount();
  }
}
