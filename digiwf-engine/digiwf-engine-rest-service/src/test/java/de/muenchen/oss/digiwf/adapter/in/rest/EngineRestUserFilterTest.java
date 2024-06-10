package de.muenchen.oss.digiwf.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.adapter.out.engine.EngineAdapter;
import de.muenchen.oss.digiwf.adapter.out.ldap.LdapMockAdapter;
import de.muenchen.oss.digiwf.application.usecase.ResolveUserUseCase;
import de.muenchen.oss.digiwf.domain.Group;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ObjectMapper.class})
class EngineRestUserFilterTest {
    private final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    private final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
    private final FilterChain filterChain = mock(FilterChain.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testMock() throws ServletException, IOException {
        when(servletRequest.getPathInfo()).thenReturn("/engine/default/user/johndoe/profile");
        val writer = new StringWriter();
        when(servletResponse.getWriter()).thenReturn(new PrintWriter(writer));
        val engineOutPort = mock(EngineAdapter.class);
        when(engineOutPort.getOptimizeAuthorizedGroups()).thenReturn(List.of(new Group("digiwf-webapp-user")));
        val ldapOutPort = new LdapMockAdapter();
        val inPort = new ResolveUserUseCase(engineOutPort, ldapOutPort, ldapOutPort);
        val engineRestGroupFilter = new EngineRestUserFilter(objectMapper, inPort);
        // call
        engineRestGroupFilter.doFilter(servletRequest, servletResponse, filterChain);
        // test
        verify(servletResponse, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(servletResponse, times(1)).setContentType("application/json");
        assertTrue(writer.toString().contains("johndoe"));
    }

    @Test
    void testMockNotFound() throws ServletException, IOException {
        when(servletRequest.getPathInfo()).thenReturn("/engine/default/user/test/profile");
        val writer = new StringWriter();
        when(servletResponse.getWriter()).thenReturn(new PrintWriter(writer));
        val engineOutPort = mock(EngineAdapter.class);
        when(engineOutPort.getOptimizeAuthorizedGroups()).thenReturn(List.of(new Group("digiwf-webapp-user")));
        val ldapOutPort = new LdapMockAdapter();
        val inPort = new ResolveUserUseCase(engineOutPort, ldapOutPort, ldapOutPort);
        val engineRestGroupFilter = new EngineRestUserFilter(objectMapper, inPort);
        // call
        engineRestGroupFilter.doFilter(servletRequest, servletResponse, filterChain);
        // test
        verify(servletResponse, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}