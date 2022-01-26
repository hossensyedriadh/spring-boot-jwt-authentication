package io.github.hossensyedriadh.springbootjwtauthentication.repository;

import io.github.hossensyedriadh.springbootjwtauthentication.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {
}