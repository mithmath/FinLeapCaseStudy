package com.weather.app.web;

/*
 * The Resat api contoller to map the url
 */
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.model.WeatherData;
import com.weather.app.service.WeatherDataService;

@RestController
@RequestMapping(value = "/wethertoday")
public class WeatherController {

	@Autowired
	private WeatherDataService weatherDataService;

	@Value("${openweathermap.appid}")
	private String appid;

	/*
	 * The get method to map the url
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{city}", produces = "application/json")
	public ResponseEntity<Object> getWaetherToday(@PathVariable String city) {

		WeatherData weatherData = null;

		try {
			weatherData = weatherDataService.getWeatherData(city);
		} catch (IOException | ParseException e) {

			e.printStackTrace();
		}

		if (weatherData == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_HTML)
					.body("Application Down");
		}

		return ResponseEntity.status(200).body(weatherData);

	}

}
