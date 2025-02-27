package com.lucassousa25.weatherapi;

import com.lucassousa25.weatherapi.dto.WeatherResponse;
import com.lucassousa25.weatherapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherIntegrationTest {

    @Autowired
    WebTestClient testClient;

    @LocalServerPort
    private int port;


    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void getWeather_CidadeValida_RetornarInfoClimaComStatus200() {
        String city = "Santos";
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/weather?city=" + city,
                WeatherResponse.class
        );

        org.assertj.core.api.Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        org.assertj.core.api.Assertions.assertThat(response.getBody()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getBody().getCityName()).isEqualToIgnoringCase(city);
    }

    @Test
    public void getWeather_CidadeInvalida_RetornarErroMessageComStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/weather?city={city}", "CidadeNãoExistente")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void getWeather_CampoCidadeVazio_deveRetornarErroMessageComStatus400() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/weather?city={city}", "")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }
}
