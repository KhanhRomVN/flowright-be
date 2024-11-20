package com.flowright.other_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.flowright.other_service.entity.Notification;
import com.flowright.other_service.exception.OtherException;
import com.flowright.other_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private final NotificationRepository notificationRepository;

    public List<Notification> getNotificationsByMemberId(UUID memberId, Pageable pageable) {
        return notificationRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable);
    }

    public void createNotification(
            UUID workspaceId, UUID memberId, String title, String detail, String uri, String type) {
        Notification notification = Notification.builder()
                .workspaceId(workspaceId)
                .memberId(memberId)
                .title(title)
                .detail(detail)
                .uri(uri)
                .type(type)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    public void readNotification(UUID memberId, UUID notificationId) {
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new OtherException("Notification not found", HttpStatus.NOT_FOUND));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
