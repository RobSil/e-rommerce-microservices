package com.robsil.authorizationservice.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@Service
@Log4j2
public class RSAHolder {

    // todo: inject it as property
    private final String APP_MODE = "test";

    private final KeyPair RSA_KEY_PAIR;
    private final RSAPublicKey RSA_PUBLIC_KEY;
    private final RSAPrivateKey RSA_PRIVATE_KEY;

    public RSAHolder() {
        var keyPair = this.generateRsaKey();
        this.RSA_KEY_PAIR = keyPair;
        this.RSA_PUBLIC_KEY = (RSAPublicKey) keyPair.getPublic();
        this.RSA_PRIVATE_KEY = (RSAPrivateKey) keyPair.getPrivate();
        if (APP_MODE.equals("test")) {
            log.info("RSAHolder has successfully generated keys.");
            log.info("RSA PublicKey: " + Base64.getEncoder().encodeToString(RSA_PUBLIC_KEY.getEncoded()));
            log.info("RSA PrivateKey: " + Base64.getEncoder().encodeToString(RSA_PRIVATE_KEY.getEncoded()));
        }
    }

    public KeyPair getKeyPair() {
        return this.RSA_KEY_PAIR;
    }

    public RSAPublicKey getPublicKey() {
        return this.RSA_PUBLIC_KEY;
    }

    public RSAPrivateKey getPrivateKey() {
        return this.RSA_PRIVATE_KEY;
    }

    private KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
