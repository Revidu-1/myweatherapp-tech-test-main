package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private WeatherService weatherService;
    private Map<String, CityInfo> mockRepo;  //  Simulating the repository

    @BeforeEach
    void setUp() {
        mockRepo = new HashMap<>();

        // Injecting Mock Repository into WeatherService
        weatherService = new WeatherService(new VisualcrossingRepository() {
            @Override
            public CityInfo getByCity(String city) {
                return mockRepo.get(city);  //  Return mock data instead of API call
            }
        });
    }

    @Test
    void testCompareDaylightHours_City1HasLongerDay() {
        mockWeatherRepo("London", "06:00:00", "19:00:00");
        mockWeatherRepo("New York", "07:30:00", "18:00:00");

        String result = weatherService.compareDaylightHours("London", "New York");

        assertTrue(result.contains("London has longer daylight hours"));
    }

    @Test
    void testCompareDaylightHours_City2HasLongerDay() {
        mockWeatherRepo("Tokyo", "06:30:00", "17:30:00");
        mockWeatherRepo("Paris", "06:00:00", "18:30:00");

        String result = weatherService.compareDaylightHours("Tokyo", "Paris");

        assertTrue(result.contains("Paris has longer daylight hours"));
    }

    @Test
    void testCompareDaylightHours_BothEqual() {
        mockWeatherRepo("Berlin", "07:00:00", "19:00:00");
        mockWeatherRepo("Madrid", "07:00:00", "19:00:00");

        String result = weatherService.compareDaylightHours("Berlin", "Madrid");

        assertEquals("Both cities have the same daylight duration (720 minutes).", result);
    }

    @Test
    void testCheckRain_City1Raining() {
        mockWeatherRepoWithRain("Seattle", Arrays.asList("rain"));
        mockWeatherRepoWithRain("Dubai", null);

        String result = weatherService.checkRain("Seattle", "Dubai");

        assertEquals("It is currently raining in Seattle.", result);
    }

    @Test
    void testCheckRain_City2Raining() {
        mockWeatherRepoWithRain("Miami", null);
        mockWeatherRepoWithRain("Sydney", Arrays.asList("rain"));

        String result = weatherService.checkRain("Miami", "Sydney");

        assertEquals("It is currently raining in Sydney.", result);
    }

    @Test
    void testCheckRain_BothRaining() {
        mockWeatherRepoWithRain("Tokyo", Arrays.asList("rain"));
        mockWeatherRepoWithRain("Paris", Arrays.asList("rain"));

        String result = weatherService.checkRain("Tokyo", "Paris");

        assertEquals("It is currently raining in both Tokyo and Paris.", result);
    }

    @Test
    void testCheckRain_NeitherRaining() {
        mockWeatherRepoWithRain("Los Angeles", null);
        mockWeatherRepoWithRain("Cairo", null);

        String result = weatherService.checkRain("Los Angeles", "Cairo");

        assertEquals("It is not currently raining in either Los Angeles or Cairo.", result);
    }
    @Test
void testCompareDaylightHours_MissingSunriseOrSunset() {
    mockWeatherRepo("CityA", "06:00:00", null); // No sunset time
    mockWeatherRepo("CityB", "07:00:00", "18:00:00");

    String result = weatherService.compareDaylightHours("CityA", "CityB");

    assertEquals("Error: Sunrise or sunset data is missing for one or both cities.", result);
}

@Test
void testCompareDaylightHours_InvalidTimeFormat() {
    mockWeatherRepo("CityA", "invalid", "19:00:00");
    mockWeatherRepo("CityB", "07:00:00", "18:00:00");

    String result = weatherService.compareDaylightHours("CityA", "CityB");

    assertEquals("Error: Could not parse sunrise/sunset time for one or both cities.", result);
}

@Test
void testCompareDaylightHours_CityNotFound() {
    mockWeatherRepo("CityA", "06:00:00", "18:00:00");

    String result = weatherService.compareDaylightHours("CityA", "CityB");

    assertEquals("Error: Could not retrieve weather data for one or both cities.", result);
}

@Test
void testCheckRain_CityHasEmptyPrecipType() {
    mockWeatherRepoWithRain("Seattle", new ArrayList<>()); // Empty list instead of null
    mockWeatherRepoWithRain("Dubai", null);

    String result = weatherService.checkRain("Seattle", "Dubai");

    assertEquals("It is not currently raining in either Seattle or Dubai.", result);
}

@Test
void testCheckRain_CityHasMultiplePrecipTypes() {
    mockWeatherRepoWithRain("Tokyo", Arrays.asList("rain", "snow"));
    mockWeatherRepoWithRain("Paris", Arrays.asList("rain"));

    String result = weatherService.checkRain("Tokyo", "Paris");

    assertEquals("It is currently raining in both Tokyo and Paris.", result);
}

@Test
void testCheckRain_CityNotFound() {
    mockWeatherRepoWithRain("CityA", Arrays.asList("rain"));

    String result = weatherService.checkRain("CityA", "CityB");

    assertEquals("Error: Could not retrieve current weather data for CityB.", result);
}


    //  Mock method to simulate weather repo storage
    private void mockWeatherRepo(String cityName, String sunrise, String sunset) {
        CityInfo cityInfo = new CityInfo();
        CityInfo.CurrentConditions conditions = new CityInfo.CurrentConditions();
        conditions.setSunrise(sunrise);
        conditions.setSunset(sunset);
        cityInfo.setCurrentConditions(conditions);
        mockRepo.put(cityName, cityInfo);  
    }

    //  Mock method for precipitation type
    private void mockWeatherRepoWithRain(String cityName, List<String> precipType) {
        CityInfo cityInfo = new CityInfo();
        CityInfo.CurrentConditions conditions = new CityInfo.CurrentConditions();
        conditions.setPrecipType(precipType);
        cityInfo.setCurrentConditions(conditions);
        mockRepo.put(cityName, cityInfo);  
    }
    
}
