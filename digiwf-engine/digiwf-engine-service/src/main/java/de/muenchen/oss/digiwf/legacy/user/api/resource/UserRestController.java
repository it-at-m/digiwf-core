/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.user.api.resource;

import de.muenchen.oss.digiwf.legacy.user.api.mapper.UserTOMapper;
import de.muenchen.oss.digiwf.legacy.user.api.transport.SearchUserTO;
import de.muenchen.oss.digiwf.legacy.user.api.transport.UserTO;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.shared.security.AppAuthenticationProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API to get users.
 *
 * @author externer.dl.horn
 */
@RestController
@Transactional
@RequestMapping("/rest/user")
@RequiredArgsConstructor
@Tag(name = "UserRestController", description = "API to get users")
public class UserRestController {

    private final UserService userService;
    private final AppAuthenticationProvider userAuthenticationProvider;

    //Mapper
    private final UserTOMapper userMapper;

    /**
     * Search users for the given parameters.
     *
     * @param searchUserTO Search information
     * @return users
     */
    @PostMapping("/search")
    public ResponseEntity<List<UserTO>> getUsers(@RequestBody final SearchUserTO searchUserTO) {
        val users = this.userService.searchUser(searchUserTO.getSearchString(), searchUserTO.getOus());
        return ResponseEntity.ok(this.userMapper.map2TO(users));
    }

    /**
     * Get the logged in user.
     *
     * @return user
     */
    @GetMapping("/info")
    public ResponseEntity<UserTO> userinfo() {
        val user = this.userService.getUser(this.userAuthenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    /**
     * Get user by id.
     *
     * @param id Id of the user
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserTO> getUser(@PathVariable("id") final String id) {
        val user = this.userService.getUser(id);
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

    /**
     * Get user by username.
     *
     * @param username
     * @return user
     */
    @GetMapping("/uid/{username}")
    public ResponseEntity<UserTO> getUserByUsername(@PathVariable("username") final String username) {
        val user = this.userService.getUserByUserName(username).get();
        return ResponseEntity.ok(this.userMapper.map2TO(user));
    }

}

