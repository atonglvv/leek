package com.atong.leek.demo.config.log;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Order(0)
@Component
@WebFilter(filterName = "requestIdFilter", urlPatterns = "/**")
public class RequestIdFilter implements Filter {

    private static final String HEADER_REQUEST_ID = "Request-Id";

    private static final String HEADER_X_REQUEST_ID = "X-Request-Id";

    private static final String REQUEST_ID = "requestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            //获取并设置RequestId
            MDC.put(REQUEST_ID, this.getRequestId(httpServletRequest));
            chain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            MDC.clear();
        }
    }

    /**
     * 获取请求RequestId
     *
     * @param httpServletRequest 请求
     * @return requestId
     */
    private String getRequestId(HttpServletRequest httpServletRequest) {
        String requestId = httpServletRequest.getHeader(HEADER_REQUEST_ID);
        if (StringUtils.isBlank(requestId)) {
            requestId = httpServletRequest.getHeader(REQUEST_ID);
        }
        if (StringUtils.isBlank(requestId)) {
            requestId = httpServletRequest.getHeader(HEADER_X_REQUEST_ID);
        }
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString().replace("-", "");
        }
        return requestId;
    }
}