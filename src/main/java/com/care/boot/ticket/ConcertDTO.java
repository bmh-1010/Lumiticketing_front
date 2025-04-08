package com.care.boot.ticket;

import java.io.Serializable;

public class ConcertDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int concertId;       // ✅ camelCase로 수정
    private String name;
    private String genre;
    private String location;
    private String date;         // 또는 java.sql.Date

    // Getter / Setter
    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
