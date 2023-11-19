package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchSubjectAreaPort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchSubjectAreaServiceTest {

    private final SearchSubjectAreaPort searchSubjectAreaPort = mock(SearchSubjectAreaPort.class);

    private final SearchSubjectAreaService searchSubjectAreaService = new SearchSubjectAreaService(searchSubjectAreaPort);

    @Test
    void searchFile() {

        when(searchSubjectAreaPort.searchSubjectArea(any(), any())).thenReturn(List.of("coo"));

        searchSubjectAreaService.searchSubjectArea("searchString", "user");

        verify(this.searchSubjectAreaPort, times(1)).searchSubjectArea("searchString", "user");
    }


}
