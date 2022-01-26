package io.github.hossensyedriadh.springbootjwtauthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"io.github.hossensyedriadh.springbootjwtauthentication.repository"})
@EntityScan(basePackages = {"io.github.hossensyedriadh.springbootjwtauthentication.entity"})
@SpringBootApplication
public class SpringBootJwtAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtAuthenticationApplication.class, args);
	}

}
