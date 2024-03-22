package de.muenchen.oss.digiwf.dms.integration.application.usecase;

import de.muenchen.oss.digiwf.dms.integration.application.port.out.SearchSubjectAreaOutPort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchSubjectAreaUseCaseTest {

    private final SearchSubjectAreaOutPort searchSubjectAreaOutPort = mock(SearchSubjectAreaOutPort.class);

    private final SearchSubjectAreaUseCase searchSubjectAreaUseCase = new SearchSubjectAreaUseCase(searchSubjectAreaOutPort);

    @Test
    void searchSubject() {

        when(searchSubjectAreaOutPort.searchSubjectArea(any(), any())).thenReturn(List.of("coo"));

        searchSubjectAreaUseCase.searchSubjectArea("searchString", "user");

        verify(this.searchSubjectAreaOutPort, times(1)).searchSubjectArea("searchString", "user");
    }


}
