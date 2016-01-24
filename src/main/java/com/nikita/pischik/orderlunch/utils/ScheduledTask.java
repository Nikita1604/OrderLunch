package com.nikita.pischik.orderlunch.utils;

import com.nikita.pischik.orderlunch.model.User;
import com.nikita.pischik.orderlunch.notification.SimpleNotificationManager;
import com.nikita.pischik.orderlunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configurable
public class ScheduledTask {

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 11-11 * * MON-SUT")
    public void needOrderNotification() {
        SimpleNotificationManager simpleNotificationManager = new SimpleNotificationManager();
        List<User> users = userService.findAllUsers();
        simpleNotificationManager.sendNotificationMessage(users);
    }
}
