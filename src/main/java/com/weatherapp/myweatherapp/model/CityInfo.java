package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CityInfo {

    @JsonProperty("address")
    private String address;

    @JsonProperty("description")
    private String description;

    @JsonProperty("currentConditions")
    private CurrentConditions currentConditions;

    @JsonProperty("days")
    private List<Days> days;

    //  Getter & Setter for current conditions
    public CurrentConditions getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(CurrentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }

    public static class CurrentConditions {

        @JsonProperty("temp")
        private String currentTemperature;

        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("sunset")
        private String sunset;

        @JsonProperty("feelslike")
        private String feelslike;

        @JsonProperty("humidity")
        private String humidity;

        @JsonProperty("conditions")
        private String conditions;

        @JsonProperty("preciptype")
        private List<String> precipType;

        //  Getters & Setters for fields
        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public List<String> getPrecipType() {
            return precipType;
        }

        public void setPrecipType(List<String> precipType) {
            this.precipType = precipType;
        }
    }

    public static class Days {
        @JsonProperty("datetime")
        private String date;

        @JsonProperty("temp")
        private String currentTemperature;

        @JsonProperty("tempmax")
        private String maxTemperature;

        @JsonProperty("tempmin")
        private String minTemperature;

        @JsonProperty("conditions")
        private String conditions;

        @JsonProperty("description")
        private String description;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }
    }
}
