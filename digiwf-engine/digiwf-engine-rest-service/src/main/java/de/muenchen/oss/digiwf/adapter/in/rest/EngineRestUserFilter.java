package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class EngineRestUserFilter implements Filter {

    public static final String ROUTE_PATTERN = "/engine/default/user/([a-z.]+)/profile";
    private final ObjectMapper objectMapper;
    private final Pattern pattern = Pattern.compile(ROUTE_PATTERN);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("EngineRestUserFilter called");

        if (servletRequest instanceof HttpServletRequest request && servletResponse instanceof HttpServletResponse response) {
            val path = request.getPathInfo();
            val matcher = pattern.matcher(path);
            if (!matcher.matches()) {
                log.debug("Skipped filter");
                filterChain.doFilter(servletRequest, servletResponse);
            }
            val username = matcher.group(1);
            log.debug("Asking profile for user {}", username);

            val payload = OptimizeUserDto.builder()
                    .id(username)
                    .build();
            log.info("Resolved user {} to: {}", username, payload);

            response.setStatus(200);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), payload);
        } else {
            log.debug("Skipped filter");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
