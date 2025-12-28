package com.github.petrovyegor.tennisscoreboard.controller.filter;

import com.github.petrovyegor.tennisscoreboard.exception.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebFilter("/*")
public class ErrorHandlerFilter implements Filter {
    private static final String UNSUPPORTED_URL_MESSAGE = "Page not found. Unsupported URL '%s' given. ";
    private static final Set<String> EXACT_PATHS = Set.of(
            "/", "/index", "/new-match", "/home"
    );
    private static final List<String> PREFIX_PATHS = List.of(
            "/matches", "/match-score", "/finished-match", "/css/", "/js/", "/images", "/error.jsp"
    );

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());

        if (path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/")) {

            request.getRequestDispatcher(path).forward(request, response);
            return;
        }

        if (!isValidPath(path)) {
            request.setAttribute("errorMessage", UNSUPPORTED_URL_MESSAGE.formatted(path));
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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
            forwardToErrorPage(request, response, e.getMessage() + e.getCause());
        }
    }

    @Override
    public void destroy() {
    }

    private void forwardToErrorPage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String message) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    private boolean isValidPath(String path) {
        return EXACT_PATHS.contains(path) || PREFIX_PATHS.stream().anyMatch(path::startsWith);
    }
}
