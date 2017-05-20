package com.sample.stat.config;

import org.springframework.context.annotation.Configuration;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@EnableMetrics(proxyTargetClass = true)
@Configuration
public class MetricsConfig extends MetricsConfigurerAdapter{

}
