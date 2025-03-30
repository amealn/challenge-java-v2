package com.challenge.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableCaching
public class AppMain {
	
	public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }

    /*public static void main(String[] args) {
        
        Map<String, String> secrets = new HashMap<>();
        secrets.put("mongo_connection_string", readSecret("/run/secrets/mongo_connection_string"));
        secrets.put("keystore_password", readSecret("/run/secrets/keystore_password"));
        secrets.put("jwt_private_key", readSecret("/run/secrets/jwt_private_key"));
        secrets.put("jwt_public_key", readSecret("/run/secrets/jwt_public_key"));
        
        // Log the secrets
        System.out.println("--- Secret Values ---");
        secrets.forEach((key, value) -> System.out.println(key + ": [" + value + "]"));
        System.out.println("---------------------");
    	
        String password = readSecret("/run/secrets/keystore_password");
        System.out.println("SSL Keystore Password: " + password);
        
        try {
            // Load the keystore directly
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (FileInputStream fis = new FileInputStream("/app/keystore.p12")) {
                keyStore.load(fis, password.toCharArray());
            }

            // Create KeyManagerFactory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());

            // Create TrustManagerFactory (if needed)
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Create SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            // Set the SSLContext as the default context
            SSLContext.setDefault(sslContext);

        } catch (Exception e) {
            System.err.println("Error loading keystore: " + e.getMessage());
            e.printStackTrace();
            // Optionally, exit the application if keystore loading fails
            // System.exit(1);
        }
        
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AppMain.class);
        builder.run(args);
    }
    
    private static String readSecret(String secretPath) {
        try {
            return Files.readString(Paths.get(secretPath)).replaceAll("\\n", "").replaceAll("\\r", "");
        } catch (IOException e) {
            System.err.println("Error reading secret from " + secretPath + ": " + e.getMessage());
            return null;
        }
    }*/

}