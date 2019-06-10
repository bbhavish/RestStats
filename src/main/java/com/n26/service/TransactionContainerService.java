package com.n26.service;

import com.n26.entity.TransactionEntity;
import com.n26.exception.TransactionRangeException;
import com.n26.model.Transaction;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionContainerService {

  @Value("${time.mills.max}")
  Integer maxTimeMillsToKeep;

  @Value("${time.mills.interval}")
  Integer timeMillsInterval;

  TransactionEntity[] transactionEntities;

  @PostConstruct
  private void postConstruct() {
    if (maxTimeMillsToKeep <= 0 || timeMillsInterval <= 0) {
      throw new IllegalArgumentException(
          "YML is missing valid positive values for time.mills.max or time.mills.interval");
    }

    this.transactionEntities = new TransactionEntity[maxTimeMillsToKeep / timeMillsInterval];

    initAggregator();

    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  private void initAggregator() {
    //fill transactionStatistics with empty statistics aggregators
    for (int init = 0; init < transactionEntities.length; init++) {
      transactionEntities[init] = new TransactionEntity();
    }
  }


  public void addTransaction(Transaction transaction, long currentTimestamp)
      throws TransactionRangeException {
    if (!isTransactionValid(transaction.getTimestamp(), currentTimestamp)) {
      throw new TransactionRangeException();
    }
    mergeTransactions(transaction, currentTimestamp);
  }

  public List<TransactionEntity> getValidTransactions(long currentTimestamp) {
    return Arrays.stream(transactionEntities)
        .filter(te -> isTransactionValid(te.getTimestamp(), currentTimestamp))
        .collect(Collectors.toList());
  }

  public void clear() {
    initAggregator();
  }

  private void mergeTransactions(Transaction transaction, long currentTimestamp) {
    // get the index of transaction
    Long transactionTime = transaction.getTimestamp();
    Long currentTime = Instant.now().toEpochMilli();
    Integer index =
        (int) ((currentTime - transactionTime) / timeMillsInterval) % (maxTimeMillsToKeep
            / timeMillsInterval);

    if (index > 0) {
      TransactionEntity transactionEntity = this.transactionEntities[index];

      try {
        transactionEntity.getLock().writeLock().lock();

        if (transactionEntity.isEmpty()) {
          transactionEntity.create(transaction);
        } else {
          if (isTransactionValid(transactionEntity.getTimestamp(), currentTimestamp)) {
            transactionEntity.merge(transaction);
          } else {
            transactionEntity.reset();
            transactionEntity.create(transaction);
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage());
      } finally {
        transactionEntity.getLock().writeLock().unlock();
      }
    } else {
      log.error("Invalid index : {}, {}", transaction.getTimestamp(), currentTime);
    }
  }

  private boolean isTransactionValid(long txnTimeStamp, long currentTimestamp) {
    return txnTimeStamp >= currentTimestamp - maxTimeMillsToKeep;
  }


}
