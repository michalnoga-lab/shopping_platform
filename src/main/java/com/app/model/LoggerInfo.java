package com.app.model;

import com.app.exceptions.ExceptionCodes;
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
    private ExceptionCodes exceptionCode;
    private String exceptionMessage;
    private LocalDateTime time;
}