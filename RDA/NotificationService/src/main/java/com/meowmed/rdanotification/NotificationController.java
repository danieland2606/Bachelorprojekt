package com.meowmed.rdanotification;

import com.meowmed.rdanotification.Email.MailCustomerEntity;
import com.meowmed.rdanotification.Email.MailPolicyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationService nService;
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.nService = notificationService;
    }

    @PostMapping("/customernotification")
    public String postNotificationCustomer(@RequestBody MailCustomerEntity details) {
        return nService.customerNotification(details);
    }

    @PostMapping("/policynotification")
    public String postNotificationPolicy(@RequestBody MailPolicyEntity details) {
        //System.out.println(details);
        return nService.policyNotification(details);
    }
}
