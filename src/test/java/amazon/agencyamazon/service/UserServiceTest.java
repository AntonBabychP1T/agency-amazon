package amazon.agencyamazon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import amazon.agencyamazon.dto.user.UserRegistrationRequestDto;
import amazon.agencyamazon.dto.user.UserResponseDto;
import amazon.agencyamazon.expection.RegistrationException;
import amazon.agencyamazon.mapper.UserMapper;
import amazon.agencyamazon.model.User;
import amazon.agencyamazon.repository.UserRepository;
import amazon.agencyamazon.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String VALID_EMAIL = "test@email.com";
    private static final String VALID_PASSWORD = "Password";
    private static final String VALID_ID = "123456";
    @InjectMocks
    public UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User createValidUser() {
        User user = new User();
        user.setId(VALID_ID);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        return user;
    }

    private UserResponseDto createValidUserResponseDto() {
        return new UserResponseDto(VALID_ID, VALID_EMAIL);
    }

    private UserRegistrationRequestDto createValidUserRegistrationRequestDto() {
        return new UserRegistrationRequestDto(VALID_EMAIL, VALID_PASSWORD);
    }

    @Test
    @DisplayName("Verify register() method works")
    public void register_ValidUserRegistrationRequestDto_ReturnUserResponseDto() {
        UserRegistrationRequestDto requestDto = createValidUserRegistrationRequestDto();
        User user = createValidUser();
        UserResponseDto expected = createValidUserResponseDto();
        when(userRepository.findUserByEmail(requestDto.email())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(passwordEncoder.encode(requestDto.password())).thenReturn("HashedPassword");
        when(userMapper.toDto(user)).thenReturn(expected);

        UserResponseDto actual = userService.register(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify register() method throw exception")
    public void register_ExistingEmailRegistrationRequestDto_ThrowException() {
        UserRegistrationRequestDto requestDto = createValidUserRegistrationRequestDto();
        when(userRepository.findUserByEmail(requestDto.email())).thenReturn(Optional.of(createValidUser()));

        assertThrows(RegistrationException.class, () -> userService.register(requestDto));
    }
}
