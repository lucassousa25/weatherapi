package com.lucassousa25.weatherapi.service;

import com.lucassousa25.weatherapi.dto.WeatherResponse;
import com.lucassousa25.weatherapi.exception.CityNotFoundException;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final Dotenv dotenv = Dotenv.load();
    private final String apiKey = dotenv.get("OPENWEATHER_API_KEY");
    private final String apiUrl = dotenv.get("OPENWEATHER_URL");

    public WeatherResponse getWeather(String city) {
        String url = apiUrl + "?q=" + city + "&lang=pt_br" + "&appid=" + apiKey + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            JSONObject json = new JSONObject(responseBody);
            String cod = json.optString("cod");
            String message = json.optString("message", "Erro desconhecido");

            if(cod.equals("404")) {
                throw new CityNotFoundException(message);
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
