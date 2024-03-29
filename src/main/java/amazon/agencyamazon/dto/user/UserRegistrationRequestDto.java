package amazon.agencyamazon.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(
        @Email(message = "Email cannot be null")
        String email,
        @NotNull
        @Size(min = 8, max = 24, message = "Password must be between 8 and 24 characters")
        String password
) {
}
