package com.github.petrovyegor.tennisscoreboard.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",  // Применяется ко всем URL
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8"),
                //@WebInitParam(name = "contentType", value = "application/json")
        }
)
public class ContentTypeAndEncodingFilter implements Filter {
    private String encoding;
    //private String contentType;

    @Override
    public void init(FilterConfig filterConfig) {
        this.encoding = filterConfig.getInitParameter("encoding");
        //this.contentType = filterConfig.getInitParameter("contentType");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        //servletResponse.setContentType(contentType);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

