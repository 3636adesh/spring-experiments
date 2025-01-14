package com.example.controllers;


import com.example.config.logging.Loggable;
import com.example.entities.User;
import com.example.model.request.UserRequest;
import com.example.model.response.PagedResult;
import com.example.services.UserService;
import com.example.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Loggable
public class UserController {

    private final UserService userService;

    @GetMapping
    public PagedResult<User> getAllUsers(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)
                    int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)
                    String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)
                    String sortDir) {
        return userService.findAllUsers(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService
                .findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService
                .findUserById(id)
                .map(userObj -> ResponseEntity.ok(userService.updateUser(userObj, userRequest)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        return userService
                .findUserById(id)
                .map(
                        user -> {
                            userService.deleteUserById(id);
                            return ResponseEntity.ok(user);
                        })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
