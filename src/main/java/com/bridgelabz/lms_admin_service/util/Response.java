package com.bridgelabz.lms_admin_service.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * purpose: Creating a response class
 * @author Anuj Solanki
 */
@Data
@AllArgsConstructor
public class Response {
    private String errorMsg;
    private long errorCode;
    private Object token;

    public Response() {

    }
}
