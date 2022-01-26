package io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.configuration;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Log4j
@Configuration
public class JwtConfiguration {
    @Value("${application.security.jwt.keystore.location}")
    private String keyStorePath;

    @Value("${application.security.jwt.keystore.password}")
    private String keyStorePassword;

    @Value("${application.security.jwt.key-alias}")
    private String keyAlias;

    @Value("${application.security.jwt.private-key-passphrase}")
    private String privateKeyPassword;

    @Bean
    public KeyStore keyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            keyStore.load(inputStream, keyStorePassword.toCharArray());

            return keyStore;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error("Exception occurred: {}", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
        try {
            Key key = keyStore.getKey(keyAlias, privateKeyPassword.toCharArray());

            if (key instanceof RSAPrivateKey) {
                return (RSAPrivateKey) key;
            }
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
            log.error("Exception occurred: {}", e);
        }
        throw new RuntimeException("Unable to load RSA Private Key");
    }

    @Bean
    public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
        try {
            Certificate certificate = keyStore.getCertificate(keyAlias);
            PublicKey publicKey = certificate.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
        } catch (KeyStoreException e) {
            log.error("Exception occurred: {}", e);
        }
        throw new RuntimeException("Unable to load RSA Public Key");
    }
}
