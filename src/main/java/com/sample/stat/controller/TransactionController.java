package com.sample.stat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sample.stat.model.TransactionRecord;
import com.sample.stat.service.StatService;
import com.sample.stat.util.StatResourceException;

import io.swagger.annotations.ApiOperation;

@RestController
public class TransactionController {
	
	@Autowired
	StatService statService;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(nickname = "Update Transactions", value = "", notes = "Endpoint to invoke when a new Transactions has happened.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(name="Transactions",absolute=true)
	@RequestMapping(method = RequestMethod.POST, value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateTransactions(@RequestBody TransactionRecord tr)
			throws StatResourceException {
		tr.validate();
		statService.saveTransactionRecord(tr);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
