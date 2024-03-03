package amazon.agencyamazon.service.impl;

import amazon.agencyamazon.dto.user.UserRegistrationRequestDto;
import amazon.agencyamazon.dto.user.UserResponseDto;
import amazon.agencyamazon.expection.RegistrationException;
import amazon.agencyamazon.mapper.UserMapper;
import amazon.agencyamazon.model.User;
import amazon.agencyamazon.repository.UserRepository;
import amazon.agencyamazon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findUserByEmail(requestDto.email()).isPresent()) {
            throw new RegistrationException("This email already registered");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        return userMapper.toDto(userRepository.save(user));
    }
}
