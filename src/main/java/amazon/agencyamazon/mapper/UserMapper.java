package amazon.agencyamazon.mapper;

import amazon.agencyamazon.config.MapperConfig;
import amazon.agencyamazon.dto.user.UserRegistrationRequestDto;
import amazon.agencyamazon.dto.user.UserResponseDto;
import amazon.agencyamazon.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toDto(User user);
}