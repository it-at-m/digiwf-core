package de.muenchen.oss.digiwf.process;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class UserMock implements Serializable {
    private String firstName;
    private String lastName;
    public String lastname(String dummyArg) {
        return lastName;
    }
    public String firstname(String dummyArg) {
        return firstName;
    }
}
