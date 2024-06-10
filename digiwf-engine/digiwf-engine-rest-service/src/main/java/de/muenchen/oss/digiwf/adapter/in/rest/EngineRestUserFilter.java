package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.application.port.in.ResolveUserInPort;
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

    public static final String ROUTE_PATTERN = "/engine/default/user/([\\w.]+)/profile";
    private final ObjectMapper objectMapper;
    private final Pattern pattern = Pattern.compile(ROUTE_PATTERN);
    private final ResolveUserInPort resolveUserInPort;

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
                log.warn("Request to user endpoint not matching profile path");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            val username = matcher.group(1);
            log.debug("Asking profile for user {}", username);

            val payload = resolveUserInPort.resolveUser(username);
            log.info("Resolved user {} to: {}", username, payload);
            if (payload == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                objectMapper.writeValue(response.getWriter(), payload);
            }
        } else {
            log.debug("Skipped filter");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
