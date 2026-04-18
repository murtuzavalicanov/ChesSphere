package com.chessphere.notification.message.consumer;

import com.chessphere.notification.message.dto.MatchCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "match-created-topic",
            groupId = "notification-group-v2"
    )
    public void consume(String message) {
        try {
            log.info("RAW MESSAGE: {}", message);

            MatchCreatedEvent event =
                    objectMapper.readValue(message, MatchCreatedEvent.class);

            log.info("Parsed event: {}", event);

            // TODO: notification göndər (email/websocket və s.)

        } catch (Exception e) {
            log.error("Failed to parse Kafka message", e);
        }
    }
}
