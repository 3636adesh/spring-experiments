//package com.example.rest;
//
//import com.example.common.AbstractIntegrationTest;
//import com.example.entities.Gender;
//import com.example.entities.User;
//import com.example.model.request.UserRequest;
//import com.example.repo.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class UserControllerIT extends AbstractIntegrationTest {
//
//    @Autowired private UserRepository userRepository;
//
//    private List<User> userList = null;
//
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAllInBatch();
//
//        userList = new ArrayList<>();
//        userList.add(new User(null, "First User", "Last Name", 30, Gender.MALE, "9848022334"));
//        userList.add(new User(null, "Second User", "Last Name", 20, Gender.FEMALE, "9848022334"));
//        userList.add(new User(null, "Third User", "Last Name", 30, Gender.MALE, "9848022334"));
//        userList = userRepository.saveAll(userList);
//    }
//
//    @Test
//    void shouldFetchAllUsers() throws Exception {
//        this.mockMvc
//                .perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.size()", is(userList.size())))
//                .andExpect(jsonPath("$.totalElements", is(3)))
//                .andExpect(jsonPath("$.pageNumber", is(1)))
//                .andExpect(jsonPath("$.totalPages", is(1)))
//                .andExpect(jsonPath("$.isFirst", is(true)))
//                .andExpect(jsonPath("$.isLast", is(true)))
//                .andExpect(jsonPath("$.hasNext", is(false)))
//                .andExpect(jsonPath("$.hasPrevious", is(false)));
//    }
//
//    @Test
//    void shouldFindUserById() throws Exception {
//        User user = userList.getFirst();
//        Long userId = user.getId();
//
//        this.mockMvc
//                .perform(get("/api/users/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(user.getId()), Long.class))
//                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
//                .andExpect(jsonPath("$.age", is(user.getAge())))
//                .andExpect(jsonPath("$.gender", is(user.getGender().name())))
//                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
//    }
//
//    @Test
//    void shouldCreateNewUser() throws Exception {
//        UserRequest userRequest =
//                new UserRequest("New User", "Last Name", 30, Gender.FEMALE, "test@gmail.com","9848022334");
//        this.mockMvc
//                .perform(
//                        post("/api/users")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", notNullValue()))
//                .andExpect(jsonPath("$.firstName", is(userRequest.firstName())))
//                .andExpect(jsonPath("$.lastName", is(userRequest.lastName())))
//                .andExpect(jsonPath("$.age", is(userRequest.age())))
//                .andExpect(jsonPath("$.gender", is(userRequest.gender().name())))
//                .andExpect(jsonPath("$.phoneNumber", is(userRequest.phoneNumber())));
//    }
//
//    @Test
//    void shouldReturn400WhenCreateNewUserWithoutFirstName() throws Exception {
//        UserRequest userRequest = new UserRequest(null, "Last Name", 0, Gender.MALE,"test@gmail.com", "9848022334");
//
//        this.mockMvc
//                .perform(
//                        post("/api/users")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(header().string("Content-Type", is("application/problem+json")))
//                .andExpect(jsonPath("$.type", is("about:blank")))
//                .andExpect(jsonPath("$.title", is("Constraint Violation")))
//                .andExpect(jsonPath("$.status", is(400)))
//                .andExpect(jsonPath("$.detail", is("Invalid request content.")))
//                .andExpect(jsonPath("$.instance", is("/api/users")))
//                .andExpect(jsonPath("$.violations", hasSize(2)))
//                .andExpect(jsonPath("$.violations[0].field", is("age")))
//                .andExpect(jsonPath("$.violations[0].message", is("Age must be greater than 0")))
//                .andExpect(jsonPath("$.violations[1].field", is("firstName")))
//                .andExpect(jsonPath("$.violations[1].message", is("FirstName can't be blank")))
//                .andReturn();
//    }
//
//    @Test
//    void shouldUpdateUser() throws Exception {
//        User user = userList.getFirst();
//        UserRequest userRequest =
//                new UserRequest("Updated User", "Last Name", 50, Gender.FEMALE,"test@gmail.com", "9848022334");
//
//        this.mockMvc
//                .perform(
//                        put("/api/users/{id}", user.getId())
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(userRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(user.getId()), Long.class))
//                .andExpect(jsonPath("$.firstName", is(userRequest.firstName())))
//                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
//                .andExpect(jsonPath("$.age", is(userRequest.age())))
//                .andExpect(jsonPath("$.gender", is(userRequest.gender().name())))
//                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
//    }
//
//    @Test
//    void shouldDeleteUser() throws Exception {
//        User user = userList.getFirst();
//
//        this.mockMvc
//                .perform(delete("/api/users/{id}", user.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(user.getId()), Long.class))
//                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
//                .andExpect(jsonPath("$.age", is(user.getAge())))
//                .andExpect(jsonPath("$.gender", is(user.getGender().name())))
//                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
//    }
//}
