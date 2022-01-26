package io.github.hossensyedriadh.springbootjwtauthentication;

import io.github.hossensyedriadh.springbootjwtauthentication.entity.RefreshToken;
import io.github.hossensyedriadh.springbootjwtauthentication.entity.UserData;
import io.github.hossensyedriadh.springbootjwtauthentication.enumerator.Authority;
import io.github.hossensyedriadh.springbootjwtauthentication.repository.RefreshTokenRepository;
import io.github.hossensyedriadh.springbootjwtauthentication.repository.UserDataRepository;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataJpaTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Order(1)
    @Test
    public void should_persist_userdata() {
        UserData userData = new UserData();
        userData.setUsername("spy");
        userData.setPassword("espionage");
        userData.setAuthority(Authority.ROLE_ADMINISTRATOR);
        userData.setEnabled(true);

        entityManager.persist(userData);
        assert userDataRepository.findById(userData.getUsername()).isPresent();
    }

    @Order(2)
    @Test
    public void should_persist_refreshtoken() {
        UserData userData = new UserData();
        userData.setUsername("spy");
        userData.setPassword("espionage");
        userData.setAuthority(Authority.ROLE_ADMINISTRATOR);
        userData.setEnabled(true);

        entityManager.persist(userData);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(UUID.randomUUID().toString());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(userData);

        entityManager.persist(refreshToken);

        assert refreshTokenRepository.findById(refreshToken.getId()).isPresent();
    }
}
