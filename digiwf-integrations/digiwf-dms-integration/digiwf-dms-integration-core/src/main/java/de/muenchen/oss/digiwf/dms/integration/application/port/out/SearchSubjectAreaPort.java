package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import java.util.List;

public interface SearchSubjectAreaPort {
    List<String> searchSubjectArea(String searchString, String user);
}
