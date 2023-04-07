package com.meowmed.rdanotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationService nService;
    public NotificationController(NotificationService notificationService) {
        this.nService = notificationService;
    }

    @PostMapping("/customernotification")
    public int postNotificationCustomer() {
        return nService.customerNotification();

    }

    @PostMapping("/policynotification")
    public int postNotificationPolicy() {
        return nService.policyNotification();
    }

    
}
