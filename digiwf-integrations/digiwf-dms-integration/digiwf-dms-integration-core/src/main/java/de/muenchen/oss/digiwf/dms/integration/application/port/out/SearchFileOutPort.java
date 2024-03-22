package de.muenchen.oss.digiwf.dms.integration.application.port.out;

import java.util.List;

public interface SearchFileOutPort {

    List<String> searchFile(String searchString, String user, String reference, String value);
}
