package com.github.petrovyegor.tennisscoreboard.controller.filter;

import com.github.petrovyegor.tennisscoreboard.exception.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(
        filterName = "03_ErrorHandlerFilter",
        urlPatterns = "/*"
)
public class ErrorHandlerFilter implements Filter {
    private static final String FATAL_ERROR_MESSAGE = "Fatal error. ";
    private static final String UNSUPPORTED_URL_MESSAGE = "Page not found. Unsupported URL '%s' given. ";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        if (!isValidPath(path)) {
            forwardToErrorPage(request, response, UNSUPPORTED_URL_MESSAGE.formatted(path));
            return;
        }

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

    @Override
    public void destroy() {
    }

    private void forwardToErrorPage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String message) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        //request.setAttribute("errorDetails", details);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    private boolean isValidPath(String path) {
        return path.equals("/") ||
                path.equals("/index.jsp") ||
                //path.equals("/error.jsp") ||
                path.equals("/new-match") ||
                path.startsWith("/matches") ||
                path.startsWith("/match-score") ||
                path.startsWith("/finished-match") ||
                path.startsWith("/css/");
    }
}
