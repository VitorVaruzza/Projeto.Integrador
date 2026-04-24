package com.example.Projeto.Integrador.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfig {

    @Bean
    public Firestore firestore(FirebaseProperties properties) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions.Builder options = FirebaseOptions.builder()
                    .setProjectId(properties.getProjectId());

            if (StringUtils.hasText(properties.getCredentialsPath())) {
                try (InputStream inputStream = new FileInputStream(properties.getCredentialsPath())) {
                    options.setCredentials(GoogleCredentials.fromStream(inputStream));
                }
            } else {
                options.setCredentials(GoogleCredentials.getApplicationDefault());
            }

            if (StringUtils.hasText(properties.getDatabaseUrl())) {
                options.setDatabaseUrl(properties.getDatabaseUrl());
            }

            FirebaseApp.initializeApp(options.build());
        }

        return FirestoreClient.getFirestore();
    }
}
