package asset.ledger.assetledgerserver.fcm.application.service;

import asset.ledger.assetledgerserver.fcm.domain.entity.FCMDeviceToken;
import asset.ledger.assetledgerserver.fcm.domain.repository.FCMDeviceTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMServiceImpl implements FCMService {

    private final FCMDeviceTokenRepository fcmDeviceTokenRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${fcm.server.fcm-url}")
    private String FCM_API_URL;

    @Value("${fcm.server.firebase-server-credentials-path}")
    private String FIREBASE_SERVER_CREDENTIALS_PATH;

    public void requestFCMPush(String fcmDeviceToken, String title, String body) throws IOException {
        try {
            Map<String, Object> message = createFCMMessage(fcmDeviceToken, title, body);

            webClient.post()
                    .uri(FCM_API_URL)
                    .header("Authorization", "Bearer " + getAccessToken())
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .bodyValue(objectMapper.writeValueAsString(message))
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> log.info("FCM Response: {}", response))
                    .doOnError(error -> log.error("FCM Error", error))
                    .subscribe();

        } catch (Exception e) {
            log.error("FCM Push Send Error", e);
        }
    }

    private Map<String, Object> createFCMMessage(String targetDeviceToken, String title, String body) { // JsonParseException, JsonProcessingException
        Map<String, Object> notification = new HashMap<>();
        notification.put("title", title);
        notification.put("body", body);

        Map<String, Object> message = new HashMap<>();
        message.put("to", targetDeviceToken);
        message.put("priority", "high");
        message.put("notification", notification);

        return message;
    }

    private String getAccessToken() throws IOException {
        try {
            final GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FIREBASE_SERVER_CREDENTIALS_PATH).getInputStream())
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));

            googleCredentials.refreshIfExpired();
            log.info("access token: {}", googleCredentials.getAccessToken());
            return googleCredentials.getAccessToken().getTokenValue();

        } catch (IOException e) {
            throw new IOException("GoogleCredentials Error");
        }
    }

    @Override
    public void saveFCMDeviceToken(final String userId, final String token) {
        FCMDeviceToken fcmDeviceToken = fcmDeviceTokenRepository.getFCMDeviceTokenByUserId(userId);

        if (fcmDeviceToken != null) {
            updateFCMDeviceToken(fcmDeviceToken, token);
            return;
        }

        FCMDeviceToken newFcmDeviceToken = FCMDeviceToken
                .builder()
                .userId(userId)
                .fcmDeviceToken(token)
                .build();

        fcmDeviceTokenRepository.save(newFcmDeviceToken);
    }

    private void updateFCMDeviceToken(FCMDeviceToken fcmDeviceToken, final String token) {
        if (!fcmDeviceToken.getFcmDeviceToken().equals(token)) {
            fcmDeviceToken.updateFCMDeviceToken(token);
        }
    }
}
