package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "logger_infos")
public class LoggerInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private InfoCodes infoCode;
    private Long userId;
    private String message;
    private String remoteAddress;
    private LocalDateTime time;

    public LoggerInfo(InfoCodes infoCode, String message) {
        this.infoCode = infoCode;
        this.message = message;
        this.remoteAddress = remoteAddress;
        this.time = LocalDateTime.now();
    }
}