package com.sample.stat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sample.stat.model.TransactionStatus;
import com.sample.stat.service.StatService;
import com.sample.stat.util.StatResourceException;

import io.swagger.annotations.ApiOperation;



@RestController
public class StatusController {
	
	@Autowired
	StatService statService;

	@ApiOperation(nickname = "Get Statistics", value = "", notes = "Returns the statistic based on the transactions which happened in the last 60 seconds.", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(name="Statistics",absolute=true)
	@RequestMapping(method = RequestMethod.GET, value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<TransactionStatus> fetchStats() throws StatResourceException{
		return new ResponseEntity<TransactionStatus>(statService.getStatistics(), HttpStatus.OK);
	}
}
