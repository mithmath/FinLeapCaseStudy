package com.weather.app;

/*
 * Bean Initialisation for the dependency injection
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.weather.app.service.WeatherDataService;

@Configuration
public class Initializer {

	@Bean
	public WeatherDataService weatherDataService() {
		return new WeatherDataService();
	}

}
