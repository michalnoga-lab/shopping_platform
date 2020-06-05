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

    @Column(length = 10000)
    private String message;

    private String remoteAddress;
    private LocalDateTime time;

    public void setMessage(String message) {
        if (message.length() > 10000) {
            this.message = message.substring(0, 10000);
        } else {
            this.message = message;
        }
    }
}