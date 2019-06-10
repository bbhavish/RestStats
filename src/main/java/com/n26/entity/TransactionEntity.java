package com.n26.entity;

import com.n26.model.Transaction;
import com.n26.model.TransactionStatistics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionEntity {

  private ReadWriteLock lock;

  TransactionStatistics transactionStatistics;
  long timestamp;

  public TransactionEntity() {
    transactionStatistics = new TransactionStatistics();
    this.lock = new ReentrantReadWriteLock();
  }

  public ReadWriteLock getLock() {
    return lock;
  }

  public void create(Transaction transaction) {
    transactionStatistics.setMin(transaction.getAmount());
    transactionStatistics.setMax(transaction.getAmount());
    transactionStatistics.setCount(1);
    transactionStatistics.setAvg(transaction.getAmount());
    transactionStatistics.setSum(transaction.getAmount());
    timestamp = transaction.getTimestamp();
  }

  public void merge(Transaction transaction) {
    transactionStatistics.setSum(transactionStatistics.getSum().add(transaction.getAmount()));
    transactionStatistics.setCount(transactionStatistics.getCount() + 1);
    transactionStatistics.setAvg(
        transactionStatistics.getSum().divide(new BigDecimal(transactionStatistics.getCount())));

    if (transactionStatistics.getMin().doubleValue() > transaction.getAmount().doubleValue()) {
      transactionStatistics.setMin(transaction.getAmount());
    }
    if (transactionStatistics.getMax().doubleValue() < transaction.getAmount().doubleValue()) {
      transactionStatistics.setMax(transaction.getAmount());
    }

  }

  public void mergeToStats(TransactionStatistics transactionResults) {
    try {
      getLock().readLock().lock();
      transactionResults
          .setSum(transactionResults.getSum().add(getTransactionStatistics().getSum()));
      transactionResults
          .setCount(transactionResults.getCount() + getTransactionStatistics().getCount());
      transactionResults.setAvg(transactionResults.getSum()
          .divide(new BigDecimal(transactionResults.getCount()), 2, RoundingMode.HALF_UP));

      if (transactionResults.getMin().doubleValue() > getTransactionStatistics().getMin()
          .doubleValue()) {
        transactionResults.setMin(getTransactionStatistics().getMin());
      }
      if (transactionResults.getMax().doubleValue() < getTransactionStatistics().getMax()
          .doubleValue()) {
        transactionResults.setMax(getTransactionStatistics().getMax());
      }
    } finally {
      getLock().readLock().unlock();
    }
  }

  public boolean isEmpty() {
    return transactionStatistics.getCount() == 0;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public TransactionStatistics getTransactionStatistics() {
    return transactionStatistics;
  }

  public void reset() {
    transactionStatistics.reset();
    timestamp = 0l;
  }


}
