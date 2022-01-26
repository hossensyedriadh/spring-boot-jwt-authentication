package io.github.hossensyedriadh.springbootjwtauthentication.repository;

import io.github.hossensyedriadh.springbootjwtauthentication.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}