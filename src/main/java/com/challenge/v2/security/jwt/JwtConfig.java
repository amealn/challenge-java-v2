package com.challenge.v2.security.jwt;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtConfig {
	
	private static final String KEY_ALIAS = "selfsigned";
	private static final String KEYSTORE_TYPE = "PKCS12";
    
	@Value("${JWT_PRIVATE_KEY_FILE:#{null}}")
    private String jwtPrivateKeyFile;

    @Value("${JWT_PUBLIC_KEY_FILE:#{null}}")
    private String jwtPublicKeyFile;
    
    @Value("${KEYSTORE_PATH}")
    private String keystorePath;

    @Value("${KEYSTORE_PASSWORD}")
    private String keystorePassword;
    
    private final ResourceLoader resourceLoader;
    
    public JwtConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) getPublicKeyFromKeystore()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
    	RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) getPublicKeyFromKeystore())
                .privateKey((RSAPrivateKey) getPrivateKeyFromKeystore())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }
    
    private RSAPrivateKey getPrivateKeyFromKeystore() throws Exception {
        KeyStore keyStore = loadKeystore();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, keystorePassword.toCharArray());
        return (RSAPrivateKey) privateKey;
    }

    private RSAPublicKey getPublicKeyFromKeystore() throws Exception {
        KeyStore keyStore = loadKeystore();
        Certificate certificate = keyStore.getCertificate(KEY_ALIAS);
        return (RSAPublicKey) certificate.getPublicKey();
    }

    private KeyStore loadKeystore() throws Exception {
        Resource resource = resourceLoader.getResource(keystorePath);
        InputStream inputStream = resource.getInputStream();
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        keyStore.load(inputStream, keystorePassword.toCharArray());
        return keyStore;
    }
    
    
}