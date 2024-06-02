package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import org.springframework.stereotype.Component;

/**
 * Komponent mappera do konwersji między encjami User a DTO User.
 */
@Component
public class UserMapper {

    /**
     * Konwertuje encję User na UserDto.
     *
     * @param user encja User do konwersji.
     * @return skonwertowany UserDto.
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null; // Obsługa przypadku, gdy user jest null
        }
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
    }

    /**
     * Konwertuje UserDto na encję User.
     *
     * @param userDto UserDto do konwersji.
     * @return skonwertowana encja User.
     */
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null; // Obsługa przypadku, gdy userDto jest null
        }
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );
    }

    /**
     * Aktualizuje encję User danymi z UserDto.
     *
     * @param userDto dane do aktualizacji.
     * @param user encja User do zaktualizowania.
     */
    public void updateEntityFromDto(UserDto userDto, User user) {
        if (userDto != null && user != null) {
            user.setFirstName(userDto.firstName());
            user.setLastName(userDto.lastName());
            user.setBirthdate(userDto.birthdate());
            user.setEmail(userDto.email());
        }
    }
}
