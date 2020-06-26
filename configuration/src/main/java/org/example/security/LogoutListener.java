package org.example.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutListener implements ApplicationListener <SessionDestroyedEvent> {

    private final LoggedUsersRegistry loggedUsersRegistry;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {
        loggedUsersRegistry.updateLoggedUsers();
    }
}
