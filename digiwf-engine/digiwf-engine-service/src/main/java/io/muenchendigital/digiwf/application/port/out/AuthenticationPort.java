package io.muenchendigital.digiwf.application.port.out;

import java.util.List;

public interface AuthenticationPort {

    void setAuthentication(String userId, List<String> groups);

    void clearAuthentication();

}
