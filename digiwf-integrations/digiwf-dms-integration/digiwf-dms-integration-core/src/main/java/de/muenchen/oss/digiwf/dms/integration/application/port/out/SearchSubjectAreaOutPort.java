package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import java.util.List;

public interface SearchSubjectAreaOutPort {

    List<String> searchSubjectArea(String searchString, String user);
}
