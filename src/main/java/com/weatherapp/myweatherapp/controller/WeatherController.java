package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city) {

    CityInfo ci = weatherService.forecastByCity(city);

    return ResponseEntity.ok(ci);
  }

  @GetMapping("/compare-daylight/{city1}/{city2}")
  public ResponseEntity<String> compareDaylightHours(@PathVariable String city1, @PathVariable String city2) {
      return ResponseEntity.ok(weatherService.compareDaylightHours(city1, city2));
  }

  @GetMapping("/rain-check/{city1}/{city2}")
  public ResponseEntity<String> checkRain(@PathVariable String city1, @PathVariable String city2) {
    return ResponseEntity.ok(weatherService.checkRain(city1, city2));
}

}
