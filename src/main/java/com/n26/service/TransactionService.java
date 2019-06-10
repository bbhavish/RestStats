package com.n26.service;

import com.n26.entity.TransactionEntity;
import com.n26.exception.TransactionRangeException;
import com.n26.model.Transaction;
import com.n26.model.TransactionStatistics;
import com.n26.model.TransactionStats;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransactionService {

  @Autowired
  TransactionContainerService transactionContainerService;

  public void addTransaction(Transaction transaction) throws TransactionRangeException {
    transactionContainerService.addTransaction(transaction, Instant.now().toEpochMilli());
  }

  public TransactionStats getTransactionStats() {
    List<TransactionEntity> validTransactions = transactionContainerService
        .getValidTransactions(Instant.now().toEpochMilli());

    TransactionStatistics transactionStatistics = new TransactionStatistics();
    validTransactions.forEach(tx -> tx.mergeToStats(transactionStatistics));
    TransactionStats stats = new TransactionStats(transactionStatistics);
    log.info(stats.toString());
    return stats;

  }

  public void deleteStats() {
    transactionContainerService.clear();
  }

}
