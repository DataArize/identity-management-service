package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {


    private String title;
    private String message;
    private String errorCode;
    private int status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<Violation> violations;


    public ErrorResponse(String message, int status,
                         String title, String errorCode) {
        this.status = status;
        this.message = message;
        this.title = title;
        this.errorCode = errorCode;
    }

}