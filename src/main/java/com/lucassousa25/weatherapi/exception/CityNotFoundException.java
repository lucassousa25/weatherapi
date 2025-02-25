package com.lucassousa25.weatherapi.exception;

import lombok.Getter;

@Getter
public class CityNotFoundException extends RuntimeException {

    private String city;

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(String city, String message) {
        super(message);
        this.city = city;
    }
}
