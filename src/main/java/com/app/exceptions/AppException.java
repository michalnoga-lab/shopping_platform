package com.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exceptions")
public class AppException extends RuntimeException {

    @Id
    @GeneratedValue
    private Long id;
    private ExceptionCodes exceptionCode;
    private String description;
    private LocalDateTime time;

    public AppException(ExceptionCodes exceptionCode, String description) {
        this.exceptionCode = exceptionCode;
        this.description = description;
        this.time = LocalDateTime.now();
    }
}