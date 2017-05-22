package org.manuel.teambuilting.players.controllers.query;

import com.auth0.Auth0User;
import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Doncel Martos on 11/12/2016.
 */
@RestController
@RequestMapping("/players/user")
@AllArgsConstructor
public class UserQueryController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public UserData getUserData() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Auth0User user = (Auth0User) auth.getPrincipal();
        return userService.getOrCreateUserData(user.getUserId());
    }

}
