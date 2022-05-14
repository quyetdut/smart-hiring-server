package com.smartdev.iresource.personal.common.response;

import com.smartdev.iresource.personal.common.enums.IResponseResult;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    private ResponseHandler() {}

    private static ResponseHandler responseHandler;
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String RESULT = "result";
    private static final String DATA = "data";

    public static ResponseHandler getInstance() {
        if (responseHandler == null) {
            responseHandler = new ResponseHandler();
        }

        return responseHandler;
    }

    public Map<String, Object> response(Object data, IResponseResult responseEnum){
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE , responseEnum.getMessage());
        response.put(STATUS , responseEnum.getCode());
        response.put(RESULT , responseEnum.getResult());

        if (data != null) {
            response.put(DATA, data);
        }

        return response;
    }
}
