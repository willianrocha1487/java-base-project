package com.base.java.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "error")
@AllArgsConstructor
public class ErrorResponse {

    private Integer code;

    private String type;
    private String message;

    private List<String> details;

}
