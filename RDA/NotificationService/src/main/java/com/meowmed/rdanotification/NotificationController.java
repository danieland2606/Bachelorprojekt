package com.meowmed.rdanotification;

@RestController
public class NotificationController {

    private final NotificationService nService;
    @Autowired
    public NotificationController(NotificatinService notificatinService) {
        this.nService = notificatinService;
    }

    @PostMapping("/customernotification")
    public RequestStatusCode postNotificationCustomer() {
        nService.
        return 200
    }

    @PostMapping("/policynotification")
    public RequestStatusCode postNotificationPolicy() {
        return
    }

    
}
