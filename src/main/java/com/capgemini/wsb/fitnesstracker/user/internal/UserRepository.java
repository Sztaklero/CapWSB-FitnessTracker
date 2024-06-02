package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Wyszukuje użytkowników po adresie e-mail. Dopasowanie jest dokładne.
     *
     * @param email adres e-mail użytkownika do wyszukania
     * @return {@link Optional} zawierający znalezionego użytkownika lub {@link Optional#empty()} jeśli żaden nie pasuje
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Wyszukuje użytkowników po fragmencie adresu e-mail (ignorując wielkość liter).
     *
     * @param emailFragment fragment adresu e-mail użytkowników do wyszukania
     * @return Lista użytkowników spełniających kryteria wyszukiwania
     */
    default List<User> findByEmailContainingIgnoreCase(String emailFragment) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(emailFragment.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Wyszukuje użytkowników starszych niż podany wiek.
     *
     * @param age wiek do porównania z wiekiem użytkowników
     * @return Lista użytkowników starszych niż podany wiek
     */
    default List<User> findByAgeGreaterThan(int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .collect(Collectors.toList());
    }

    /**
     * Znajduje użytkowników urodzonych przed określoną datą.
     *
     * @param date data do porównania
     * @return lista użytkowników urodzonych przed określoną datą
     */
    List<User> findByBirthdateBefore(LocalDate date);
}
