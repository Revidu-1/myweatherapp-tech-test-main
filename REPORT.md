# Weather App Feature Implementation Report
**Author:** Revidu Induvara  
**Date:** February 14, 2025  
**Module:** Software Engineer Graduate Programme – The Telegraph  

## 1. Introduction
This document describes the implementation of the two features for the Weather App:

- **Daylight Hours Comparison** – Given two city names, compare the length of their daylight hours.
- **Rain Check** – Determine if it is currently raining in one or both of the given cities.

The implementation follows a clean, modular approach using Spring Boot, with proper dependency injection and unit tests to validate correctness.

## 2. Implementation Details
### 2.1 Technologies Used
- Java 17  
- Spring Boot (Web & Dependency Injection)  
- Maven (for build management)  
- JUnit 5 (for unit testing)  
- Visual Crossing Weather API (for real-time weather data)  

### 2.2 Features Implemented
#### 2.2.1 Daylight Hours Comparison
- The method `compareDaylightHours(String city1, String city2)` retrieves sunrise and sunset times from the API and calculates the duration of daylight for both cities.
- It returns a message indicating which city has a longer daylight duration or if both are equal.
- **Handles edge cases** like missing or invalid sunrise/sunset times.

#### 2.2.2 Rain Check
- The method `checkRain(String city1, String city2)` checks the `precipType` field in the API response.
- Determines if either or both cities are experiencing rain.
- **Handles cases** where precipitation data is missing.

## 3. Code Design & Best Practices
### 3.1 Dependency Injection
- Used **constructor-based dependency injection** for `WeatherService` 
- This makes the service more testable and avoids issues with dependency injection in unit tests.

### 3.2 Exception Handling
- The `WeatherController` includes basic **exception handling** to return meaningful error messages instead of stack traces.
- Example: If the API returns `null` for a city, the controller responds with  
  `"Error: Could not retrieve weather data for <city>."`

### 3.3 Unit Testing
- **Implemented 14 unit tests** using JUnit 5.
- **Mocked API calls** using a custom in-memory repository to ensure tests do not depend on external services.
- **Tests cover edge cases**, such as:
  - City not found in the API response.
  - Missing or incorrectly formatted sunrise/sunset times.
  - Empty or multiple precipitation types (e.g., `"rain"`, `"snow"`).

## 4. Testing Summary
### Test Cases

| Test Name | Expected Output | Status |
|-----------|---------------|--------|
| `testCompareDaylightHours_City1HasLongerDay` | City 1 has longer daylight hours |  Passed |
| `testCompareDaylightHours_City2HasLongerDay` | City 2 has longer daylight hours |  Passed |
| `testCompareDaylightHours_BothEqual` | Both cities have the same daylight duration |  Passed |
| `testCompareDaylightHours_MissingSunriseOrSunset` | Error message |  Passed |
| `testCompareDaylightHours_InvalidTimeFormat` | Error message |  Passed |
| `testCompareDaylightHours_CityNotFound` | Error message |  Passed |
| `testCheckRain_City1Raining` | "City 1 is currently raining" |  Passed |
| `testCheckRain_City2Raining` | "City 2 is currently raining" |  Passed |
| `testCheckRain_BothRaining` | "Both cities are currently raining" | Passed |
| `testCheckRain_NeitherRaining` | "Neither city is currently raining" |  Passed |
| `testCheckRain_CityHasEmptyPrecipType` | "Neither city is currently raining" |  Passed |
| `testCheckRain_CityHasMultiplePrecipTypes` | "Both cities are currently raining" |  Passed |
| `testCheckRain_CityNotFound` | Error message |  Passed |

### Overall Test Results:
 **All 14 tests passed successfully** 

## 5. Deployment & Submission
- The final implementation was **committed to GitHub** under the `feature/weather-service-tests` branch.
- A **Pull Request** was raised with proper documentation, review comments, and commit history.
- The **Maven build passed successfully** with no errors.

## 6. Conclusion
The successfully implements both the **daylight hours comparison** and **rain check** features while following **clean code principles, exception handling, and comprehensive unit testing**.  
This ensures it is **reliable, maintainable, and testable**.
