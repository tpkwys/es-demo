package com.aaron.esdemo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Movie {
    private String name;
    private String bref;
    private String type;
    private String country;
    private String director;
    private LocalDateTime date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBref() {
        return bref;
    }

    public void setBref(String bref) {
        this.bref = bref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
