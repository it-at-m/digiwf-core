package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.adapter.out.ldap.LdapMockAdapter;
import de.muenchen.oss.digiwf.application.usecase.ResolveUserGroupsUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ObjectMapper.class})
class EngineRestGroupFilterTest {
    private final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    private final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    private final FilterChain filterChain = mock(FilterChain.class);

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        when(servletRequest.getParameterMap()).thenReturn(Map.of(
                "member", new String[]{"test.user"}
        ));
    }

    @Test
    void testMock() throws ServletException, IOException {
        val writer = new StringWriter();
        when(servletResponse.getWriter()).thenReturn(new PrintWriter(writer));
        val outPort = new LdapMockAdapter();
        val inPort = new ResolveUserGroupsUseCase(outPort);
        val engineRestGroupFilter = new EngineRestGroupFilter(objectMapper, inPort);
        // call
        engineRestGroupFilter.doFilter(servletRequest, servletResponse, filterChain);
        // test
        verify(servletResponse, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(servletResponse, times(1)).setContentType("application/json");
        assertTrue(writer.toString().contains("digiwf-webapp-user"));
    }
}
