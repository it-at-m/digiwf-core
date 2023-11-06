package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchFileServiceTest {

    private final SearchFilePort searchFilePort = mock(SearchFilePort.class);

    private final SearchFileService searchFileService = new SearchFileService(searchFilePort);

    @Test
    void searchFile() {

        when(searchFilePort.searchFile(any(), any())).thenReturn(List.of(new File("coo", "title")));

        searchFileService.searchFile("searchString", "user");

        verify(this.searchFilePort, times(1)).searchFile("searchString", "user");
    }


}
