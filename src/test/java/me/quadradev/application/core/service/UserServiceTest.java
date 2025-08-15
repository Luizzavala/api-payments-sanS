package me.quadradev.application.core.service;

import me.quadradev.application.core.dto.UserDto;
import me.quadradev.application.core.dto.UserRequest;
import me.quadradev.application.core.mapper.UserMapper;
import me.quadradev.application.core.model.User;
import me.quadradev.application.core.model.UserStatus;
import me.quadradev.application.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUserEncodesPasswordAndReturnsDto() {
        UserRequest request = new UserRequest("user@example.com", "secret", "John", null, "Doe", null, UserStatus.ACTIVE);
        User user = User.builder().email(request.email()).password(request.password()).status(UserStatus.ACTIVE).build();
        User saved = User.builder().id(1L).email(request.email()).password("encoded").status(UserStatus.ACTIVE).build();
        UserDto dto = new UserDto(1L, request.email(), request.firstName(), null, request.lastName(), null, UserStatus.ACTIVE, Set.of());

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(userMapper.toEntity(request)).thenReturn(user);
        when(passwordEncoder.encode(request.password())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(saved);
        when(userMapper.toDto(saved)).thenReturn(dto);

        UserDto result = userService.createUser(request);
        assertEquals(dto, result);
        verify(passwordEncoder).encode("secret");
    }

    @Test
    void deactivateUserSetsStatusInactive() {
        User user = User.builder().id(1L).status(UserStatus.ACTIVE).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deactivateUser(1L);

        assertEquals(UserStatus.INACTIVE, user.getStatus());
        verify(userRepository).save(user);
    }
}

