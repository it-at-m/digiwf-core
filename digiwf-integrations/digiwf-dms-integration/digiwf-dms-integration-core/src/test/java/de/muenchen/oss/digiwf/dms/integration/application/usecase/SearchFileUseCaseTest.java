package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchFileOutPort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchFileUseCaseTest {

    private final SearchFileOutPort searchFileOutPort = mock(SearchFileOutPort.class);

    private final SearchFileUseCase searchFileUseCase = new SearchFileUseCase(searchFileOutPort);

    @Test
    void searchFile() {

        when(searchFileOutPort.searchFile(any(), any(), any(), any())).thenReturn(List.of("coo"));

        searchFileUseCase.searchFile("searchString", "user", "reference", "value");

        verify(this.searchFileOutPort, times(1)).searchFile("searchString", "user", "reference", "value");
    }

}
