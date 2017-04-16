package org.manuel.teambuilting.players.controllers.query;

import com.auth0.spring.security.api.Auth0JWTToken;
import org.manuel.teambuilting.players.config.Auth0Client;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Manuel Doncel Martos on 11/12/2016.
 */
@RestController
@RequestMapping("/players/user")
public class UserQueryController {

    private final UserService userService;
    private final Auth0Client auth0Client;

    @Inject
    public UserQueryController(final UserService userService, final Auth0Client auth0Client) {
        this.userService = userService;
        this.auth0Client = auth0Client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public UserData getUserData() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String userId = auth0Client.getUser((Auth0JWTToken) auth).getId();
        return userService.getOrCreateUserData(userId);
    }

}
