package de.muenchen.oss.digiwf.process;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class UserMock {
    private String firstName;
    private String lastName;
    public String lastname(String userId) {
        return lastName;
    }
    public String firstname(String userId) {
        return firstName;
    }
}
