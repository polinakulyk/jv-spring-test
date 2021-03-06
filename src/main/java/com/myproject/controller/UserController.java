package com.myproject.controller;

import com.myproject.dto.UserResponseDto;
import com.myproject.model.User;
import com.myproject.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/inject")
    public String injectMockData() {
        User first = new User("Mercury", "firstToSun@universe.io");
        userService.add(first);

        User second = new User("Venus", "secondToSun@universe.io");
        userService.add(second);

        User third = new User("Earth", "thirdToSun@universe.io");
        userService.add(third);

        User forth = new User("Mars", "forthToSun@universe.io");
        userService.add(forth);

        return "Success: stars have aligned and warriors are here...";
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return getUserDto(userService.get(id));
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.listUsers()
                .stream()
                .map(this::getUserDto)
                .collect(Collectors.toList());
    }

    private UserResponseDto getUserDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setLogin(user.getLogin());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }
}
