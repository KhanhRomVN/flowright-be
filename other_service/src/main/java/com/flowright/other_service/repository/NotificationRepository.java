package com.flowright.other_service.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.flowright.other_service.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByMemberIdOrderByCreatedAtDesc(UUID memberId, Pageable pageable);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.memberId = :memberId AND n.id = :notificationId")
    void updateIsReadByMemberIdAndId(UUID memberId, UUID notificationId);
}
