package com.app.exceptions;

import com.app.model.InfoCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppException extends RuntimeException {

    private InfoCodes exceptionCode;
    private String description;
    private LocalDateTime time;

    public AppException(InfoCodes exceptionCode, String description) {
        this.exceptionCode = exceptionCode;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    public InfoCodes getExceptionCode() {
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