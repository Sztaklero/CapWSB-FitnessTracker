package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacja serwisu do zarządzania użytkownikami.
 */
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Pobiera wszystkich użytkowników jako DTO.
     *
     * @return lista wszystkich użytkowników jako DTO.
     */
    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera użytkownika po jego ID.
     *
     * @param id ID użytkownika do pobrania.
     * @return użytkownik o podanym ID.
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony.
     */
    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Pobiera użytkownika po jego ID jako DTO.
     *
     * @param id ID użytkownika do pobrania.
     * @return użytkownik o podanym ID jako DTO.
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony.
     */
    @Override
    public UserDto findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Tworzy nowego użytkownika.
     *
     * @param user użytkownik do utworzenia.
     * @return utworzony użytkownik.
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Tworzy nowego użytkownika jako DTO.
     *
     * @param userDto użytkownik do utworzenia jako DTO.
     * @return utworzony użytkownik jako DTO.
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Aktualizuje istniejącego użytkownika.
     *
     * @param id   ID użytkownika do aktualizacji.
     * @param user dane użytkownika do aktualizacji.
     * @return zaktualizowany użytkownik.
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony.
     */
    @Override
    public User updateUser(Long id, User user) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        user.setId(id);
        return userRepository.save(user);
    }

    /**
     * Aktualizuje istniejącego użytkownika jako DTO.
     *
     * @param id   ID użytkownika do aktualizacji.
     * @param userDto dane użytkownika do aktualizacji jako DTO.
     * @return zaktualizowany użytkownik jako DTO.
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony.
     */
    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userMapper.updateEntityFromDto(userDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Usuwa użytkownika po jego ID.
     *
     * @param id ID użytkownika do usunięcia.
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony.
     */
    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Wyszukuje użytkowników po adresie e-mail.
     *
     * @param email adres e-mail do wyszukania.
     * @return lista użytkowników zawierających podany adres e-mail.
     */
    @Override
    public List<User> searchUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    /**
     * Wyszukuje użytkowników starszych niż określony wiek.
     *
     * @param age wiek do porównania.
     * @return lista użytkowników jako DTO.
     */
    @Override
    public List<UserDto> searchUsersByAge(int age) {
        return userRepository.findByAgeGreaterThan(age).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Wyszukuje użytkowników starszych niż określony wiek.
     *
     * @param age wiek do porównania.
     * @return lista użytkowników, których wiek jest większy niż podana wartość.
     */
    @Override
    public List<User> searchUsersByAgeGreaterThan(int age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    /**
     * Wyszukuje użytkowników starszych niż określona data.
     *
     * @param date data do porównania.
     * @return lista użytkowników, którzy urodzili się przed podaną datą.
     */
    @Override
    public List<User> findUsersOlderThan(LocalDate date) {
        return userRepository.findByBirthdateBefore(date);
    }
}