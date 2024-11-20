package com.flowright.other_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowright.other_service.entity.Notification;
import com.flowright.other_service.service.JwtService;
import com.flowright.other_service.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/other/service/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtService jwtService;
    // get notifications by member id: /other/service/notifications?page=0
    @GetMapping
    public List<Notification> getNotifications(
            @RequestHeader("access_token") String token, @RequestParam(defaultValue = "0") int page) {
        UUID memberId = jwtService.extractMemberId(token);
        return notificationService.getNotificationsByMemberId(memberId, PageRequest.of(page, 10));
    }

    @PutMapping
    public void readNotification(@RequestHeader("access_token") String token, @RequestParam UUID notificationId) {
        UUID memberId = jwtService.extractMemberId(token);
        notificationService.readNotification(memberId, notificationId);
    }
}
