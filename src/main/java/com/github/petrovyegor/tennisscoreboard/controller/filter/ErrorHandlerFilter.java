package com.github.petrovyegor.tennisscoreboard.controller.filter;

import com.github.petrovyegor.tennisscoreboard.exception.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8"),
        }
)
public class ErrorHandlerFilter implements Filter {
    private static final String FATAL_ERROR_MESSAGE = "Fatal error. ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidParamException |
                 InvalidRequestException |
                 NotFoundException |
                 RestErrorException |
                 DBException e) {
            forwardToErrorPage(request, response, e.getMessage());
        } catch (Exception e) {
            forwardToErrorPage(request, response, FATAL_ERROR_MESSAGE + e.getMessage());
        }
    }

    private void forwardToErrorPage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String message) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        //request.setAttribute("errorDetails", details);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
    }
}
