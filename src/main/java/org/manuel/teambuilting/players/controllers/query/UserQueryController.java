package org.manuel.teambuilting.players.controllers.query;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.services.UserService;
import org.manuel.teambuilting.players.util.Util;
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
    private final Util util;

    @RequestMapping(method = RequestMethod.GET)
    public UserData getUserData() {
        final Auth0User user = util.getUserProfile().get();
        return userService.getOrCreateUserData(user.getUserId());
    }

}
