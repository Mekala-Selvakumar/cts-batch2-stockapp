package com.stackroute.stockapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stackroute.stockapp.filter.AppFilter;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockappApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockappApplication.class, args);
	}
	//create AppFilter bean
	//create bean for BCryptPasswordEncoder

	// @Bean
	// public AppFilter appFilter() {
	// 	return new AppFilter();
	// }



}
