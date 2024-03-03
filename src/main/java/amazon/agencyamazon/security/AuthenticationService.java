package amazon.agencyamazon.security;

import amazon.agencyamazon.dto.UserLoginRequestDto;
import amazon.agencyamazon.dto.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authentication(UserLoginRequestDto requestDto);
}
