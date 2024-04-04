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
    private final RestMapper restMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Filter called");

        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse response) {
            var params = new HashMap<String, String>();
            servletRequest.getParameterMap().forEach((key, values) -> params.put(key, values[0]));
            var queryDto = new OptimizeGroupQueryDto(objectMapper, new MultivaluedHashMap<>(params));
            log.info("Asking membership for user: {}", queryDto.getMember());

            var payload = resolveUserGroupsInPort
                .resolveGroups(queryDto.getMember())
                .stream()
                .map(restMapper::toDto)
                .collect(Collectors.toList());

            response.setStatus(200);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(), payload);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }


}
