package com.weather.app.service;

/*
 * This calls repsonsible to make external service  and take data and give back
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import com.weather.app.model.WeatherData;

public class WeatherDataService {

	@Value("${openweathermap.url}")
	private String extranalUrl;
	@Value("${openweathermap.appid}")
	private String appid;

	String UNAUTHORISED = "Unauthorised";

	public WeatherData getWeatherData(String city) throws IOException, ParseException {

		StringBuffer response = makeExternalApiRequest(city);
		WeatherData weatherData = null;
		JSONParser parser = new JSONParser();

		if (!response.equals(null)) {
			weatherData = new WeatherData();
			Object obj = parser.parse(response.toString());
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject jObject = (JSONObject) jsonObject.get("main");
			System.out.println();
			weatherData.setDayTemparature(jObject.get("temp").toString());
			weatherData.setNightTemparature(jObject.get("temp_min").toString());
			weatherData.setPreasure(jObject.get("pressure").toString());
		}
		return weatherData;

	}

	/*
	 * The method to make extrenal commnication
	 */

	private StringBuffer makeExternalApiRequest(String city) throws IOException {
		String url = extranalUrl + city + "&appid=" + appid;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		StringBuffer response = null;
		// optional default is GET
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response;
		}
		if (responseCode == 401) {
			response = new StringBuffer();
			return response.append(UNAUTHORISED);
		}
		return response;

	}
}
