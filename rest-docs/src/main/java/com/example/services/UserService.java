package com.example.services;


import com.example.entities.User;
import com.example.model.request.UserRequest;
import com.example.model.response.PagedResult;
import com.example.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PagedResult<User> findAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort =
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> usersPage = userRepository.findAll(pageable);

        return new PagedResult<>(usersPage);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(UserRequest userRequest) {

        User user = mapUserRequestToUser(userRequest);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User userObj, UserRequest userRequest) {
        updateUserFromUserRequest(userRequest, userObj);
        return userRepository.save(userObj);
    }

    private User mapUserRequestToUser(UserRequest userRequest) {
        return new User(
                null,
                userRequest.firstName(),
                userRequest.lastName(),
                userRequest.email(),
                userRequest.age(),
                userRequest.gender(),
                userRequest.phoneNumber());
    }

    void updateUserFromUserRequest(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setAge(userRequest.age());
        user.setGender(userRequest.gender());
        user.setPhoneNumber(userRequest.phoneNumber());
    }
}
