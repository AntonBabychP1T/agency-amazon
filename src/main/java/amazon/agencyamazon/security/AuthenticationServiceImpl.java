package amazon.agencyamazon.security;

import amazon.agencyamazon.dto.authentication.UserLoginRequestDto;
import amazon.agencyamazon.dto.authentication.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;

    @Override
    public UserLoginResponseDto authentication(UserLoginRequestDto requestDto) {
        final Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
