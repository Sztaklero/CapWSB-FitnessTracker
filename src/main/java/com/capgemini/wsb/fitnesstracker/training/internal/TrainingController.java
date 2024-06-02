package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Pobierz wszystkie treningi
     *
     * @return Lista wszystkich treningów
     */
    @GetMapping
    public List<Training> getAllTrainings() {
        List<Training> allTrainings = trainingRepository.findAll();
        if (allTrainings.isEmpty()) {
            throw new RuntimeException("Brak znalezionych treningów");
        }
        return allTrainings;
    }

    /**
     * Dodaj nowy trening
     *
     * @param trainingDto Dane treningu
     * @return ResponseEntity z utworzonym treningiem
     */
    @PostMapping
    public ResponseEntity<Training> addNewTraining(@RequestBody TrainingDtoWithUserId trainingDto) {
        if (trainingDto.userId() == null) {
            throw new RuntimeException("ID użytkownika treningu jest puste");
        }
        Optional<User> userOptional = userRepository.findById(trainingDto.userId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Training newTraining = trainingService.createTraining(trainingMapper.toEntity(trainingDto, user));
            return new ResponseEntity<>(newTraining, HttpStatus.CREATED);
        }
        throw new RuntimeException("Nie znaleziono użytkownika");
    }

    /**
     * Pobierz zakończone treningi po określonym czasie
     *
     * @param afterTime Data, po której treningi są zakończone
     * @return Lista zakończonych treningów
     */
    @GetMapping("/finished/{afterTime}")
    public List<Training> getFinishedTrainings(@PathVariable("afterTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        return trainingRepository.getTrainingsFinishedAfter(afterTime);
    }

    /**
     * Pobierz treningi według typu aktywności
     *
     * @param activityType Typ aktywności do filtrowania treningów
     * @return Lista treningów o określonym typie aktywności
     */
    @GetMapping("/activityType")
    public List<Training> getTrainingsByActivityType(@RequestParam("activityType") String activityType) {
        ActivityType activity = ActivityType.valueOf(activityType.toUpperCase());
        return trainingRepository.getTrainingsByType(activity);
    }

    /**
     * Zaktualizuj istniejący trening
     *
     * @param id ID treningu
     * @param trainingDto Dane treningu do aktualizacji
     * @return ResponseEntity z zaktualizowanym treningiem
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<Training> updateTraining(@PathVariable("trainingId") Long id, @RequestBody TrainingDto trainingDto) {
        Optional<Training> existingTrainingOptional = trainingService.getTrainingById(id);
        if (existingTrainingOptional.isPresent()) {
            Training existingTraining = existingTrainingOptional.get();
            User user = existingTraining.getUser();
            Training updatedTraining = trainingMapper.toEntity(trainingDto, id, user);
            trainingRepository.updateTraining(updatedTraining);
            return new ResponseEntity<>(updatedTraining, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Pobierz treningi według ID użytkownika
     *
     * @param userId ID użytkownika
     * @return ResponseEntity z listą treningów
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Training>> getTrainingsByUserId(@PathVariable("userId") Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Training> userTrainings = trainingRepository.getTrainingsByUser(user);
            return new ResponseEntity<>(userTrainings, HttpStatus.OK);
        }
        throw new RuntimeException("Nie znaleziono użytkownika");
    }
}