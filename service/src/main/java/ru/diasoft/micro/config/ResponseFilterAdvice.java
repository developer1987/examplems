package ru.diasoft.micro.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@ControllerAdvice
public class ResponseFilterAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    public static final String FILTER_ID = "DynamicExclude";
    private static final String PARAM_NAME = "fields";
    private static final String DELIMITER = ",";
    private static final String[] DEFAULT_PARAMS_ARRAY = { "errorData", "errorMessage", "errorDetails", "status" };

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue container, MediaType contentType,
            MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        if (container.getFilters() != null) {
            return;
        }

        HttpServletRequest baseReq = ((ServletServerHttpRequest) request).getServletRequest();
        String exclusion = baseReq.getParameter(PARAM_NAME);

        String[] attrs = StringUtils.split(exclusion, DELIMITER);
        container.setFilters(configFilters(attrs));

    }

    private FilterProvider configFilters(String[] attrs) {
        PropertyFilter filter = ArrayUtils.isNotEmpty(attrs)
                ? SimpleBeanPropertyFilter.filterOutAllExcept(ArrayUtils.addAll(attrs, DEFAULT_PARAMS_ARRAY))
                : SimpleBeanPropertyFilter.serializeAllExcept();

        return new SimpleFilterProvider().addFilter(FILTER_ID, filter);
    }
}
