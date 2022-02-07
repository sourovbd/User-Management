package com.aes.corebackend.util.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aes.corebackend.util.response.AjaxResponseStatus.ERROR;
import static com.aes.corebackend.util.response.AjaxResponseStatus.SUCCESS;
import static java.util.Objects.isNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private AjaxResponseStatus status;

    private Object data;

    private String msg;

    private List<String> errors;

    private Map<String, List<String>> fieldsErrors;


    public AjaxResponse(AjaxResponseStatus status) {

        this.status = status;
    }

    public AjaxResponse addErrorMessage(String msg) {
        if (isNull(errors)) {
            errors = new ArrayList<>();
        }

        errors.add(msg);

        return this;
    }

    public AjaxResponse addFieldError(String fieldName, String msg) {
        if (isNull(fieldsErrors)) {
            fieldsErrors = new HashMap<>();
        }

        List<String> errors = fieldsErrors.get(fieldName);
        if (isNull(errors)) {
            errors = new ArrayList<>();
            fieldsErrors.put(fieldName, errors);
        }

        errors.add(msg);

        return this;
    }

    public static AjaxResponse success(Object data) {

        return new AjaxResponse(SUCCESS).setData(data);
    }

    public static AjaxResponse error() {

        return new AjaxResponse(ERROR);
    }

    public static AjaxResponse error(String msg) {

        return error().addErrorMessage(msg);
    }

    public static AjaxResponse prepareErrorResponse(BindingResult result) {
        AjaxResponse response = AjaxResponse.error();
        for (FieldError fe : result.getFieldErrors()) {
            response.addFieldError(fe.getField(), fe.getDefaultMessage());
        }

        return response;
    }

}
