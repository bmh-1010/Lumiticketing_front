package com.care.boot.ticket;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long reservationId;
    private int concertId;
    private String id; // 사용자 ID
    private LocalDateTime reservedAt;
    private String eventType; // 예: booking, cancel

    // 기본 생성자
    public ReservationDTO() {}

    // 전체 생성자
    public ReservationDTO(Long reservationId, int concertId, String id, LocalDateTime reservedAt, String eventType) {
        this.reservationId = reservationId;
        this.concertId = concertId;
        this.id = id;
        this.reservedAt = reservedAt;
        this.eventType = eventType;
    }

    // Getter & Setter
    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public int getConcertId() { return concertId; }
    public void setConcertId(int concertId) { this.concertId = concertId; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
