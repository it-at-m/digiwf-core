package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.application.port.in.ResolveUserGroupsInPort;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MultivaluedHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class EngineRestGroupFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final ResolveUserGroupsInPort resolveUserGroupsInPort;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("EngineRestGroupFilter called");

        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse response) {
            var params = new HashMap<String, String>();
            servletRequest.getParameterMap().forEach((key, values) -> params.put(key, values[0]));
            var queryDto = new OptimizeGroupQueryDto(objectMapper, new MultivaluedHashMap<>(params));
            var username = queryDto.getMember();
            log.debug("Asking membership for user: {}", username);

            var payload = resolveUserGroupsInPort
                    .resolveGroups(username)
                    .stream()
                    .map(OptimizeGroupDto::fromGroup)
                    .collect(Collectors.toList());
            log.info("Resolved user {} to groups: {}", username, payload);

            response.setStatus(200);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), payload);
        } else {
            log.debug("Skipped filter");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}
