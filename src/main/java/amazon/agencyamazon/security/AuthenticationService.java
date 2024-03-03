package amazon.agencyamazon.security;

import amazon.agencyamazon.dto.authentication.UserLoginRequestDto;
import amazon.agencyamazon.dto.authentication.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authentication(UserLoginRequestDto requestDto);
}
