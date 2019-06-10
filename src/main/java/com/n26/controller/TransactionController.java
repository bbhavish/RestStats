package com.n26.controller;

import com.n26.exception.TransactionRangeException;
import com.n26.model.Transaction;
import com.n26.model.TransactionStats;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @PostMapping("/transactions")
  public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
    try {
      if (!transaction.validate()) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
      }
      transactionService.addTransaction(transaction);
      return new ResponseEntity<>(HttpStatus.CREATED);

    } catch (TransactionRangeException e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/statistics")
  public ResponseEntity<TransactionStats> getStats() {
    return ResponseEntity.ok(transactionService.getTransactionStats());
  }

  @DeleteMapping("transactions")
  public ResponseEntity<?> deletedStats() {
    transactionService.deleteStats();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
