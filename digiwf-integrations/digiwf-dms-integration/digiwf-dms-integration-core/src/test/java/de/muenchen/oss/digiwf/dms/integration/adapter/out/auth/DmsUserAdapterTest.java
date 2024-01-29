package de.muenchen.oss.digiwf.dms.integration.adapter.out.auth;

import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

class DmsUserAdapterTest {

    private final UserAuthenticationProvider userAuthenticationProvider = mock(UserAuthenticationProvider.class);

    private final DmsUserAdapter dmsUserAdapter = new DmsUserAdapter(userAuthenticationProvider);

    @Test
    void getDmsUser() {

        when(userAuthenticationProvider.getLoggedInUser()).thenReturn("user");

        String user = dmsUserAdapter.getDmsUser();

        assertThat(user).isEqualTo("user");

    }
}