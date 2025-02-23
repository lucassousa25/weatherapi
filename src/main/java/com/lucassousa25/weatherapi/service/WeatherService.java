package com.lucassousa25.weatherapi.service;

import com.lucassousa25.weatherapi.dto.WeatherResponse;
import com.lucassousa25.weatherapi.exception.WeatherException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final Dotenv dotenv = Dotenv.load();
    private final String apiKey = dotenv.get("OPENWEATHER_API_KEY");
    private final String apiUrl = dotenv.get("OPENWEATHER_URL");

    public WeatherResponse getWeather(String city) {
        String url = apiUrl + "?q=" + city + "&lang=pt_br" + "&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new WeatherException("Erro ao obter dados de previsão: " + response.getStatusCode());
        }

        return response.getBody();
    }
}
