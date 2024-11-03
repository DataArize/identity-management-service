package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SuccessResponse implements Serializable {

    private String status;
    private int statusCode;
    private String message;
    private Object data;

}
