package com.sample.stat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sample.stat.service.StatService;

@Component
public class StatScheduler {

	@Autowired
	StatService statService;
	
	@Scheduled(initialDelay=60000,fixedRate = 60000)
	public void refreshStats() {
		statService.refreshStatistics();
	}
}
