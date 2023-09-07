package de.muenchen.oss.digiwf.alw.integration.application;

import de.muenchen.oss.digiwf.alw.integration.adapter.out.orgstructure.OrgStructureMapperAdapter;
import de.muenchen.oss.digiwf.alw.integration.application.port.in.GetResponsibilityInPort;
import de.muenchen.oss.digiwf.alw.integration.application.port.out.AlwResponsibilityOutPort;
import de.muenchen.oss.digiwf.alw.integration.domain.exception.AlwException;
import de.muenchen.oss.digiwf.alw.integration.domain.model.ResponsibilityRequest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class GetResponsibilityUseCaseTest {

  private final Map<String, String> sachbearbeiterMap = new HashMap<>();

  private final AlwResponsibilityOutPort alwResponsibilityMock = mock(AlwResponsibilityOutPort.class);

  private final GetResponsibilityInPort getResponsibility = new GetResponsibilityUseCase(
      new OrgStructureMapperAdapter(sachbearbeiterMap),
      alwResponsibilityMock
  );

  @BeforeEach
  void init_mapping() {
    sachbearbeiterMap.put("SUB2", "OU1");
  }

  @Test
  void finds_responsible() throws Exception {
    when(alwResponsibilityMock.getResponsibleSachbearbeiter(any())).thenReturn(Optional.of("SUB2"));
    val responsibility = getResponsibility.getResponsibility(ResponsibilityRequest.builder().azrNummer("123456789012").build());
    assertThat(responsibility.getOrgUnit()).isEqualTo("OU1");
    verify(alwResponsibilityMock).getResponsibleSachbearbeiter("123456789012");
  }

  @Test
  void finds_responsible_without_mapping() throws Exception {
    when(alwResponsibilityMock.getResponsibleSachbearbeiter(any())).thenReturn(Optional.of("UNKNOWN"));
    val responsibility = getResponsibility.getResponsibility(ResponsibilityRequest.builder().azrNummer("123456789012").build());
    assertThat(responsibility.getOrgUnit()).isNull();
    verify(alwResponsibilityMock).getResponsibleSachbearbeiter("123456789012");
  }

  @Test
  void fails_to_find_responsible() {
    when(alwResponsibilityMock.getResponsibleSachbearbeiter(any())).thenReturn(Optional.empty());
    val exception = assertThrows(AlwException.class,
        () -> getResponsibility.getResponsibility(ResponsibilityRequest.builder().azrNummer("123456789012").build())
    );
    assertThat(exception.getMessage()).isEqualTo("Could not find ALW responsible for 123456789012");
  }

  @Test
  void fails_to_find_responsible_broken_server() {
    when(alwResponsibilityMock.getResponsibleSachbearbeiter(any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "server error"));
    val exception = assertThrows(HttpClientErrorException.class,
        () -> getResponsibility.getResponsibility(ResponsibilityRequest.builder().azrNummer("123456789012").build())
    );
    assertThat(exception.getMessage()).isEqualTo("500 server error");
  }

}