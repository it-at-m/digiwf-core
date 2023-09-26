package de.muenchen.oss.digiwf.process.api.config;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ConfigEntryTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.ProcessConfigTO;
import de.muenchen.oss.digiwf.process.api.config.api.dto.StatusConfigTO;
import de.muenchen.oss.digiwf.process.api.config.impl.ProcessConfigApiImpl;
import de.muenchen.oss.digiwf.process.api.config.impl.ProcessConfigClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ProcessConfigApiImplTest {

    private final ProcessConfigClient processConfigClient = mock(ProcessConfigClient.class);

    private final ProcessConfigApi processConfigApi = new ProcessConfigApiImpl(processConfigClient);

    @Test
    void testGetProcessConfig() {
        final String processDefinitionId = "123";
        final ProcessConfigTO expectedConfig = new ProcessConfigTO(
                "123",
                "myStatusDokument",
                List.of(new StatusConfigTO("start", "Start", 0), new StatusConfigTO("end", "Ende", 1)),
                List.of(new ConfigEntryTO("key", "value"))
        );

        when(processConfigClient.getProcessConfig(processDefinitionId)).thenReturn(expectedConfig);

        final ProcessConfigTO actualConfig = processConfigApi.getProcessConfig(processDefinitionId);

        Assertions.assertNotNull(actualConfig);
        Assertions.assertEquals(expectedConfig, actualConfig);

        verify(processConfigClient).getProcessConfig(processDefinitionId);
        verifyNoMoreInteractions(processConfigClient);
    }

}
