package com.lucassousa25.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonProperty("name")
    private String cityName;

    @JsonProperty("weather")
    private List<WeatherDescription> weather;

    @JsonProperty("main")
    private MainInfo main;

    @JsonProperty("wind")
    private WindInfo wind;

    public String getCityName() {
        return cityName;
    }

    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public MainInfo getMain() {
        return main;
    }

    public WindInfo getWind() {
        return wind;
    }

    public static class WeatherDescription {
        private String main;

        public String getMain() { return main; }

        @JsonProperty("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }

    public static class MainInfo {
        @JsonProperty("temp")
        private double temperature;

        @JsonProperty("humidity")
        private int humidity;

        public double getTemperature() {
            return temperature;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class WindInfo {
        @JsonProperty("speed")
        private double speed;

        public double getSpeed() {
            return speed;
        }
    }
}
