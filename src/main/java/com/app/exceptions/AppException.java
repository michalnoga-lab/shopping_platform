package com.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
/*@Entity
@Table(name = "exceptions")*/ // TODO: 2020-02-15
public class AppException extends RuntimeException {

    /*@Id
    @GeneratedValue
    private Long id;*/
    private ExceptionCodes exceptionCode;
    private String description;
    private LocalDateTime time;

    public AppException(ExceptionCodes exceptionCode, String description) {
        this.exceptionCode = exceptionCode;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    public ExceptionCodes getExceptionCode() {
        return exceptionCode;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppException that = (AppException) o;
        return exceptionCode == that.exceptionCode &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exceptionCode, description);
    }

    @Override
    public String toString() {
        return "AppException{" +
                "exceptionCode=" + exceptionCode +
                ", description='" + description + '\'' +
                '}';
    }
}