package amazon.agencyamazon.controller;

import amazon.agencyamazon.dto.authentication.UserLoginRequestDto;
import amazon.agencyamazon.dto.authentication.UserLoginResponseDto;
import amazon.agencyamazon.dto.user.UserRegistrationRequestDto;
import amazon.agencyamazon.dto.user.UserResponseDto;
import amazon.agencyamazon.security.AuthenticationService;
import amazon.agencyamazon.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication manager", description = "Endpoints for registration and authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authentication(requestDto);
    }
}
