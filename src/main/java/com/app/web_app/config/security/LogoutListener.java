package com.app.web_app.config.security;

import com.app.web_app.service.LoggedUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoutListener implements ApplicationListener <SessionDestroyedEvent> {

    @Autowired
    private LoggedUsersService loggedUsersService;


    @Override
    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {
        loggedUsersService.updateLoggedUsers();
    }
}
