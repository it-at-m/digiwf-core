package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyldap;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

@SpringBootTest
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
public class EasyLdapClientTest {

    @Autowired
    EasyLdapClient easyLdapClient;

    @Test
    public void getEmptyObject () {

        System.out.println(easyLdapClient.getUserById("123456789101112"));


    }

}
