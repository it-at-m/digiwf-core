package de.muenchen.oss.digiwf.task.service.adapter.out.user;

import de.muenchen.oss.digiwf.task.service.adapter.out.user.easyldap.EasyLdapClient;
import de.muenchen.oss.digiwf.task.service.adapter.out.user.easyldap.UserInfoResponse;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserNotFoundException;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserProfilePort;
import de.muenchen.oss.digiwf.task.service.domain.UserProfile;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LdapUserProfileAdapterTest {

  private final EasyLdapClient easyLdapClient = Mockito.mock(EasyLdapClient.class);
  private final UserProfilePort userProfilePort = new LdapUserProfileAdapter(easyLdapClient);


  @Test
  public void retrieve_profile() {
    val userId = "1234";
    when(easyLdapClient.getUserById(any())).thenReturn(new UserInfoResponse(userId, "OU", "Path", "First", "Last"));
    UserProfile user = userProfilePort.findUser(userId);
    assertThat(user).isNotNull();
    assertThat(user.getUserId()).isEqualTo(userId);
    assertThat(user.getFirstName()).isEqualTo("First");
    assertThat(user.getLastName()).isEqualTo("Last");
    assertThat(user.getPrimaryOrgUnit()).isEqualTo("OU");

    verify(easyLdapClient).getUserById(userId);
    verifyNoMoreInteractions(easyLdapClient);
  }

  @Test
  public void fail_to_find_unknown_user() {
    val userId = "unknown-id";
    var request = Request.create(Request.HttpMethod.GET, "url",
        new HashMap<>(), null, new RequestTemplate());
    when(easyLdapClient.getUserById(any())).thenThrow(new FeignException.NotFound("", request, null, null));

    val exception = assertThrows(UserNotFoundException.class, () -> userProfilePort.findUser(userId));
    assertThat(exception.getMessage()).isEqualTo("User with id " + userId + " could not be found.");

    verify(easyLdapClient).getUserById(userId);
    verifyNoMoreInteractions(easyLdapClient);
  }


}
