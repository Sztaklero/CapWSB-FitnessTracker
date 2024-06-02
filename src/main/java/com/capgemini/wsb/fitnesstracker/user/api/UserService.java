package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfejs (API) do operacji modyfikujących na encjach {@link User} poprzez API.
 * Implementujące klasy są odpowiedzialne za wykonywanie zmian w ramach transakcji bazodanowej, kontynuując istniejącą transakcję lub tworząc nową, jeśli to konieczne.
 */
public interface UserService {
    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    List<User> getAllUsers();

    /**
     * Pobiera użytkownika po jego ID.
     *
     * @param id ID użytkownika do pobrania
     * @return użytkownik o podanym ID
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony
     */
    User getUserById(Long id) throws UserNotFoundException;

    /**
     * Tworzy nowego użytkownika.
     *
     * @param user użytkownik do utworzenia
     * @return utworzony użytkownik
     */
    User createUser(User user);

    /**
     * Pobiera wszystkich użytkowników jako DTO.
     *
     * @return lista wszystkich użytkowników jako DTO
     */
    List<UserDto> findAllUsers();

    /**
     * Tworzy nowego użytkownika jako DTO.
     *
     * @param userDto użytkownik do utworzenia jako DTO
     * @return utworzony użytkownik jako DTO
     */
    UserDto createUser(UserDto userDto);

    /**
     * Aktualizuje istniejącego użytkownika.
     *
     * @param id ID użytkownika do aktualizacji
     * @param user dane użytkownika do aktualizacji
     * @return zaktualizowany użytkownik
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony
     */
    User updateUser(Long id, User user) throws UserNotFoundException;

    /**
     * Usuwa użytkownika po jego ID.
     *
     * @param id ID użytkownika do usunięcia
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie zostanie znaleziony
     */
    void deleteUser(Long id) throws UserNotFoundException;

    /**
     * Aktualizuje istniejącego użytkownika jako DTO.
     *
     * @param id ID użytkownika do aktualizacji
     * @param userDto dane użytkownika do aktualizacji jako DTO
     * @return zaktualizowany użytkownik jako DTO
     */
    UserDto updateUser(Long id, UserDto userDto);

    /**
     * Pobiera użytkownika po jego ID jako DTO.
     *
     * @param id ID użytkownika do pobrania
     * @return użytkownik o podanym ID jako DTO
     */
    UserDto findUserById(Long id);

    /**
     * Wyszukuje użytkowników po adresie e-mail.
     *
     * @param email adres e-mail do wyszukania
     * @return lista użytkowników zawierających podany adres e-mail
     */
    List<User> searchUsersByEmail(String email);

    /**
     * Wyszukuje użytkowników starszych niż określony wiek.
     *
     * @param age wiek do porównania
     * @return lista użytkowników jako DTO starszych niż podany wiek
     */
    List<UserDto> searchUsersByAge(int age);

    /**
     * Wyszukuje użytkowników starszych niż określony wiek.
     *
     * @param age wiek do porównania
     * @return lista użytkowników starszych niż podany wiek
     */
    List<User> searchUsersByAgeGreaterThan(int age);

    /**
     * Wyszukuje użytkowników starszych niż określona data.
     *
     * @param date data do porównania
     * @return lista użytkowników, którzy urodzili się przed podaną datą
     */
    List<User> findUsersOlderThan(LocalDate date);
}

