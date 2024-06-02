package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST kontroler do zarządzania użytkownikami.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return ResponseEntity zawierające listę UserDto.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Pobiera wszystkich użytkowników w uproszczonym formacie.
     *
     * @return ResponseEntity zawierające listę UserDto.
     */
    @GetMapping("/simple")
    public ResponseEntity<List<UserDto>> getAllSimpleUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Pobiera użytkownika po jego ID.
     *
     * @param id ID użytkownika do pobrania.
     * @return ResponseEntity zawierające UserDto, jeśli znaleziono, lub 404, jeśli nie znaleziono.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userMapper.toDto(userService.getUserById(id));
            return ResponseEntity.ok(userDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Wyszukuje użytkowników po e-mailu.
     *
     * @param email e-mail do wyszukania.
     * @return ResponseEntity zawierające listę UserDto.
     */
    @GetMapping("/email")
    public ResponseEntity<List<UserDto>> getUserByEmail(@RequestParam String email) {
        List<UserDto> users = userService.searchUsersByEmail(email)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Dodaje nowego użytkownika.
     *
     * @param userDto UserDto do dodania.
     * @return ResponseEntity zawierające utworzony UserDto.
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userMapper.toDto(userService.createUser(userMapper.toEntity(userDto)));
        return ResponseEntity.status(201).body(createdUser);
    }

    /**
     * Aktualizuje istniejącego użytkownika.
     *
     * @param id ID użytkownika do aktualizacji.
     * @param userDto UserDto zawierający zaktualizowane dane.
     * @return ResponseEntity zawierające zaktualizowany UserDto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
            return ResponseEntity.ok(userMapper.toDto(updatedUser));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Usuwa użytkownika po jego ID.
     *
     * @param id ID użytkownika do usunięcia.
     * @return ResponseEntity ze statusem 204, jeśli operacja się powiodła, lub 404, jeśli nie znaleziono.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Wyszukuje użytkowników po e-mailu.
     *
     * @param email e-mail do wyszukania.
     * @return ResponseEntity zawierające listę UserDto.
     */
    @GetMapping("/search/email")
    public ResponseEntity<List<UserDto>> searchUsersByEmail(@RequestParam String email) {
        List<UserDto> users = userService.searchUsersByEmail(email)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Wyszukuje użytkowników starszych niż określony wiek.
     *
     * @param age wiek do porównania.
     * @return ResponseEntity zawierające listę UserDto.
     */
    @GetMapping("/search/age")
    public ResponseEntity<List<UserDto>> searchUsersByAge(@RequestParam int age) {
        List<UserDto> users = userService.searchUsersByAgeGreaterThan(age)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * Wyszukuje użytkowników starszych niż określona data.
     *
     * @param time data do porównania.
     * @return ResponseEntity zawierające listę UserDto.
     */
    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> findUsersOlderThan(@PathVariable String time) {
        LocalDate date = LocalDate.parse(time);
        List<UserDto> users = userService.findUsersOlderThan(date)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}