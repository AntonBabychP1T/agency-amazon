package amazon.agencyamazon.service;

import amazon.agencyamazon.dto.user.UserRegistrationRequestDto;
import amazon.agencyamazon.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);
}
