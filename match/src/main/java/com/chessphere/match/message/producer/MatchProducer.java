package com.chessphere.match.message.producer;

import com.chessphere.match.dto.MatchRequestDto;
import com.chessphere.match.message.dto.MatchCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMatchCreatedEvent(MatchRequestDto match) {
        log.info("Sending Match Created Event");
        MatchCreatedEvent event = new MatchCreatedEvent(
                match.getId(),
                match.getBlackPlayerId(),
                match.getWhitePlayerId(),
                match.getGameType()
        );

        kafkaTemplate.send("match-created-topic", event);
        log.info("Match Created Event sent to topic successfully");
    }
}
