package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.time.Duration;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;
  public WeatherService(VisualcrossingRepository weatherRepo) {
    this.weatherRepo = weatherRepo;
}
  public CityInfo forecastByCity(String city) {
    return weatherRepo.getByCity(city);
  }

  public String compareDaylightHours(String city1, String city2) {
    CityInfo info1 = weatherRepo.getByCity(city1);
    CityInfo info2 = weatherRepo.getByCity(city2);

    if (info1 == null || info2 == null || info1.getCurrentConditions() == null || info2.getCurrentConditions() == null) {
      return "Error: Could not retrieve weather data for one or both cities.";
    }

    // Extract sunrise and sunset times
    String sunrise1 = info1.getCurrentConditions().getSunrise();
    String sunset1 = info1.getCurrentConditions().getSunset();
    String sunrise2 = info2.getCurrentConditions().getSunrise();
    String sunset2 = info2.getCurrentConditions().getSunset();

    if (sunrise1 == null || sunset1 == null || sunrise2 == null || sunset2 == null) {
      return "Error: Sunrise or sunset data is missing for one or both cities.";
    }

    // Parse times 
    LocalTime sunriseTime1 = parseTimeSafely(sunrise1);
    LocalTime sunsetTime1 = parseTimeSafely(sunset1);
    LocalTime sunriseTime2 = parseTimeSafely(sunrise2);
    LocalTime sunsetTime2 = parseTimeSafely(sunset2);

    if (sunriseTime1 == null || sunsetTime1 == null || sunriseTime2 == null || sunsetTime2 == null) {
      return "Error: Could not parse sunrise/sunset time for one or both cities.";
    }

    // Calculate daylight duration in minutes
    long daylight1 = Duration.between(sunriseTime1, sunsetTime1).toMinutes();
    long daylight2 = Duration.between(sunriseTime2, sunsetTime2).toMinutes();

    if (daylight1 > daylight2) {
      return city1 + " has longer daylight hours (" + daylight1 + " minutes) compared to " + city2 + " (" + daylight2 + " minutes).";
    } else if (daylight2 > daylight1) {
      return city2 + " has longer daylight hours (" + daylight2 + " minutes) compared to " + city1 + " (" + daylight1 + " minutes).";
    } else {
      return "Both cities have the same daylight duration (" + daylight1 + " minutes).";
    }
  }

  
   // Api returns in "HH:mm:ss" format however for future safety supporting both "HH:mm:ss" and "HH:mm" formats.  
   
  private LocalTime parseTimeSafely(String timeString) {
    DateTimeFormatter[] formatters = {
        DateTimeFormatter.ofPattern("HH:mm:ss"),
        DateTimeFormatter.ofPattern("HH:mm")
    };

    for (DateTimeFormatter formatter : formatters) {
      try {
        return LocalTime.parse(timeString, formatter);
      } catch (DateTimeParseException ignored) {
        
      }
    }

    return null; // If parsing fails, return null
  }

    
    public String checkRain(String city1, String city2) {
        CityInfo info1 = weatherRepo.getByCity(city1);
        CityInfo info2 = weatherRepo.getByCity(city2);
        if (info1 == null || info2 == null) {
          if (info1 == null && info2 == null) {
              return "Error: Could not retrieve current weather data for both " + city1 + " and " + city2 + ".";
          } else if (info1 == null) {
              return "Error: Could not retrieve current weather data for " + city1 + ".";
          } else {
              return "Error: Could not retrieve current weather data for " + city2 + ".";
          }
      }
  
    

        List<String> precipTypeCity1 = info1.getCurrentConditions().getPrecipType();
        List<String> precipTypeCity2 = info2.getCurrentConditions().getPrecipType();

        // Safe check for empty list
        boolean isRainingCity1 = precipTypeCity1 != null && !precipTypeCity1.isEmpty() && precipTypeCity1.contains("rain");
        boolean isRainingCity2 = precipTypeCity2 != null && !precipTypeCity2.isEmpty() && precipTypeCity2.contains("rain");

        if (isRainingCity1 && isRainingCity2) {
            return "It is currently raining in both " + city1 + " and " + city2 + ".";
        } else if (isRainingCity1) {
            return "It is currently raining in " + city1 + ".";
        } else if (isRainingCity2) {
            return "It is currently raining in " + city2 + ".";
        } else {
            return "It is not currently raining in either " + city1 + " or " + city2 + ".";
        }
    }
}
