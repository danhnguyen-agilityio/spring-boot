package com.agility.shopping.cart.models;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

/**
 * This class contain info request api and expected response for that request
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestInfo {
    private MockHttpServletRequestBuilder request;
    private Map<String, Object> params;
    private String token;
    private Object body;
    private HttpStatus httpStatus;

    // This field used to map key from response data to expected value
    private Map<String, Object> jsonMap;
}
