package io.github.hossensyedriadh.springbootjwtauthentication.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("unused")
@Getter
@Setter
public class GenericErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -3609663713177512786L;

    private int status;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private String path;

    private GenericErrorResponse() {
        this.timestamp = LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }

    public GenericErrorResponse(int status) {
        this();
        this.status = status;
    }

    public GenericErrorResponse(HttpStatus status, String message, String path) {
        this();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public GenericErrorResponse(int status, Throwable throwable, String path) {
        this();
        this.status = status;
        this.message = "Access denied";
        this.error = (throwable.getCause() != null) ? throwable.getCause().getMessage() : throwable.getMessage();
        this.path = path;
    }

    public GenericErrorResponse(int status, String message, String path) {
        this();
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public GenericErrorResponse(String message) {
        this();
        this.message = message;
    }

    public GenericErrorResponse(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public GenericErrorResponse(int status, String message, Throwable throwable, String path) {
        this();
        this.status = status;
        this.message = message;
        this.error = (throwable.getCause() != null) ? throwable.getCause().getMessage() : throwable.getMessage();
        this.path = path;
    }
}
