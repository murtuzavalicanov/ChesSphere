package com.chessphere.notification.entity;

import com.chessphere.notification.enums.NotificationType;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private UUID receiverId;
    private UUID senderId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;   // enum istifadə et

    private String title;
    private String message;

    private String referenceId;
    // məsələn matchId, postId və s. (nəyə bağlıdır)

    private LocalDateTime createdAt;
}
