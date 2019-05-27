package com.agility.shopping.cart.models;

import com.agility.shopping.cart.controllers.ProductController;
import com.agility.shopping.cart.securities.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

/**
 * ProductControllerView class used to custom view for serialize response body
 */
@ControllerAdvice(assignableTypes = ProductController.class)
@Slf4j
public class ProductControllerView extends AbstractMappingJacksonResponseBodyAdvice {

    @Autowired
    private SecurityService securityService;

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue mappingJacksonValue,
                                           MediaType mediaType,
                                           MethodParameter methodParameter,
                                           ServerHttpRequest serverHttpRequest,
                                           ServerHttpResponse serverHttpResponse) {
        log.debug("Serialize body data with view: {}", securityService.getViewForCurrentUser());
        mappingJacksonValue.setSerializationView(securityService.getViewForCurrentUser());
    }
}
