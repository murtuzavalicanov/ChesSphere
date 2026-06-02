package com.chessphere.match.message.producer;

import com.chessphere.match.message.dto.MatchCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.chessphere.match.mapper.MatchMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchProducer {

    private final MatchMapper matchMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMatchCreatedEvent(MatchCreatedEvent matchCreatedEvent) {
        log.info("Sending Match Created Event: " + matchCreatedEvent);
        kafkaTemplate.send("match-created-topic", matchCreatedEvent);
        log.info("Match Created Event sent to topic successfully");
    }
}
